import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IIndicateurSMI, IndicateurSMI } from 'app/shared/model/indicateur-smi.model';
import { IndicateurSMIService } from './indicateur-smi.service';
import { ProcessusSMIService } from 'app/entities/processus-smi/processus-smi.service.ts';


@Component({
  selector: 'jhi-indicateur-smi-update',
  templateUrl: './indicateur-smi-update.component.html',
})
export class IndicateurSMIUpdateComponent implements OnInit {
  isSaving = false;
  dateIdentificationDp: any;
  pros: String[] | null = null;
  editForm = this.fb.group({
    id: [],
    processus: [],
    dateIdentification: [],
    indicateur: [],
    formuleCalcul: [],
    cible: [],
    seuilTolerance: [],
    unite: [],
    periodicite: [],
    responsableCalcul: [],
    observations: [],
    vigueur: [],
    annee: [],
    observation: [],
  });

  constructor(protected indicateurSMIService: IndicateurSMIService,
     protected activatedRoute: ActivatedRoute, private fb: FormBuilder,
     private processusSMIService : ProcessusSMIService ) {}

  ngOnInit(): void {
    this.loadAll() ;
    this.activatedRoute.data.subscribe(({ indicateurSMI }) => {
      this.updateForm(indicateurSMI);
    });
  }

  updateForm(indicateurSMI: IIndicateurSMI): void {
    this.editForm.patchValue({
      id: indicateurSMI.id,
      processus: indicateurSMI.processus,
      dateIdentification: indicateurSMI.dateIdentification,
      indicateur: indicateurSMI.indicateur,
      formuleCalcul: indicateurSMI.formuleCalcul,
      cible: indicateurSMI.cible,
      seuilTolerance: indicateurSMI.seuilTolerance,
      unite: indicateurSMI.unite,
      periodicite: indicateurSMI.periodicite,
      responsableCalcul: indicateurSMI.responsableCalcul,
      observations: indicateurSMI.observations,
      vigueur: indicateurSMI.vigueur,
      annee: indicateurSMI.annee,
      observation: indicateurSMI.observation,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const indicateurSMI = this.createFromForm();
    if (indicateurSMI.id !== undefined) {
      this.subscribeToSaveResponse(this.indicateurSMIService.update(indicateurSMI));
    } else {
      this.subscribeToSaveResponse(this.indicateurSMIService.create(indicateurSMI));
    }
  }

  private createFromForm(): IIndicateurSMI {
    return {
      ...new IndicateurSMI(),
      id: this.editForm.get(['id'])!.value,
      processus: this.editForm.get(['processus'])!.value,
      dateIdentification: this.editForm.get(['dateIdentification'])!.value,
      indicateur: this.editForm.get(['indicateur'])!.value,
      formuleCalcul: this.editForm.get(['formuleCalcul'])!.value,
      cible: this.editForm.get(['cible'])!.value,
      seuilTolerance: this.editForm.get(['seuilTolerance'])!.value,
      unite: this.editForm.get(['unite'])!.value,
      periodicite: this.editForm.get(['periodicite'])!.value,
      responsableCalcul: this.editForm.get(['responsableCalcul'])!.value,
      observations: this.editForm.get(['observations'])!.value,
      vigueur: this.editForm.get(['vigueur'])!.value,
      annee: this.editForm.get(['annee'])!.value,
      observation: this.editForm.get(['observation'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IIndicateurSMI>>): void {
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
