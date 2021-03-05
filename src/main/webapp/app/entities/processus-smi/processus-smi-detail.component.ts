import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IProcessusSMI } from 'app/shared/model/processus-smi.model';

@Component({
  selector: 'jhi-processus-smi-detail',
  templateUrl: './processus-smi-detail.component.html',
})
export class ProcessusSMIDetailComponent implements OnInit {
  processusSMI: IProcessusSMI | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ processusSMI }) => (this.processusSMI = processusSMI));
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
