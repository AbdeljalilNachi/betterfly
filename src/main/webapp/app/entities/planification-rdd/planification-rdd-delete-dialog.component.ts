import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPlanificationRDD } from 'app/shared/model/planification-rdd.model';
import { PlanificationRDDService } from './planification-rdd.service';

@Component({
  templateUrl: './planification-rdd-delete-dialog.component.html',
})
export class PlanificationRDDDeleteDialogComponent {
  planificationRDD?: IPlanificationRDD;

  constructor(
    protected planificationRDDService: PlanificationRDDService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.planificationRDDService.delete(id).subscribe(() => {
      this.eventManager.broadcast('planificationRDDListModification');
      this.activeModal.close();
    });
  }
}
