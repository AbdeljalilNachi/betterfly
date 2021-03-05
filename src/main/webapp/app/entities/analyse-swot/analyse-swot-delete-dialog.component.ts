import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAnalyseSWOT } from 'app/shared/model/analyse-swot.model';
import { AnalyseSWOTService } from './analyse-swot.service';

@Component({
  templateUrl: './analyse-swot-delete-dialog.component.html',
})
export class AnalyseSWOTDeleteDialogComponent {
  analyseSWOT?: IAnalyseSWOT;

  constructor(
    protected analyseSWOTService: AnalyseSWOTService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.analyseSWOTService.delete(id).subscribe(() => {
      this.eventManager.broadcast('analyseSWOTListModification');
      this.activeModal.close();
    });
  }
}
