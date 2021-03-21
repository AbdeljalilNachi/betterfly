import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IConstatAudit, ConstatAudit } from 'app/shared/model/constat-audit.model';
import { ConstatAuditService } from './constat-audit.service';
import { IAction } from 'app/shared/model/action.model';
import { ActionService } from 'app/entities/action/action.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { IProcessusSMI } from 'app/shared/model/processus-smi.model';
import { ProcessusSMIService } from 'app/entities/processus-smi/processus-smi.service';
import { IAudit } from 'app/shared/model/audit.model';
import { AuditService } from 'app/entities/audit/audit.service';

type SelectableEntity = IAction | IUser | IProcessusSMI | IAudit;

@Component({
  selector: 'jhi-constat-audit-update',
  templateUrl: './constat-audit-update.component.html',
})
export class ConstatAuditUpdateComponent implements OnInit {
  isSaving = false;
  actions: IAction[] = [];
  users: IUser[] = [];
  processussmis: IProcessusSMI[] = [];
  audits: IAudit[] = [];

  editForm = this.fb.group({
    id: [],
    type: [],
    constat: [],
    origine: [],
    action: [],
    delegue: [],
    processus: [],
    audit: [],
  });

  constructor(
    protected constatAuditService: ConstatAuditService,
    protected actionService: ActionService,
    protected userService: UserService,
    protected processusSMIService: ProcessusSMIService,
    protected auditService: AuditService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ constatAudit }) => {
      this.updateForm(constatAudit);

      this.actionService.query().subscribe((res: HttpResponse<IAction[]>) => (this.actions = res.body || []));

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));

      this.processusSMIService.query().subscribe((res: HttpResponse<IProcessusSMI[]>) => (this.processussmis = res.body || []));

      this.auditService.query().subscribe((res: HttpResponse<IAudit[]>) => (this.audits = res.body || []));
    });
  }

  updateForm(constatAudit: IConstatAudit): void {
    this.editForm.patchValue({
      id: constatAudit.id,
      type: constatAudit.type,
      constat: constatAudit.constat,
      origine: constatAudit.origine,
      action: constatAudit.action,
      delegue: constatAudit.delegue,
      processus: constatAudit.processus,
      audit: constatAudit.audit,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const constatAudit = this.createFromForm();
    if (constatAudit.id !== undefined) {
      this.subscribeToSaveResponse(this.constatAuditService.update(constatAudit));
    } else {
      this.subscribeToSaveResponse(this.constatAuditService.create(constatAudit));
    }
  }

  private createFromForm(): IConstatAudit {
    return {
      ...new ConstatAudit(),
      id: this.editForm.get(['id'])!.value,
      type: this.editForm.get(['type'])!.value,
      constat: this.editForm.get(['constat'])!.value,
      origine: this.editForm.get(['origine'])!.value,
      action: this.editForm.get(['action'])!.value,
      delegue: this.editForm.get(['delegue'])!.value,
      processus: this.editForm.get(['processus'])!.value,
      audit: this.editForm.get(['audit'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IConstatAudit>>): void {
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
