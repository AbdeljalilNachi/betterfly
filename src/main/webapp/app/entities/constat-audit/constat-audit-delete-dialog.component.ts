import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IConstatAudit } from 'app/shared/model/constat-audit.model';
import { ConstatAuditService } from './constat-audit.service';

@Component({
  templateUrl: './constat-audit-delete-dialog.component.html',
})
export class ConstatAuditDeleteDialogComponent {
  constatAudit?: IConstatAudit;

  constructor(
    protected constatAuditService: ConstatAuditService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.constatAuditService.delete(id).subscribe(() => {
      this.eventManager.broadcast('constatAuditListModification');
      this.activeModal.close();
    });
  }
}
