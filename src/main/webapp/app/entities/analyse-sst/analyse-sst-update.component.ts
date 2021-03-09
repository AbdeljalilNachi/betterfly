import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IAnalyseSST, AnalyseSST } from 'app/shared/model/analyse-sst.model';
import { AnalyseSSTService } from './analyse-sst.service';
import { ProcessusSMIService } from 'app/entities/processus-smi/processus-smi.service.ts';


@Component({
  selector: 'jhi-analyse-sst-update',
  templateUrl: './analyse-sst-update.component.html',
})
export class AnalyseSSTUpdateComponent implements OnInit {
  isSaving = false;
  dateDp: any;
  pros: String[] | null = null;
  editForm = this.fb.group({
    id: [],
    date: [],
    processus: [],
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
  });

  constructor(protected analyseSSTService: AnalyseSSTService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder,
    private processusSMIService : ProcessusSMIService ) {}

  ngOnInit(): void {
    this.loadAll() ;
    this.activatedRoute.data.subscribe(({ analyseSST }) => {
      this.updateForm(analyseSST);
    });
  }

  updateForm(analyseSST: IAnalyseSST): void {
    this.editForm.patchValue({
      id: analyseSST.id,
      date: analyseSST.date,
      processus: analyseSST.processus,
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
      processus: this.editForm.get(['processus'])!.value,
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

  private loadAll(): void {
    this.processusSMIService .getProcs()
      .subscribe((res: String[]) => this.onSuccessLogins(res));

  }

  private onSuccessLogins(pros: String[] | null): void {
    this.pros = pros;
  }
}
