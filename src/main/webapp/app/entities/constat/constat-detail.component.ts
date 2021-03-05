import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IConstat } from 'app/shared/model/constat.model';

@Component({
  selector: 'jhi-constat-detail',
  templateUrl: './constat-detail.component.html',
})
export class ConstatDetailComponent implements OnInit {
  constat: IConstat | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ constat }) => (this.constat = constat));
  }

  previousState(): void {
    window.history.back();
  }
}
