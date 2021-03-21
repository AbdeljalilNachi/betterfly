import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IAutreAction, AutreAction } from 'app/shared/model/autre-action.model';
import { AutreActionService } from './autre-action.service';
import { IAction } from 'app/shared/model/action.model';
import { ActionService } from 'app/entities/action/action.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { IProcessusSMI } from 'app/shared/model/processus-smi.model';
import { ProcessusSMIService } from 'app/entities/processus-smi/processus-smi.service';

type SelectableEntity = IAction | IUser | IProcessusSMI;

@Component({
  selector: 'jhi-autre-action-update',
  templateUrl: './autre-action-update.component.html',
})
export class AutreActionUpdateComponent implements OnInit {
  isSaving = false;
  actions: IAction[] = [];
  users: IUser[] = [];
  processussmis: IProcessusSMI[] = [];

  editForm = this.fb.group({
    id: [],
    origineAction: [],
    origine: [],
    action: [],
    delegue: [],
    processus: [],
  });

  constructor(
    protected autreActionService: AutreActionService,
    protected actionService: ActionService,
    protected userService: UserService,
    protected processusSMIService: ProcessusSMIService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ autreAction }) => {
      this.updateForm(autreAction);

      this.actionService.query().subscribe((res: HttpResponse<IAction[]>) => (this.actions = res.body || []));

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));

      this.processusSMIService.query().subscribe((res: HttpResponse<IProcessusSMI[]>) => (this.processussmis = res.body || []));
    });
  }

  updateForm(autreAction: IAutreAction): void {
    this.editForm.patchValue({
      id: autreAction.id,
      origineAction: autreAction.origineAction,
      origine: autreAction.origine,
      action: autreAction.action,
      delegue: autreAction.delegue,
      processus: autreAction.processus,
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
      origineAction: this.editForm.get(['origineAction'])!.value,
      origine: this.editForm.get(['origine'])!.value,
      action: this.editForm.get(['action'])!.value,
      delegue: this.editForm.get(['delegue'])!.value,
      processus: this.editForm.get(['processus'])!.value,
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
