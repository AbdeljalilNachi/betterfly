import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IPlanificationRDD } from 'app/shared/model/planification-rdd.model';

@Component({
  selector: 'jhi-planification-rdd-detail',
  templateUrl: './planification-rdd-detail.component.html',
})
export class PlanificationRDDDetailComponent implements OnInit {
  planificationRDD: IPlanificationRDD | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ planificationRDD }) => (this.planificationRDD = planificationRDD));
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
