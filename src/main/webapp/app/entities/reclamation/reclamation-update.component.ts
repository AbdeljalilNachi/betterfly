import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IReclamation, Reclamation } from 'app/shared/model/reclamation.model';
import { ReclamationService } from './reclamation.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { ProcessusSMIService } from 'app/entities/processus-smi/processus-smi.service.ts';


@Component({
  selector: 'jhi-reclamation-update',
  templateUrl: './reclamation-update.component.html',
})
export class ReclamationUpdateComponent implements OnInit {
  isSaving = false;
  dateDp: any;
  pros: String[] | null = null;
  editForm = this.fb.group({
    id: [],
    processus: [],
    date: [],
    description: [],
    justifiee: [],
    client: [],
    piecejointe: [],
    piecejointeContentType: [],
    origine: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected reclamationService: ReclamationService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
    ,
    private processusSMIService : ProcessusSMIService ) {}

  ngOnInit(): void {
    this.loadAll() ;
    this.activatedRoute.data.subscribe(({ reclamation }) => {
      this.updateForm(reclamation);
    });
  }

  updateForm(reclamation: IReclamation): void {
    this.editForm.patchValue({
      id: reclamation.id,
      processus: reclamation.processus,
      date: reclamation.date,
      description: reclamation.description,
      justifiee: reclamation.justifiee,
      client: reclamation.client,
      piecejointe: reclamation.piecejointe,
      piecejointeContentType: reclamation.piecejointeContentType,
      origine: reclamation.origine,
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: any, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('betterFlyApp.error', { message: err.message })
      );
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const reclamation = this.createFromForm();
    if (reclamation.id !== undefined) {
      this.subscribeToSaveResponse(this.reclamationService.update(reclamation));
    } else {
      this.subscribeToSaveResponse(this.reclamationService.create(reclamation));
    }
  }

  private createFromForm(): IReclamation {
    return {
      ...new Reclamation(),
      id: this.editForm.get(['id'])!.value,
      processus: this.editForm.get(['processus'])!.value,
      date: this.editForm.get(['date'])!.value,
      description: this.editForm.get(['description'])!.value,
      justifiee: this.editForm.get(['justifiee'])!.value,
      client: this.editForm.get(['client'])!.value,
      piecejointeContentType: this.editForm.get(['piecejointeContentType'])!.value,
      piecejointe: this.editForm.get(['piecejointe'])!.value,
      origine: this.editForm.get(['origine'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IReclamation>>): void {
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
