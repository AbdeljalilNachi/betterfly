import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPolitiqueQSE, PolitiqueQSE } from 'app/shared/model/politique-qse.model';
import { PolitiqueQSEService } from './politique-qse.service';
import { IProcessusSMI } from 'app/shared/model/processus-smi.model';
import { ProcessusSMIService } from 'app/entities/processus-smi/processus-smi.service';
import { IIndicateurSMI } from 'app/shared/model/indicateur-smi.model';
import { IndicateurSMIService } from 'app/entities/indicateur-smi/indicateur-smi.service';

type SelectableEntity = IProcessusSMI | IIndicateurSMI;

@Component({
  selector: 'jhi-politique-qse-update',
  templateUrl: './politique-qse-update.component.html',
})
export class PolitiqueQSEUpdateComponent implements OnInit {
  isSaving = false;
  processussmis: IProcessusSMI[] = [];
  indicateursmis: IIndicateurSMI[] = [];
  dateDp: any;

  editForm = this.fb.group({
    id: [],
    date: [],
    axePolitiqueQSE: [],
    objectifQSE: [],
    vigueur: [],
    processus: [],
    indicateur: [],
  });

  constructor(
    protected politiqueQSEService: PolitiqueQSEService,
    protected processusSMIService: ProcessusSMIService,
    protected indicateurSMIService: IndicateurSMIService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ politiqueQSE }) => {
      this.updateForm(politiqueQSE);

      this.processusSMIService.query().subscribe((res: HttpResponse<IProcessusSMI[]>) => (this.processussmis = res.body || []));

      this.indicateurSMIService.query().subscribe((res: HttpResponse<IIndicateurSMI[]>) => (this.indicateursmis = res.body || []));
    });
  }

  updateForm(politiqueQSE: IPolitiqueQSE): void {
    this.editForm.patchValue({
      id: politiqueQSE.id,
      date: politiqueQSE.date,
      axePolitiqueQSE: politiqueQSE.axePolitiqueQSE,
      objectifQSE: politiqueQSE.objectifQSE,
      vigueur: politiqueQSE.vigueur,
      processus: politiqueQSE.processus,
      indicateur: politiqueQSE.indicateur,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const politiqueQSE = this.createFromForm();
    if (politiqueQSE.id !== undefined) {
      this.subscribeToSaveResponse(this.politiqueQSEService.update(politiqueQSE));
    } else {
      this.subscribeToSaveResponse(this.politiqueQSEService.create(politiqueQSE));
    }
  }

  private createFromForm(): IPolitiqueQSE {
    return {
      ...new PolitiqueQSE(),
      id: this.editForm.get(['id'])!.value,
      date: this.editForm.get(['date'])!.value,
      axePolitiqueQSE: this.editForm.get(['axePolitiqueQSE'])!.value,
      objectifQSE: this.editForm.get(['objectifQSE'])!.value,
      vigueur: this.editForm.get(['vigueur'])!.value,
      processus: this.editForm.get(['processus'])!.value,
      indicateur: this.editForm.get(['indicateur'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPolitiqueQSE>>): void {
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
