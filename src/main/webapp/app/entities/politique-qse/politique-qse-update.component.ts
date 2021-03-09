import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPolitiqueQSE, PolitiqueQSE } from 'app/shared/model/politique-qse.model';
import { PolitiqueQSEService } from './politique-qse.service';
import { ProcessusSMIService } from 'app/entities/processus-smi/processus-smi.service.ts';


@Component({
  selector: 'jhi-politique-qse-update',
  templateUrl: './politique-qse-update.component.html',
})
export class PolitiqueQSEUpdateComponent implements OnInit {
  isSaving = false;
  dateDp: any;
  pros: String[] | null = null;
  editForm = this.fb.group({
    id: [],
    processus: [],
    date: [],
    axePolitiqueQSE: [],
    objectifQSE: [],
    vigueur: [],
    indicateur: [],
  });

  constructor(protected politiqueQSEService: PolitiqueQSEService, 
    protected activatedRoute: ActivatedRoute, private fb: FormBuilder,
    private processusSMIService : ProcessusSMIService ) {}

  ngOnInit(): void {
    this.loadAll() ;
    this.activatedRoute.data.subscribe(({ politiqueQSE }) => {
      this.updateForm(politiqueQSE);
    });
  }

  updateForm(politiqueQSE: IPolitiqueQSE): void {
    this.editForm.patchValue({
      id: politiqueQSE.id,
      processus: politiqueQSE.processus,
      date: politiqueQSE.date,
      axePolitiqueQSE: politiqueQSE.axePolitiqueQSE,
      objectifQSE: politiqueQSE.objectifQSE,
      vigueur: politiqueQSE.vigueur,
      indicateur: politiqueQSE.indicateur,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const politiqueQSE = this.createFromForm();
    if (politiqueQSE.id !== undefined) {
      this.subscribeToSaveResponse(this.politiqueQSEService.update(politiqueQSE));
    } else {
      this.subscribeToSaveResponse(this.politiqueQSEService.create(politiqueQSE));
    }
  }

  private createFromForm(): IPolitiqueQSE {
    return {
      ...new PolitiqueQSE(),
      id: this.editForm.get(['id'])!.value,
      processus: this.editForm.get(['processus'])!.value,
      date: this.editForm.get(['date'])!.value,
      axePolitiqueQSE: this.editForm.get(['axePolitiqueQSE'])!.value,
      objectifQSE: this.editForm.get(['objectifQSE'])!.value,
      vigueur: this.editForm.get(['vigueur'])!.value,
      indicateur: this.editForm.get(['indicateur'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPolitiqueQSE>>): void {
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

  private loadAll(): void {
    this.processusSMIService .getProcs()
      .subscribe((res: String[]) => this.onSuccessLogins(res));

  }

  private onSuccessLogins(pros: String[] | null): void {
    this.pros = pros;
  }
}
