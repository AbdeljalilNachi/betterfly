import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IResultIndicateurs } from 'app/shared/model/result-indicateurs.model';
import { ResultIndicateursService } from './result-indicateurs.service';

@Component({
  templateUrl: './result-indicateurs-delete-dialog.component.html',
})
export class ResultIndicateursDeleteDialogComponent {
  resultIndicateurs?: IResultIndicateurs;

  constructor(
    protected resultIndicateursService: ResultIndicateursService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.resultIndicateursService.delete(id).subscribe(() => {
      this.eventManager.broadcast('resultIndicateursListModification');
      this.activeModal.close();
    });
  }
}
