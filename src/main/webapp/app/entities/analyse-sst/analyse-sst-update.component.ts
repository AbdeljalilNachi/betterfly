import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IAnalyseSST, AnalyseSST } from 'app/shared/model/analyse-sst.model';
import { AnalyseSSTService } from './analyse-sst.service';
import { IAction } from 'app/shared/model/action.model';
import { ActionService } from 'app/entities/action/action.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { IProcessusSMI } from 'app/shared/model/processus-smi.model';
import { ProcessusSMIService } from 'app/entities/processus-smi/processus-smi.service';

type SelectableEntity = IAction | IUser | IProcessusSMI;

@Component({
  selector: 'jhi-analyse-sst-update',
  templateUrl: './analyse-sst-update.component.html',
})
export class AnalyseSSTUpdateComponent implements OnInit {
  isSaving = false;
  actions: IAction[] = [];
  users: IUser[] = [];
  processussmis: IProcessusSMI[] = [];
  dateDp: any;

  editForm = this.fb.group({
    id: [],
    date: [],
    buisnessUnit: [],
    uniteTravail: [],
    danger: [],
    risque: [],
    competence: [],
    situation: [],
    frequence: [],
    dureeExposition: [],
    coefficientMaitrise: [],
    gravite: [],
    criticite: [],
    maitriseExistante: [],
    origine: [],
    action: [],
    delegue: [],
    processus: [],
  });

  constructor(
    protected analyseSSTService: AnalyseSSTService,
    protected actionService: ActionService,
    protected userService: UserService,
    protected processusSMIService: ProcessusSMIService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ analyseSST }) => {
      this.updateForm(analyseSST);

      this.actionService.query().subscribe((res: HttpResponse<IAction[]>) => (this.actions = res.body || []));

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));

      this.processusSMIService.query().subscribe((res: HttpResponse<IProcessusSMI[]>) => (this.processussmis = res.body || []));
    });
  }

  updateForm(analyseSST: IAnalyseSST): void {
    this.editForm.patchValue({
      id: analyseSST.id,
      date: analyseSST.date,
      buisnessUnit: analyseSST.buisnessUnit,
      uniteTravail: analyseSST.uniteTravail,
      danger: analyseSST.danger,
      risque: analyseSST.risque,
      competence: analyseSST.competence,
      situation: analyseSST.situation,
      frequence: analyseSST.frequence,
      dureeExposition: analyseSST.dureeExposition,
      coefficientMaitrise: analyseSST.coefficientMaitrise,
      gravite: analyseSST.gravite,
      criticite: analyseSST.criticite,
      maitriseExistante: analyseSST.maitriseExistante,
      origine: analyseSST.origine,
      action: analyseSST.action,
      delegue: analyseSST.delegue,
      processus: analyseSST.processus,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const analyseSST = this.createFromForm();
    if (analyseSST.id !== undefined) {
      this.subscribeToSaveResponse(this.analyseSSTService.update(analyseSST));
    } else {
      this.subscribeToSaveResponse(this.analyseSSTService.create(analyseSST));
    }
  }

  private createFromForm(): IAnalyseSST {
    return {
      ...new AnalyseSST(),
      id: this.editForm.get(['id'])!.value,
      date: this.editForm.get(['date'])!.value,
      buisnessUnit: this.editForm.get(['buisnessUnit'])!.value,
      uniteTravail: this.editForm.get(['uniteTravail'])!.value,
      danger: this.editForm.get(['danger'])!.value,
      risque: this.editForm.get(['risque'])!.value,
      competence: this.editForm.get(['competence'])!.value,
      situation: this.editForm.get(['situation'])!.value,
      frequence: this.editForm.get(['frequence'])!.value,
      dureeExposition: this.editForm.get(['dureeExposition'])!.value,
      coefficientMaitrise: this.editForm.get(['coefficientMaitrise'])!.value,
      gravite: this.editForm.get(['gravite'])!.value,
      criticite: this.editForm.get(['criticite'])!.value,
      maitriseExistante: this.editForm.get(['maitriseExistante'])!.value,
      origine: this.editForm.get(['origine'])!.value,
      action: this.editForm.get(['action'])!.value,
      delegue: this.editForm.get(['delegue'])!.value,
      processus: this.editForm.get(['processus'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAnalyseSST>>): void {
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
