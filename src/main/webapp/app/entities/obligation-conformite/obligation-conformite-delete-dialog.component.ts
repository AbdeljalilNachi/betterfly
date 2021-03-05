import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IObligationConformite } from 'app/shared/model/obligation-conformite.model';
import { ObligationConformiteService } from './obligation-conformite.service';

@Component({
  templateUrl: './obligation-conformite-delete-dialog.component.html',
})
export class ObligationConformiteDeleteDialogComponent {
  obligationConformite?: IObligationConformite;

  constructor(
    protected obligationConformiteService: ObligationConformiteService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.obligationConformiteService.delete(id).subscribe(() => {
      this.eventManager.broadcast('obligationConformiteListModification');
      this.activeModal.close();
    });
  }
}
