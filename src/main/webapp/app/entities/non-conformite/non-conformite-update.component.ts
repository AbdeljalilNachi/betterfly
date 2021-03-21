import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { INonConformite, NonConformite } from 'app/shared/model/non-conformite.model';
import { NonConformiteService } from './non-conformite.service';
import { IAction } from 'app/shared/model/action.model';
import { ActionService } from 'app/entities/action/action.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { IProcessusSMI } from 'app/shared/model/processus-smi.model';
import { ProcessusSMIService } from 'app/entities/processus-smi/processus-smi.service';

type SelectableEntity = IAction | IUser | IProcessusSMI;

@Component({
  selector: 'jhi-non-conformite-update',
  templateUrl: './non-conformite-update.component.html',
})
export class NonConformiteUpdateComponent implements OnInit {
  isSaving = false;
  actions: IAction[] = [];
  users: IUser[] = [];
  processussmis: IProcessusSMI[] = [];
  dateDp: any;

  editForm = this.fb.group({
    id: [],
    date: [],
    description: [],
    causesPotentielles: [],
    origine: [],
    action: [],
    delegue: [],
    processus: [],
  });

  constructor(
    protected nonConformiteService: NonConformiteService,
    protected actionService: ActionService,
    protected userService: UserService,
    protected processusSMIService: ProcessusSMIService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ nonConformite }) => {
      this.updateForm(nonConformite);

      this.actionService.query().subscribe((res: HttpResponse<IAction[]>) => (this.actions = res.body || []));

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));

      this.processusSMIService.query().subscribe((res: HttpResponse<IProcessusSMI[]>) => (this.processussmis = res.body || []));
    });
  }

  updateForm(nonConformite: INonConformite): void {
    this.editForm.patchValue({
      id: nonConformite.id,
      date: nonConformite.date,
      description: nonConformite.description,
      causesPotentielles: nonConformite.causesPotentielles,
      origine: nonConformite.origine,
      action: nonConformite.action,
      delegue: nonConformite.delegue,
      processus: nonConformite.processus,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const nonConformite = this.createFromForm();
    if (nonConformite.id !== undefined) {
      this.subscribeToSaveResponse(this.nonConformiteService.update(nonConformite));
    } else {
      this.subscribeToSaveResponse(this.nonConformiteService.create(nonConformite));
    }
  }

  private createFromForm(): INonConformite {
    return {
      ...new NonConformite(),
      id: this.editForm.get(['id'])!.value,
      date: this.editForm.get(['date'])!.value,
      description: this.editForm.get(['description'])!.value,
      causesPotentielles: this.editForm.get(['causesPotentielles'])!.value,
      origine: this.editForm.get(['origine'])!.value,
      action: this.editForm.get(['action'])!.value,
      delegue: this.editForm.get(['delegue'])!.value,
      processus: this.editForm.get(['processus'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INonConformite>>): void {
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
