import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { INonConformite } from 'app/shared/model/non-conformite.model';
import { NonConformiteService } from './non-conformite.service';

@Component({
  templateUrl: './non-conformite-delete-dialog.component.html',
})
export class NonConformiteDeleteDialogComponent {
  nonConformite?: INonConformite;

  constructor(
    protected nonConformiteService: NonConformiteService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.nonConformiteService.delete(id).subscribe(() => {
      this.eventManager.broadcast('nonConformiteListModification');
      this.activeModal.close();
    });
  }
}
