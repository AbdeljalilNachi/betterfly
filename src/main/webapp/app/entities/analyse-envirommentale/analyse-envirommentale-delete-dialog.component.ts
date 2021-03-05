import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAnalyseEnvirommentale } from 'app/shared/model/analyse-envirommentale.model';
import { AnalyseEnvirommentaleService } from './analyse-envirommentale.service';

@Component({
  templateUrl: './analyse-envirommentale-delete-dialog.component.html',
})
export class AnalyseEnvirommentaleDeleteDialogComponent {
  analyseEnvirommentale?: IAnalyseEnvirommentale;

  constructor(
    protected analyseEnvirommentaleService: AnalyseEnvirommentaleService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.analyseEnvirommentaleService.delete(id).subscribe(() => {
      this.eventManager.broadcast('analyseEnvirommentaleListModification');
      this.activeModal.close();
    });
  }
}
