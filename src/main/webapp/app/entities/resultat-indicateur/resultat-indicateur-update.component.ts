import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IResultatIndicateur, ResultatIndicateur } from 'app/shared/model/resultat-indicateur.model';
import { ResultatIndicateurService } from './resultat-indicateur.service';

@Component({
  selector: 'jhi-resultat-indicateur-update',
  templateUrl: './resultat-indicateur-update.component.html',
})
export class ResultatIndicateurUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    mois: [],
    cible: [],
    resultat: [],
    indicateur: [],
  });

  constructor(
    protected resultatIndicateurService: ResultatIndicateurService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ resultatIndicateur }) => {
      this.updateForm(resultatIndicateur);
    });
  }

  updateForm(resultatIndicateur: IResultatIndicateur): void {
    this.editForm.patchValue({
      id: resultatIndicateur.id,
      mois: resultatIndicateur.mois,
      cible: resultatIndicateur.cible,
      resultat: resultatIndicateur.resultat,
      indicateur: resultatIndicateur.indicateur,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const resultatIndicateur = this.createFromForm();
    if (resultatIndicateur.id !== undefined) {
      this.subscribeToSaveResponse(this.resultatIndicateurService.update(resultatIndicateur));
    } else {
      this.subscribeToSaveResponse(this.resultatIndicateurService.create(resultatIndicateur));
    }
  }

  private createFromForm(): IResultatIndicateur {
    return {
      ...new ResultatIndicateur(),
      id: this.editForm.get(['id'])!.value,
      mois: this.editForm.get(['mois'])!.value,
      cible: this.editForm.get(['cible'])!.value,
      resultat: this.editForm.get(['resultat'])!.value,
      indicateur: this.editForm.get(['indicateur'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IResultatIndicateur>>): void {
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
