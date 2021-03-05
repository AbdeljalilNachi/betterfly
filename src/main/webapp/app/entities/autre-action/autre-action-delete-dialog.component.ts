import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAutreAction } from 'app/shared/model/autre-action.model';
import { AutreActionService } from './autre-action.service';

@Component({
  templateUrl: './autre-action-delete-dialog.component.html',
})
export class AutreActionDeleteDialogComponent {
  autreAction?: IAutreAction;

  constructor(
    protected autreActionService: AutreActionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.autreActionService.delete(id).subscribe(() => {
      this.eventManager.broadcast('autreActionListModification');
      this.activeModal.close();
    });
  }
}
