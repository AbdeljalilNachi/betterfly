import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProcessus } from 'app/shared/model/processus.model';

@Component({
  selector: 'jhi-processus-detail',
  templateUrl: './processus-detail.component.html',
})
export class ProcessusDetailComponent implements OnInit {
  processus: IProcessus | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ processus }) => (this.processus = processus));
  }

  previousState(): void {
    window.history.back();
  }
}
