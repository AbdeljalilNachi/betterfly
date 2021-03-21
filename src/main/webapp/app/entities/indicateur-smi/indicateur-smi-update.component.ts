import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IIndicateurSMI, IndicateurSMI } from 'app/shared/model/indicateur-smi.model';
import { IndicateurSMIService } from './indicateur-smi.service';
import { IProcessusSMI } from 'app/shared/model/processus-smi.model';
import { ProcessusSMIService } from 'app/entities/processus-smi/processus-smi.service';

@Component({
  selector: 'jhi-indicateur-smi-update',
  templateUrl: './indicateur-smi-update.component.html',
})
export class IndicateurSMIUpdateComponent implements OnInit {
  isSaving = false;
  processussmis: IProcessusSMI[] = [];
  dateIdentificationDp: any;

  editForm = this.fb.group({
    id: [],
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
    processus: [],
  });

  constructor(
    protected indicateurSMIService: IndicateurSMIService,
    protected processusSMIService: ProcessusSMIService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ indicateurSMI }) => {
      this.updateForm(indicateurSMI);

      this.processusSMIService.query().subscribe((res: HttpResponse<IProcessusSMI[]>) => (this.processussmis = res.body || []));
    });
  }

  updateForm(indicateurSMI: IIndicateurSMI): void {
    this.editForm.patchValue({
      id: indicateurSMI.id,
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
      processus: indicateurSMI.processus,
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
      processus: this.editForm.get(['processus'])!.value,
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

  trackById(index: number, item: IProcessusSMI): any {
    return item.id;
  }
}
