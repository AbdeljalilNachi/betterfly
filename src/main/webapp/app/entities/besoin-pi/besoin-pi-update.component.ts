import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IBesoinPI, BesoinPI } from 'app/shared/model/besoin-pi.model';
import { BesoinPIService } from './besoin-pi.service';

@Component({
  selector: 'jhi-besoin-pi-update',
  templateUrl: './besoin-pi-update.component.html',
})
export class BesoinPIUpdateComponent implements OnInit {
  isSaving = false;
  dateIdentificationDp: any;

  editForm = this.fb.group({
    id: [],
    processus: [],
    dateIdentification: [],
    piPertinentes: [],
    pertinente: [],
    priseEnCharge: [],
    afficher: [],
  });

  constructor(protected besoinPIService: BesoinPIService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ besoinPI }) => {
      this.updateForm(besoinPI);
    });
  }

  updateForm(besoinPI: IBesoinPI): void {
    this.editForm.patchValue({
      id: besoinPI.id,
      processus: besoinPI.processus,
      dateIdentification: besoinPI.dateIdentification,
      piPertinentes: besoinPI.piPertinentes,
      pertinente: besoinPI.pertinente,
      priseEnCharge: besoinPI.priseEnCharge,
      afficher: besoinPI.afficher,
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
      processus: this.editForm.get(['processus'])!.value,
      dateIdentification: this.editForm.get(['dateIdentification'])!.value,
      piPertinentes: this.editForm.get(['piPertinentes'])!.value,
      pertinente: this.editForm.get(['pertinente'])!.value,
      priseEnCharge: this.editForm.get(['priseEnCharge'])!.value,
      afficher: this.editForm.get(['afficher'])!.value,
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
}
