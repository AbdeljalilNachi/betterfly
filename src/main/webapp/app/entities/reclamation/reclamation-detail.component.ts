import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IReclamation } from 'app/shared/model/reclamation.model';

@Component({
  selector: 'jhi-reclamation-detail',
  templateUrl: './reclamation-detail.component.html',
})
export class ReclamationDetailComponent implements OnInit {
  reclamation: IReclamation | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ reclamation }) => (this.reclamation = reclamation));
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  previousState(): void {
    window.history.back();
  }
}
