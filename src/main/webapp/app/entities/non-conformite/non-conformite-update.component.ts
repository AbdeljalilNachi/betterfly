import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { INonConformite, NonConformite } from 'app/shared/model/non-conformite.model';
import { NonConformiteService } from './non-conformite.service';
import { ProcessusSMIService } from 'app/entities/processus-smi/processus-smi.service.ts';


@Component({
  selector: 'jhi-non-conformite-update',
  templateUrl: './non-conformite-update.component.html',
})
export class NonConformiteUpdateComponent implements OnInit {
  isSaving = false;
  dateDp: any;
  pros: String[] | null = null;
  editForm = this.fb.group({
    id: [],
    processus: [],
    date: [],
    description: [],
    causesPotentielles: [],
    origine: [],
  });

  constructor(protected nonConformiteService: NonConformiteService, 
    protected activatedRoute: ActivatedRoute, private fb: FormBuilder,
    private processusSMIService : ProcessusSMIService ) {}

  ngOnInit(): void {
    this.loadAll() ;
    this.activatedRoute.data.subscribe(({ nonConformite }) => {
      this.updateForm(nonConformite);
    });
  }

  updateForm(nonConformite: INonConformite): void {
    this.editForm.patchValue({
      id: nonConformite.id,
      processus: nonConformite.processus,
      date: nonConformite.date,
      description: nonConformite.description,
      causesPotentielles: nonConformite.causesPotentielles,
      origine: nonConformite.origine,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const nonConformite = this.createFromForm();
    if (nonConformite.id !== undefined) {
      this.subscribeToSaveResponse(this.nonConformiteService.update(nonConformite));
    } else {
      this.subscribeToSaveResponse(this.nonConformiteService.create(nonConformite));
    }
  }

  private createFromForm(): INonConformite {
    return {
      ...new NonConformite(),
      id: this.editForm.get(['id'])!.value,
      processus: this.editForm.get(['processus'])!.value,
      date: this.editForm.get(['date'])!.value,
      description: this.editForm.get(['description'])!.value,
      causesPotentielles: this.editForm.get(['causesPotentielles'])!.value,
      origine: this.editForm.get(['origine'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INonConformite>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  private loadAll(): void {
    this.processusSMIService .getProcs()
      .subscribe((res: String[]) => this.onSuccessLogins(res));

  }

  private onSuccessLogins(pros: String[] | null): void {
    this.pros = pros;
  }
}
