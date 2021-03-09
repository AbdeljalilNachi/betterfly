import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IAnalyseEnvirommentale, AnalyseEnvirommentale } from 'app/shared/model/analyse-envirommentale.model';
import { AnalyseEnvirommentaleService } from './analyse-envirommentale.service';
import { ProcessusSMIService } from 'app/entities/processus-smi/processus-smi.service.ts';


@Component({
  selector: 'jhi-analyse-envirommentale-update',
  templateUrl: './analyse-envirommentale-update.component.html',
})
export class AnalyseEnvirommentaleUpdateComponent implements OnInit {
  isSaving = false;
  dateDp: any;
  pros: String[] | null = null;
  editForm = this.fb.group({
    id: [],
    date: [],
    processus: [],
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
  });

  constructor(
    protected analyseEnvirommentaleService: AnalyseEnvirommentaleService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
    ,
    private processusSMIService : ProcessusSMIService ) {}

  ngOnInit(): void {
    this.loadAll() ;
    this.activatedRoute.data.subscribe(({ analyseEnvirommentale }) => {
      this.updateForm(analyseEnvirommentale);
    });
  }

  updateForm(analyseEnvirommentale: IAnalyseEnvirommentale): void {
    this.editForm.patchValue({
      id: analyseEnvirommentale.id,
      date: analyseEnvirommentale.date,
      processus: analyseEnvirommentale.processus,
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
      processus: this.editForm.get(['processus'])!.value,
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

  private loadAll(): void {
    this.processusSMIService .getProcs()
      .subscribe((res: String[]) => this.onSuccessLogins(res));

  }

  private onSuccessLogins(pros: String[] | null): void {
    this.pros = pros;
  }

}
