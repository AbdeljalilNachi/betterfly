import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IProcessusSMI, ProcessusSMI } from 'app/shared/model/processus-smi.model';
import { ProcessusSMIService } from './processus-smi.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { IAudit } from 'app/shared/model/audit.model';
import { AuditService } from 'app/entities/audit/audit.service';

type SelectableEntity = IUser | IAudit;

@Component({
  selector: 'jhi-processus-smi-update',
  templateUrl: './processus-smi-update.component.html',
})
export class ProcessusSMIUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];
  audits: IAudit[] = [];
  dateDp: any;

  editForm = this.fb.group({
    id: [],
    processus: [],
    date: [],
    version: [],
    finalite: [],
    ficheProcessus: [],
    ficheProcessusContentType: [],
    type: [],
    vigueur: [],
    pilote: [],
    audit: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected processusSMIService: ProcessusSMIService,
    protected userService: UserService,
    protected auditService: AuditService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ processusSMI }) => {
      this.updateForm(processusSMI);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));

      this.auditService.query().subscribe((res: HttpResponse<IAudit[]>) => (this.audits = res.body || []));
    });
  }

  updateForm(processusSMI: IProcessusSMI): void {
    this.editForm.patchValue({
      id: processusSMI.id,
      processus: processusSMI.processus,
      date: processusSMI.date,
      version: processusSMI.version,
      finalite: processusSMI.finalite,
      ficheProcessus: processusSMI.ficheProcessus,
      ficheProcessusContentType: processusSMI.ficheProcessusContentType,
      type: processusSMI.type,
      vigueur: processusSMI.vigueur,
      pilote: processusSMI.pilote,
      audit: processusSMI.audit,
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
    const processusSMI = this.createFromForm();
    if (processusSMI.id !== undefined) {
      this.subscribeToSaveResponse(this.processusSMIService.update(processusSMI));
    } else {
      this.subscribeToSaveResponse(this.processusSMIService.create(processusSMI));
    }
  }

  private createFromForm(): IProcessusSMI {
    return {
      ...new ProcessusSMI(),
      id: this.editForm.get(['id'])!.value,
      processus: this.editForm.get(['processus'])!.value,
      date: this.editForm.get(['date'])!.value,
      version: this.editForm.get(['version'])!.value,
      finalite: this.editForm.get(['finalite'])!.value,
      ficheProcessusContentType: this.editForm.get(['ficheProcessusContentType'])!.value,
      ficheProcessus: this.editForm.get(['ficheProcessus'])!.value,
      type: this.editForm.get(['type'])!.value,
      vigueur: this.editForm.get(['vigueur'])!.value,
      pilote: this.editForm.get(['pilote'])!.value,
      audit: this.editForm.get(['audit'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProcessusSMI>>): void {
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
