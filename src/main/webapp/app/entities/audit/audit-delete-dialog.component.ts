import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAudit } from 'app/shared/model/audit.model';
import { AuditService } from './audit.service';

@Component({
  templateUrl: './audit-delete-dialog.component.html',
})
export class AuditDeleteDialogComponent {
  audit?: IAudit;

  constructor(protected auditService: AuditService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.auditService.delete(id).subscribe(() => {
      this.eventManager.broadcast('auditListModification');
      this.activeModal.close();
    });
  }
}
