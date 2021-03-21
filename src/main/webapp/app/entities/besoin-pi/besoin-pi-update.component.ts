import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IBesoinPI, BesoinPI } from 'app/shared/model/besoin-pi.model';
import { BesoinPIService } from './besoin-pi.service';
import { IProcessusSMI } from 'app/shared/model/processus-smi.model';
import { ProcessusSMIService } from 'app/entities/processus-smi/processus-smi.service';

@Component({
  selector: 'jhi-besoin-pi-update',
  templateUrl: './besoin-pi-update.component.html',
})
export class BesoinPIUpdateComponent implements OnInit {
  isSaving = false;
  processussmis: IProcessusSMI[] = [];
  dateIdentificationDp: any;

  editForm = this.fb.group({
    id: [],
    dateIdentification: [],
    piPertinentes: [],
    pertinente: [],
    priseEnCharge: [],
    afficher: [],
    processus: [],
  });

  constructor(
    protected besoinPIService: BesoinPIService,
    protected processusSMIService: ProcessusSMIService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ besoinPI }) => {
      this.updateForm(besoinPI);

      this.processusSMIService.query().subscribe((res: HttpResponse<IProcessusSMI[]>) => (this.processussmis = res.body || []));
    });
  }

  updateForm(besoinPI: IBesoinPI): void {
    this.editForm.patchValue({
      id: besoinPI.id,
      dateIdentification: besoinPI.dateIdentification,
      piPertinentes: besoinPI.piPertinentes,
      pertinente: besoinPI.pertinente,
      priseEnCharge: besoinPI.priseEnCharge,
      afficher: besoinPI.afficher,
      processus: besoinPI.processus,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const besoinPI = this.createFromForm();
    if (besoinPI.id !== undefined) {
      this.subscribeToSaveResponse(this.besoinPIService.update(besoinPI));
    } else {
      this.subscribeToSaveResponse(this.besoinPIService.create(besoinPI));
    }
  }

  private createFromForm(): IBesoinPI {
    return {
      ...new BesoinPI(),
      id: this.editForm.get(['id'])!.value,
      dateIdentification: this.editForm.get(['dateIdentification'])!.value,
      piPertinentes: this.editForm.get(['piPertinentes'])!.value,
      pertinente: this.editForm.get(['pertinente'])!.value,
      priseEnCharge: this.editForm.get(['priseEnCharge'])!.value,
      afficher: this.editForm.get(['afficher'])!.value,
      processus: this.editForm.get(['processus'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBesoinPI>>): void {
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
