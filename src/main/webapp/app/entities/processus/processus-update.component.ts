import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IProcessus, Processus } from 'app/shared/model/processus.model';
import { ProcessusService } from './processus.service';
import { AlertError } from 'app/shared/alert/alert-error.model';

@Component({
  selector: 'jhi-processus-update',
  templateUrl: './processus-update.component.html',
})
export class ProcessusUpdateComponent implements OnInit {
  isSaving = false;
  dateDp: any;

  editForm = this.fb.group({
    id: [],
    processus: [],
    date: [],
    version: [],
    finalite: [],
    fiche: [],
    ficheContentType: [],
    type: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected processusService: ProcessusService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ processus }) => {
      this.updateForm(processus);
    });
  }

  updateForm(processus: IProcessus): void {
    this.editForm.patchValue({
      id: processus.id,
      processus: processus.processus,
      date: processus.date,
      version: processus.version,
      finalite: processus.finalite,
      fiche: processus.fiche,
      ficheContentType: processus.ficheContentType,
      type: processus.type,
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
    const processus = this.createFromForm();
    if (processus.id !== undefined) {
      this.subscribeToSaveResponse(this.processusService.update(processus));
    } else {
      this.subscribeToSaveResponse(this.processusService.create(processus));
    }
  }

  private createFromForm(): IProcessus {
    return {
      ...new Processus(),
      id: this.editForm.get(['id'])!.value,
      processus: this.editForm.get(['processus'])!.value,
      date: this.editForm.get(['date'])!.value,
      version: this.editForm.get(['version'])!.value,
      finalite: this.editForm.get(['finalite'])!.value,
      ficheContentType: this.editForm.get(['ficheContentType'])!.value,
      fiche: this.editForm.get(['fiche'])!.value,
      type: this.editForm.get(['type'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProcessus>>): void {
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
