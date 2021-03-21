import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IConstat, Constat } from 'app/shared/model/constat.model';
import { ConstatService } from './constat.service';
import { IAction } from 'app/shared/model/action.model';
import { ActionService } from 'app/entities/action/action.service';

@Component({
  selector: 'jhi-constat-update',
  templateUrl: './constat-update.component.html',
})
export class ConstatUpdateComponent implements OnInit {
  isSaving = false;
  actions: IAction[] = [];

  editForm = this.fb.group({
    id: [],
    processus: [],
    audit: [],
    constat: [],
    type: [],
    origine: [],
    action: [],
  });

  constructor(
    protected constatService: ConstatService,
    protected actionService: ActionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ constat }) => {
      this.updateForm(constat);

      this.actionService.query().subscribe((res: HttpResponse<IAction[]>) => (this.actions = res.body || []));
    });
  }

  updateForm(constat: IConstat): void {
    this.editForm.patchValue({
      id: constat.id,
      processus: constat.processus,
      audit: constat.audit,
      constat: constat.constat,
      type: constat.type,
      origine: constat.origine,
      action: constat.action,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const constat = this.createFromForm();
    if (constat.id !== undefined) {
      this.subscribeToSaveResponse(this.constatService.update(constat));
    } else {
      this.subscribeToSaveResponse(this.constatService.create(constat));
    }
  }

  private createFromForm(): IConstat {
    return {
      ...new Constat(),
      id: this.editForm.get(['id'])!.value,
      processus: this.editForm.get(['processus'])!.value,
      audit: this.editForm.get(['audit'])!.value,
      constat: this.editForm.get(['constat'])!.value,
      type: this.editForm.get(['type'])!.value,
      origine: this.editForm.get(['origine'])!.value,
      action: this.editForm.get(['action'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IConstat>>): void {
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

  trackById(index: number, item: IAction): any {
    return item.id;
  }
}
