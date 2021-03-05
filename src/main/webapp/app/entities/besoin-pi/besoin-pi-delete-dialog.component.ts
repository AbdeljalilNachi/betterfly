import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBesoinPI } from 'app/shared/model/besoin-pi.model';
import { BesoinPIService } from './besoin-pi.service';

@Component({
  templateUrl: './besoin-pi-delete-dialog.component.html',
})
export class BesoinPIDeleteDialogComponent {
  besoinPI?: IBesoinPI;

  constructor(protected besoinPIService: BesoinPIService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.besoinPIService.delete(id).subscribe(() => {
      this.eventManager.broadcast('besoinPIListModification');
      this.activeModal.close();
    });
  }
}
