import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IConstat } from 'app/shared/model/constat.model';
import { ConstatService } from './constat.service';

@Component({
  templateUrl: './constat-delete-dialog.component.html',
})
export class ConstatDeleteDialogComponent {
  constat?: IConstat;

  constructor(protected constatService: ConstatService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.constatService.delete(id).subscribe(() => {
      this.eventManager.broadcast('constatListModification');
      this.activeModal.close();
    });
  }
}
