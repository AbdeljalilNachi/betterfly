import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IAction, Action } from 'app/shared/model/action.model';
import { ActionService } from './action.service';
import { Statut } from '../../shared/model/enumerations/statut.model';

@Component({
  selector: 'jhi-action-update',
  templateUrl: './action-update.component.html',
})
export class ActionUpdateComponent implements OnInit {
  isSaving = false;
  datePlanificationDp: any;
  delaiDp: any;

  editForm = this.fb.group({
    id: [],
    datePlanification: [],
    action: [],
    type: [],
    delai: [],
    avancement: [],
    realisee: [],
    critereResultat: [],
    efficace:  [],
    ressourcesNecessaires: [],
    statut: [],
  });

  constructor(protected actionService: ActionService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ action }) => {
      this.updateForm(action);
    });
  }

  updateForm(action: IAction): void {
    this.editForm.patchValue({
      id: action.id,
      datePlanification: action.datePlanification,
      action: action.action,
      type: action.type,
      delai: action.delai,
      avancement: action.avancement,
      realisee: action.realisee,
      critereResultat: action.critereResultat,
      efficace: action.efficace,
      ressourcesNecessaires: action.ressourcesNecessaires,
      statut: action.statut,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const action = this.createFromForm();
    if (action.id !== undefined) {
      this.subscribeToSaveResponse(this.actionService.update(action));
    } else {
      this.subscribeToSaveResponse(this.actionService.create(action));
    }
  }

  private createFromForm(): IAction {
    return {
      ...new Action(),
      id: this.editForm.get(['id'])!.value,
      datePlanification: this.editForm.get(['datePlanification'])!.value,
      action: this.editForm.get(['action'])!.value,
      type: this.editForm.get(['type'])!.value,
      delai: this.editForm.get(['delai'])!.value,
      avancement: this.editForm.get(['avancement'])!.value,
      realisee: this.editForm.get(['realisee'])!.value,
      critereResultat: this.editForm.get(['critereResultat'])!.value,
      efficace: this.editForm.get(['efficace'])!.value,
      ressourcesNecessaires: this.editForm.get(['ressourcesNecessaires'])!.value,
       statut: Statut.EN_COURS,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAction>>): void {
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
