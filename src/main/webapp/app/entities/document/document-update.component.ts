import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IDocument, Document } from 'app/shared/model/document.model';
import { DocumentService } from './document.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IProcessusSMI } from 'app/shared/model/processus-smi.model';
import { ProcessusSMIService } from 'app/entities/processus-smi/processus-smi.service';

@Component({
  selector: 'jhi-document-update',
  templateUrl: './document-update.component.html',
})
export class DocumentUpdateComponent implements OnInit {
  isSaving = false;
  processussmis: IProcessusSMI[] = [];
  dateDp: any;

  editForm = this.fb.group({
    id: [],
    date: [],
    intitule: [],
    code: [],
    version: [],
    type: [],
    pieceJointe: [],
    pieceJointeContentType: [],
    enApplication: [],
    appouve: [],
    processus: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected documentService: DocumentService,
    protected processusSMIService: ProcessusSMIService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ document }) => {
      this.updateForm(document);

      this.processusSMIService.query().subscribe((res: HttpResponse<IProcessusSMI[]>) => (this.processussmis = res.body || []));
    });
  }

  updateForm(document: IDocument): void {
    this.editForm.patchValue({
      id: document.id,
      date: document.date,
      intitule: document.intitule,
      code: document.code,
      version: document.version,
      type: document.type,
      pieceJointe: document.pieceJointe,
      pieceJointeContentType: document.pieceJointeContentType,
      enApplication: document.enApplication,
      appouve: document.appouve,
      processus: document.processus,
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
    const document = this.createFromForm();
    if (document.id !== undefined) {
      this.subscribeToSaveResponse(this.documentService.update(document));
    } else {
      this.subscribeToSaveResponse(this.documentService.create(document));
    }
  }

  private createFromForm(): IDocument {
    return {
      ...new Document(),
      id: this.editForm.get(['id'])!.value,
      date: this.editForm.get(['date'])!.value,
      intitule: this.editForm.get(['intitule'])!.value,
      code: this.editForm.get(['code'])!.value,
      version: this.editForm.get(['version'])!.value,
      type: this.editForm.get(['type'])!.value,
      pieceJointeContentType: this.editForm.get(['pieceJointeContentType'])!.value,
      pieceJointe: this.editForm.get(['pieceJointe'])!.value,
      enApplication: this.editForm.get(['enApplication'])!.value,
      appouve: this.editForm.get(['appouve'])!.value,
      processus: this.editForm.get(['processus'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDocument>>): void {
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
