import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProcessus } from 'app/shared/model/processus.model';
import { ProcessusService } from './processus.service';

@Component({
  templateUrl: './processus-delete-dialog.component.html',
})
export class ProcessusDeleteDialogComponent {
  processus?: IProcessus;

  constructor(protected processusService: ProcessusService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.processusService.delete(id).subscribe(() => {
      this.eventManager.broadcast('processusListModification');
      this.activeModal.close();
    });
  }
}
