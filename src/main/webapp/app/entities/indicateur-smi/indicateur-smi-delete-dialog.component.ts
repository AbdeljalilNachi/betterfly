import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IIndicateurSMI } from 'app/shared/model/indicateur-smi.model';
import { IndicateurSMIService } from './indicateur-smi.service';

@Component({
  templateUrl: './indicateur-smi-delete-dialog.component.html',
})
export class IndicateurSMIDeleteDialogComponent {
  indicateurSMI?: IIndicateurSMI;

  constructor(
    protected indicateurSMIService: IndicateurSMIService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.indicateurSMIService.delete(id).subscribe(() => {
      this.eventManager.broadcast('indicateurSMIListModification');
      this.activeModal.close();
    });
  }
}
