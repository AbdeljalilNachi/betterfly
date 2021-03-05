import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IAutreAction, AutreAction } from 'app/shared/model/autre-action.model';
import { AutreActionService } from './autre-action.service';

@Component({
  selector: 'jhi-autre-action-update',
  templateUrl: './autre-action-update.component.html',
})
export class AutreActionUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    processus: [],
    origineAction: [],
    origine: [],
  });

  constructor(protected autreActionService: AutreActionService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ autreAction }) => {
      this.updateForm(autreAction);
    });
  }

  updateForm(autreAction: IAutreAction): void {
    this.editForm.patchValue({
      id: autreAction.id,
      processus: autreAction.processus,
      origineAction: autreAction.origineAction,
      origine: autreAction.origine,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const autreAction = this.createFromForm();
    if (autreAction.id !== undefined) {
      this.subscribeToSaveResponse(this.autreActionService.update(autreAction));
    } else {
      this.subscribeToSaveResponse(this.autreActionService.create(autreAction));
    }
  }

  private createFromForm(): IAutreAction {
    return {
      ...new AutreAction(),
      id: this.editForm.get(['id'])!.value,
      processus: this.editForm.get(['processus'])!.value,
      origineAction: this.editForm.get(['origineAction'])!.value,
      origine: this.editForm.get(['origine'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAutreAction>>): void {
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
