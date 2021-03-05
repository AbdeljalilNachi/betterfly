import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPolitiqueQSE } from 'app/shared/model/politique-qse.model';
import { PolitiqueQSEService } from './politique-qse.service';

@Component({
  templateUrl: './politique-qse-delete-dialog.component.html',
})
export class PolitiqueQSEDeleteDialogComponent {
  politiqueQSE?: IPolitiqueQSE;

  constructor(
    protected politiqueQSEService: PolitiqueQSEService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.politiqueQSEService.delete(id).subscribe(() => {
      this.eventManager.broadcast('politiqueQSEListModification');
      this.activeModal.close();
    });
  }
}
