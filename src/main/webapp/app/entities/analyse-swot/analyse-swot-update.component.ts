import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IAnalyseSWOT, AnalyseSWOT } from 'app/shared/model/analyse-swot.model';
import { AnalyseSWOTService } from './analyse-swot.service';
import { IProcessusSMI } from 'app/shared/model/processus-smi.model';
import { ProcessusSMIService } from 'app/entities/processus-smi/processus-smi.service';

@Component({
  selector: 'jhi-analyse-swot-update',
  templateUrl: './analyse-swot-update.component.html',
})
export class AnalyseSWOTUpdateComponent implements OnInit {
  isSaving = false;
  processussmis: IProcessusSMI[] = [];
  dateIdentificationDp: any;

  editForm = this.fb.group({
    id: [],
    dateIdentification: [],
    description: [],
    pilote: [],
    type: [],
    bu: [],
    commentaire: [],
    afficher: [],
    processus: [],
  });

  constructor(
    protected analyseSWOTService: AnalyseSWOTService,
    protected processusSMIService: ProcessusSMIService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ analyseSWOT }) => {
      this.updateForm(analyseSWOT);

      this.processusSMIService.query().subscribe((res: HttpResponse<IProcessusSMI[]>) => (this.processussmis = res.body || []));
    });
  }

  updateForm(analyseSWOT: IAnalyseSWOT): void {
    this.editForm.patchValue({
      id: analyseSWOT.id,
      dateIdentification: analyseSWOT.dateIdentification,
      description: analyseSWOT.description,
      pilote: analyseSWOT.pilote,
      type: analyseSWOT.type,
      bu: analyseSWOT.bu,
      commentaire: analyseSWOT.commentaire,
      afficher: analyseSWOT.afficher,
      processus: analyseSWOT.processus,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const analyseSWOT = this.createFromForm();
    if (analyseSWOT.id !== undefined) {
      this.subscribeToSaveResponse(this.analyseSWOTService.update(analyseSWOT));
    } else {
      this.subscribeToSaveResponse(this.analyseSWOTService.create(analyseSWOT));
    }
  }

  private createFromForm(): IAnalyseSWOT {
    return {
      ...new AnalyseSWOT(),
      id: this.editForm.get(['id'])!.value,
      dateIdentification: this.editForm.get(['dateIdentification'])!.value,
      description: this.editForm.get(['description'])!.value,
      pilote: this.editForm.get(['pilote'])!.value,
      type: this.editForm.get(['type'])!.value,
      bu: this.editForm.get(['bu'])!.value,
      commentaire: this.editForm.get(['commentaire'])!.value,
      afficher: this.editForm.get(['afficher'])!.value,
      processus: this.editForm.get(['processus'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAnalyseSWOT>>): void {
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
