import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IAnalyseEnvirommentale, AnalyseEnvirommentale } from 'app/shared/model/analyse-envirommentale.model';
import { AnalyseEnvirommentaleService } from './analyse-envirommentale.service';
import { IAction } from 'app/shared/model/action.model';
import { ActionService } from 'app/entities/action/action.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { IProcessusSMI } from 'app/shared/model/processus-smi.model';
import { ProcessusSMIService } from 'app/entities/processus-smi/processus-smi.service';

type SelectableEntity = IAction | IUser | IProcessusSMI;

@Component({
  selector: 'jhi-analyse-envirommentale-update',
  templateUrl: './analyse-envirommentale-update.component.html',
})
export class AnalyseEnvirommentaleUpdateComponent implements OnInit {
  isSaving = false;
  actions: IAction[] = [];
  users: IUser[] = [];
  processussmis: IProcessusSMI[] = [];
  dateDp: any;

  editForm = this.fb.group({
    id: [],
    date: [],
    businessUnit: [],
    activite: [],
    aspectEnvironnemental: [],
    impactEnvironnemental: [],
    competencesRequises: [],
    situation: [],
    frequence: [],
    sensibiliteMilieu: [],
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
    protected analyseEnvirommentaleService: AnalyseEnvirommentaleService,
    protected actionService: ActionService,
    protected userService: UserService,
    protected processusSMIService: ProcessusSMIService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ analyseEnvirommentale }) => {
      this.updateForm(analyseEnvirommentale);

      this.actionService.query().subscribe((res: HttpResponse<IAction[]>) => (this.actions = res.body || []));

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));

      this.processusSMIService.query().subscribe((res: HttpResponse<IProcessusSMI[]>) => (this.processussmis = res.body || []));
    });
  }

  updateForm(analyseEnvirommentale: IAnalyseEnvirommentale): void {
    this.editForm.patchValue({
      id: analyseEnvirommentale.id,
      date: analyseEnvirommentale.date,
      businessUnit: analyseEnvirommentale.businessUnit,
      activite: analyseEnvirommentale.activite,
      aspectEnvironnemental: analyseEnvirommentale.aspectEnvironnemental,
      impactEnvironnemental: analyseEnvirommentale.impactEnvironnemental,
      competencesRequises: analyseEnvirommentale.competencesRequises,
      situation: analyseEnvirommentale.situation,
      frequence: analyseEnvirommentale.frequence,
      sensibiliteMilieu: analyseEnvirommentale.sensibiliteMilieu,
      coefficientMaitrise: analyseEnvirommentale.coefficientMaitrise,
      gravite: analyseEnvirommentale.gravite,
      criticite: analyseEnvirommentale.criticite,
      maitriseExistante: analyseEnvirommentale.maitriseExistante,
      origine: analyseEnvirommentale.origine,
      action: analyseEnvirommentale.action,
      delegue: analyseEnvirommentale.delegue,
      processus: analyseEnvirommentale.processus,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const analyseEnvirommentale = this.createFromForm();
    if (analyseEnvirommentale.id !== undefined) {
      this.subscribeToSaveResponse(this.analyseEnvirommentaleService.update(analyseEnvirommentale));
    } else {
      this.subscribeToSaveResponse(this.analyseEnvirommentaleService.create(analyseEnvirommentale));
    }
  }

  private createFromForm(): IAnalyseEnvirommentale {
    return {
      ...new AnalyseEnvirommentale(),
      id: this.editForm.get(['id'])!.value,
      date: this.editForm.get(['date'])!.value,
      businessUnit: this.editForm.get(['businessUnit'])!.value,
      activite: this.editForm.get(['activite'])!.value,
      aspectEnvironnemental: this.editForm.get(['aspectEnvironnemental'])!.value,
      impactEnvironnemental: this.editForm.get(['impactEnvironnemental'])!.value,
      competencesRequises: this.editForm.get(['competencesRequises'])!.value,
      situation: this.editForm.get(['situation'])!.value,
      frequence: this.editForm.get(['frequence'])!.value,
      sensibiliteMilieu: this.editForm.get(['sensibiliteMilieu'])!.value,
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

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAnalyseEnvirommentale>>): void {
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
