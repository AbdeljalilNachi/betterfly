import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProcessusSMI } from 'app/shared/model/processus-smi.model';
import { ProcessusSMIService } from './processus-smi.service';

@Component({
  templateUrl: './processus-smi-delete-dialog.component.html',
})
export class ProcessusSMIDeleteDialogComponent {
  processusSMI?: IProcessusSMI;

  constructor(
    protected processusSMIService: ProcessusSMIService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.processusSMIService.delete(id).subscribe(() => {
      this.eventManager.broadcast('processusSMIListModification');
      this.activeModal.close();
    });
  }
}
