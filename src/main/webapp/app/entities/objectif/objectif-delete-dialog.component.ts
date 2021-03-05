import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IObjectif } from 'app/shared/model/objectif.model';
import { ObjectifService } from './objectif.service';

@Component({
  templateUrl: './objectif-delete-dialog.component.html',
})
export class ObjectifDeleteDialogComponent {
  objectif?: IObjectif;

  constructor(protected objectifService: ObjectifService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.objectifService.delete(id).subscribe(() => {
      this.eventManager.broadcast('objectifListModification');
      this.activeModal.close();
    });
  }
}
