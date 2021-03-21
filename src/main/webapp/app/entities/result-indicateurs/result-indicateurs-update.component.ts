import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IResultIndicateurs, ResultIndicateurs } from 'app/shared/model/result-indicateurs.model';
import { ResultIndicateursService } from './result-indicateurs.service';
import { IIndicateurSMI } from 'app/shared/model/indicateur-smi.model';
import { IndicateurSMIService } from 'app/entities/indicateur-smi/indicateur-smi.service';

@Component({
  selector: 'jhi-result-indicateurs-update',
  templateUrl: './result-indicateurs-update.component.html',
})
export class ResultIndicateursUpdateComponent implements OnInit {
  isSaving = false;
  indicateursmis: IIndicateurSMI[] = [];

  editForm = this.fb.group({
    id: [],
    annee: [],
    observation: [],
    indicateur: [],
  });

  constructor(
    protected resultIndicateursService: ResultIndicateursService,
    protected indicateurSMIService: IndicateurSMIService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ resultIndicateurs }) => {
      this.updateForm(resultIndicateurs);

      this.indicateurSMIService.query().subscribe((res: HttpResponse<IIndicateurSMI[]>) => (this.indicateursmis = res.body || []));
    });
  }

  updateForm(resultIndicateurs: IResultIndicateurs): void {
    this.editForm.patchValue({
      id: resultIndicateurs.id,
      annee: resultIndicateurs.annee,
      observation: resultIndicateurs.observation,
      indicateur: resultIndicateurs.indicateur,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const resultIndicateurs = this.createFromForm();
    if (resultIndicateurs.id !== undefined) {
      this.subscribeToSaveResponse(this.resultIndicateursService.update(resultIndicateurs));
    } else {
      this.subscribeToSaveResponse(this.resultIndicateursService.create(resultIndicateurs));
    }
  }

  private createFromForm(): IResultIndicateurs {
    return {
      ...new ResultIndicateurs(),
      id: this.editForm.get(['id'])!.value,
      annee: this.editForm.get(['annee'])!.value,
      observation: this.editForm.get(['observation'])!.value,
      indicateur: this.editForm.get(['indicateur'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IResultIndicateurs>>): void {
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

  trackById(index: number, item: IIndicateurSMI): any {
    return item.id;
  }
}
