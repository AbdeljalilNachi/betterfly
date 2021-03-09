import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IObligationConformite, ObligationConformite } from 'app/shared/model/obligation-conformite.model';
import { ObligationConformiteService } from './obligation-conformite.service';
import { ProcessusSMIService } from 'app/entities/processus-smi/processus-smi.service.ts';


@Component({
  selector: 'jhi-obligation-conformite-update',
  templateUrl: './obligation-conformite-update.component.html',
})
export class ObligationConformiteUpdateComponent implements OnInit {
  isSaving = false;
  dateDp: any;
  pros: String[] | null = null;
  editForm = this.fb.group({
    id: [],
    date: [],
    rubrique: [],
    reference: [],
    num: [],
    exigence: [],
    applicable: [],
    conforme: [],
    statut: [],
    observation: [],
    processus: [],
    oRIGINE: [],
  });

  constructor(
    protected obligationConformiteService: ObligationConformiteService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
    ,
    private processusSMIService : ProcessusSMIService ) {}

  ngOnInit(): void {
    this.loadAll() ;
    this.activatedRoute.data.subscribe(({ obligationConformite }) => {
      this.updateForm(obligationConformite);
    });
  }

  updateForm(obligationConformite: IObligationConformite): void {
    this.editForm.patchValue({
      id: obligationConformite.id,
      date: obligationConformite.date,
      rubrique: obligationConformite.rubrique,
      reference: obligationConformite.reference,
      num: obligationConformite.num,
      exigence: obligationConformite.exigence,
      applicable: obligationConformite.applicable,
      conforme: obligationConformite.conforme,
      statut: obligationConformite.statut,
      observation: obligationConformite.observation,
      processus: obligationConformite.processus,
      oRIGINE: obligationConformite.oRIGINE,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const obligationConformite = this.createFromForm();
    if (obligationConformite.id !== undefined) {
      this.subscribeToSaveResponse(this.obligationConformiteService.update(obligationConformite));
    } else {
      this.subscribeToSaveResponse(this.obligationConformiteService.create(obligationConformite));
    }
  }

  private createFromForm(): IObligationConformite {
    return {
      ...new ObligationConformite(),
      id: this.editForm.get(['id'])!.value,
      date: this.editForm.get(['date'])!.value,
      rubrique: this.editForm.get(['rubrique'])!.value,
      reference: this.editForm.get(['reference'])!.value,
      num: this.editForm.get(['num'])!.value,
      exigence: this.editForm.get(['exigence'])!.value,
      applicable: this.editForm.get(['applicable'])!.value,
      conforme: this.editForm.get(['conforme'])!.value,
      statut: this.editForm.get(['statut'])!.value,
      observation: this.editForm.get(['observation'])!.value,
      processus: this.editForm.get(['processus'])!.value,
      oRIGINE: this.editForm.get(['oRIGINE'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IObligationConformite>>): void {
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
