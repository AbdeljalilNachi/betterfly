import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IAudit, Audit } from 'app/shared/model/audit.model';
import { AuditService } from './audit.service';
import { IProcessusSMI } from 'app/shared/model/processus-smi.model';
import { ProcessusSMIService } from 'app/entities/processus-smi/processus-smi.service';

@Component({
  selector: 'jhi-audit-update',
  templateUrl: './audit-update.component.html',
})
export class AuditUpdateComponent implements OnInit {
  isSaving = false;
  processussmis: IProcessusSMI[] = [];
  dateAuditDp: any;

  editForm = this.fb.group({
    id: [],
    dateAudit: [],
    typeAudit: [],
    auditeur: [],
    standard: [],
    statut: [],
    conclusion: [],
    processus: [],
  });

  constructor(
    protected auditService: AuditService,
    protected processusSMIService: ProcessusSMIService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ audit }) => {
      this.updateForm(audit);

      this.processusSMIService.query().subscribe((res: HttpResponse<IProcessusSMI[]>) => (this.processussmis = res.body || []));
    });
  }

  updateForm(audit: IAudit): void {
    this.editForm.patchValue({
      id: audit.id,
      dateAudit: audit.dateAudit,
      typeAudit: audit.typeAudit,
      auditeur: audit.auditeur,
      standard: audit.standard,
      statut: audit.statut,
      conclusion: audit.conclusion,
      processus: audit.processus,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const audit = this.createFromForm();
    if (audit.id !== undefined) {
      this.subscribeToSaveResponse(this.auditService.update(audit));
    } else {
      this.subscribeToSaveResponse(this.auditService.create(audit));
    }
  }

  private createFromForm(): IAudit {
    return {
      ...new Audit(),
      id: this.editForm.get(['id'])!.value,
      dateAudit: this.editForm.get(['dateAudit'])!.value,
      typeAudit: this.editForm.get(['typeAudit'])!.value,
      auditeur: this.editForm.get(['auditeur'])!.value,
      standard: this.editForm.get(['standard'])!.value,
      statut: this.editForm.get(['statut'])!.value,
      conclusion: this.editForm.get(['conclusion'])!.value,
      processus: this.editForm.get(['processus'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAudit>>): void {
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
