import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IObligationConformite, ObligationConformite } from 'app/shared/model/obligation-conformite.model';
import { ObligationConformiteService } from './obligation-conformite.service';
import { IAction } from 'app/shared/model/action.model';
import { ActionService } from 'app/entities/action/action.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { IProcessusSMI } from 'app/shared/model/processus-smi.model';
import { ProcessusSMIService } from 'app/entities/processus-smi/processus-smi.service';

type SelectableEntity = IAction | IUser | IProcessusSMI;

@Component({
  selector: 'jhi-obligation-conformite-update',
  templateUrl: './obligation-conformite-update.component.html',
})
export class ObligationConformiteUpdateComponent implements OnInit {
  isSaving = false;
  actions: IAction[] = [];
  users: IUser[] = [];
  processussmis: IProcessusSMI[] = [];
  dateDp: any;

  editForm = this.fb.group({
    id: [],
    date: [],
    rubrique: [],
    reference: [],
    num: [],
    exigence: [],
    applicable: [],
    conforme: [],
    statut: [],
    observation: [],
    origine: [],
    action: [],
    delegue: [],
    processus: [],
  });

  constructor(
    protected obligationConformiteService: ObligationConformiteService,
    protected actionService: ActionService,
    protected userService: UserService,
    protected processusSMIService: ProcessusSMIService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ obligationConformite }) => {
      this.updateForm(obligationConformite);

      this.actionService.query().subscribe((res: HttpResponse<IAction[]>) => (this.actions = res.body || []));

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));

      this.processusSMIService.query().subscribe((res: HttpResponse<IProcessusSMI[]>) => (this.processussmis = res.body || []));
    });
  }

  updateForm(obligationConformite: IObligationConformite): void {
    this.editForm.patchValue({
      id: obligationConformite.id,
      date: obligationConformite.date,
      rubrique: obligationConformite.rubrique,
      reference: obligationConformite.reference,
      num: obligationConformite.num,
      exigence: obligationConformite.exigence,
      applicable: obligationConformite.applicable,
      conforme: obligationConformite.conforme,
      statut: obligationConformite.statut,
      observation: obligationConformite.observation,
      origine: obligationConformite.origine,
      action: obligationConformite.action,
      delegue: obligationConformite.delegue,
      processus: obligationConformite.processus,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const obligationConformite = this.createFromForm();
    if (obligationConformite.id !== undefined) {
      this.subscribeToSaveResponse(this.obligationConformiteService.update(obligationConformite));
    } else {
      this.subscribeToSaveResponse(this.obligationConformiteService.create(obligationConformite));
    }
  }

  private createFromForm(): IObligationConformite {
    return {
      ...new ObligationConformite(),
      id: this.editForm.get(['id'])!.value,
      date: this.editForm.get(['date'])!.value,
      rubrique: this.editForm.get(['rubrique'])!.value,
      reference: this.editForm.get(['reference'])!.value,
      num: this.editForm.get(['num'])!.value,
      exigence: this.editForm.get(['exigence'])!.value,
      applicable: this.editForm.get(['applicable'])!.value,
      conforme: this.editForm.get(['conforme'])!.value,
      statut: this.editForm.get(['statut'])!.value,
      observation: this.editForm.get(['observation'])!.value,
      origine: this.editForm.get(['origine'])!.value,
      action: this.editForm.get(['action'])!.value,
      delegue: this.editForm.get(['delegue'])!.value,
      processus: this.editForm.get(['processus'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IObligationConformite>>): void {
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
