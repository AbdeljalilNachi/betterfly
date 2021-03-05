import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IObjectif, Objectif } from 'app/shared/model/objectif.model';
import { ObjectifService } from './objectif.service';

@Component({
  selector: 'jhi-objectif-update',
  templateUrl: './objectif-update.component.html',
})
export class ObjectifUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    processus: [],
    axedelapolitiqueqse: [],
    objectifqse: [],
    indicateur: [],
    origine: [],
  });

  constructor(protected objectifService: ObjectifService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ objectif }) => {
      this.updateForm(objectif);
    });
  }

  updateForm(objectif: IObjectif): void {
    this.editForm.patchValue({
      id: objectif.id,
      processus: objectif.processus,
      axedelapolitiqueqse: objectif.axedelapolitiqueqse,
      objectifqse: objectif.objectifqse,
      indicateur: objectif.indicateur,
      origine: objectif.origine,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const objectif = this.createFromForm();
    if (objectif.id !== undefined) {
      this.subscribeToSaveResponse(this.objectifService.update(objectif));
    } else {
      this.subscribeToSaveResponse(this.objectifService.create(objectif));
    }
  }

  private createFromForm(): IObjectif {
    return {
      ...new Objectif(),
      id: this.editForm.get(['id'])!.value,
      processus: this.editForm.get(['processus'])!.value,
      axedelapolitiqueqse: this.editForm.get(['axedelapolitiqueqse'])!.value,
      objectifqse: this.editForm.get(['objectifqse'])!.value,
      indicateur: this.editForm.get(['indicateur'])!.value,
      origine: this.editForm.get(['origine'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IObjectif>>): void {
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
}
