import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAnalyseSST } from 'app/shared/model/analyse-sst.model';
import { AnalyseSSTService } from './analyse-sst.service';

@Component({
  templateUrl: './analyse-sst-delete-dialog.component.html',
})
export class AnalyseSSTDeleteDialogComponent {
  analyseSST?: IAnalyseSST;

  constructor(
    protected analyseSSTService: AnalyseSSTService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.analyseSSTService.delete(id).subscribe(() => {
      this.eventManager.broadcast('analyseSSTListModification');
      this.activeModal.close();
    });
  }
}
