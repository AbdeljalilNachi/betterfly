import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IPlanificationRDD, PlanificationRDD } from 'app/shared/model/planification-rdd.model';
import { PlanificationRDDService } from './planification-rdd.service';
import { AlertError } from 'app/shared/alert/alert-error.model';

@Component({
  selector: 'jhi-planification-rdd-update',
  templateUrl: './planification-rdd-update.component.html',
})
export class PlanificationRDDUpdateComponent implements OnInit {
  isSaving = false;
  dateDp: any;

  editForm = this.fb.group({
    id: [],
    nRdd: [],
    date: [],
    realisee: [],
    presentation: [],
    presentationContentType: [],
    standard: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected planificationRDDService: PlanificationRDDService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ planificationRDD }) => {
      this.updateForm(planificationRDD);
    });
  }

  updateForm(planificationRDD: IPlanificationRDD): void {
    this.editForm.patchValue({
      id: planificationRDD.id,
      nRdd: planificationRDD.nRdd,
      date: planificationRDD.date,
      realisee: planificationRDD.realisee,
      presentation: planificationRDD.presentation,
      presentationContentType: planificationRDD.presentationContentType,
      standard: planificationRDD.standard,
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
    const planificationRDD = this.createFromForm();
    if (planificationRDD.id !== undefined) {
      this.subscribeToSaveResponse(this.planificationRDDService.update(planificationRDD));
    } else {
      this.subscribeToSaveResponse(this.planificationRDDService.create(planificationRDD));
    }
  }

  private createFromForm(): IPlanificationRDD {
    return {
      ...new PlanificationRDD(),
      id: this.editForm.get(['id'])!.value,
      nRdd: this.editForm.get(['nRdd'])!.value,
      date: this.editForm.get(['date'])!.value,
      realisee: this.editForm.get(['realisee'])!.value,
      presentationContentType: this.editForm.get(['presentationContentType'])!.value,
      presentation: this.editForm.get(['presentation'])!.value,
      standard: this.editForm.get(['standard'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPlanificationRDD>>): void {
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
