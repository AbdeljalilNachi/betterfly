import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IResultatIndicateur } from 'app/shared/model/resultat-indicateur.model';
import { ResultatIndicateurService } from './resultat-indicateur.service';

@Component({
  templateUrl: './resultat-indicateur-delete-dialog.component.html',
})
export class ResultatIndicateurDeleteDialogComponent {
  resultatIndicateur?: IResultatIndicateur;

  constructor(
    protected resultatIndicateurService: ResultatIndicateurService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.resultatIndicateurService.delete(id).subscribe(() => {
      this.eventManager.broadcast('resultatIndicateurListModification');
      this.activeModal.close();
    });
  }
}
