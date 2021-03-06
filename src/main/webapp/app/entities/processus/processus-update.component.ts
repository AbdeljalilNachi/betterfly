import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IProcessus, Processus } from 'app/shared/model/processus.model';
import { ProcessusService } from './processus.service';

@Component({
  selector: 'jhi-processus-update',
  templateUrl: './processus-update.component.html',
})
export class ProcessusUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
  });

  constructor(protected processusService: ProcessusService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ processus }) => {
      this.updateForm(processus);
    });
  }

  updateForm(processus: IProcessus): void {
    this.editForm.patchValue({
      id: processus.id,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const processus = this.createFromForm();
    if (processus.id !== undefined) {
      this.subscribeToSaveResponse(this.processusService.update(processus));
    } else {
      this.subscribeToSaveResponse(this.processusService.create(processus));
    }
  }

  private createFromForm(): IProcessus {
    return {
      ...new Processus(),
      id: this.editForm.get(['id'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProcessus>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
