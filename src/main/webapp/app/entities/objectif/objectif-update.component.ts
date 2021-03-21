import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IObjectif, Objectif } from 'app/shared/model/objectif.model';
import { ObjectifService } from './objectif.service';
import { IAction } from 'app/shared/model/action.model';
import { ActionService } from 'app/entities/action/action.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { IProcessusSMI } from 'app/shared/model/processus-smi.model';
import { ProcessusSMIService } from 'app/entities/processus-smi/processus-smi.service';
import { IIndicateurSMI } from 'app/shared/model/indicateur-smi.model';
import { IndicateurSMIService } from 'app/entities/indicateur-smi/indicateur-smi.service';

type SelectableEntity = IAction | IUser | IProcessusSMI | IIndicateurSMI;

@Component({
  selector: 'jhi-objectif-update',
  templateUrl: './objectif-update.component.html',
})
export class ObjectifUpdateComponent implements OnInit {
  isSaving = false;
  actions: IAction[] = [];
  users: IUser[] = [];
  processussmis: IProcessusSMI[] = [];
  indicateursmis: IIndicateurSMI[] = [];

  editForm = this.fb.group({
    id: [],
    axedelapolitiqueqse: [],
    objectifqse: [],
    origine: [],
    action: [],
    delegue: [],
    processus: [],
    indicateur: [],
  });

  constructor(
    protected objectifService: ObjectifService,
    protected actionService: ActionService,
    protected userService: UserService,
    protected processusSMIService: ProcessusSMIService,
    protected indicateurSMIService: IndicateurSMIService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ objectif }) => {
      this.updateForm(objectif);

      this.actionService.query().subscribe((res: HttpResponse<IAction[]>) => (this.actions = res.body || []));

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));

      this.processusSMIService.query().subscribe((res: HttpResponse<IProcessusSMI[]>) => (this.processussmis = res.body || []));

      this.indicateurSMIService.query().subscribe((res: HttpResponse<IIndicateurSMI[]>) => (this.indicateursmis = res.body || []));
    });
  }

  updateForm(objectif: IObjectif): void {
    this.editForm.patchValue({
      id: objectif.id,
      axedelapolitiqueqse: objectif.axedelapolitiqueqse,
      objectifqse: objectif.objectifqse,
      origine: objectif.origine,
      action: objectif.action,
      delegue: objectif.delegue,
      processus: objectif.processus,
      indicateur: objectif.indicateur,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const objectif = this.createFromForm();
    if (objectif.id !== undefined) {
      this.subscribeToSaveResponse(this.objectifService.update(objectif));
    } else {
      this.subscribeToSaveResponse(this.objectifService.create(objectif));
    }
  }

  private createFromForm(): IObjectif {
    return {
      ...new Objectif(),
      id: this.editForm.get(['id'])!.value,
      axedelapolitiqueqse: this.editForm.get(['axedelapolitiqueqse'])!.value,
      objectifqse: this.editForm.get(['objectifqse'])!.value,
      origine: this.editForm.get(['origine'])!.value,
      action: this.editForm.get(['action'])!.value,
      delegue: this.editForm.get(['delegue'])!.value,
      processus: this.editForm.get(['processus'])!.value,
      indicateur: this.editForm.get(['indicateur'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IObjectif>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
