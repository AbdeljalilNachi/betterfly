import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IRisque, Risque } from 'app/shared/model/risque.model';
import { RisqueService } from './risque.service';
import { ProcessusSMIService } from 'app/entities/processus-smi/processus-smi.service.ts';
// import { ActionService } from 'app/entities/action/action.service.ts';

@Component({
  selector: 'jhi-risque-update',
  templateUrl: './risque-update.component.html',
})
export class RisqueUpdateComponent implements OnInit {
  isSaving = false;
  dateIdentificationDp: any;
  pros: String[] | null = null;
  // actions: String[] | null = null;


  editForm = this.fb.group({
    id: [],
    processus: [],
    dateIdentification: [],
    description: [],
    causePotentielle: [],
    effetPotentiel: [],
    type: [],
    gravite: [],
    probabilite: [],
    criticite: [],
    traitement: [],
    commentaire: [],
    origine: [],
  //  action : [],
  });

  constructor(protected risqueService: RisqueService, 
    protected activatedRoute: ActivatedRoute, private fb: FormBuilder,
    private processusSMIService : ProcessusSMIService  , 
  //  private actionService : ActionService  
    ) {}

  ngOnInit(): void {
    this.loadAll() ;
    this.activatedRoute.data.subscribe(({ risque }) => {
      this.updateForm(risque);
    });
  }

  updateForm(risque: IRisque): void {
    this.editForm.patchValue({
      id: risque.id,
      processus: risque.processus,
      dateIdentification: risque.dateIdentification,
      description: risque.description,
      causePotentielle: risque.causePotentielle,
      effetPotentiel: risque.effetPotentiel,
      type: risque.type,
      gravite: risque.gravite,
      probabilite: risque.probabilite,
      criticite: risque.criticite,
      traitement: risque.traitement,
      commentaire: risque.commentaire,
      origine: risque.origine,
  //    action :  risque.action 
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const risque = this.createFromForm();
    if (risque.id !== undefined) {
      this.subscribeToSaveResponse(this.risqueService.update(risque));
    } else {
      this.subscribeToSaveResponse(this.risqueService.create(risque));
    }
  }

  private createFromForm(): IRisque {
    return {
      ...new Risque(),
      id: this.editForm.get(['id'])!.value,
      processus: this.editForm.get(['processus'])!.value,
      dateIdentification: this.editForm.get(['dateIdentification'])!.value,
      description: this.editForm.get(['description'])!.value,
      causePotentielle: this.editForm.get(['causePotentielle'])!.value,
      effetPotentiel: this.editForm.get(['effetPotentiel'])!.value,
      type: this.editForm.get(['type'])!.value,
      gravite: this.editForm.get(['gravite'])!.value,
      probabilite: this.editForm.get(['probabilite'])!.value,
      criticite: this.editForm.get(['criticite'])!.value,
      traitement: this.editForm.get(['traitement'])!.value,
      commentaire: this.editForm.get(['commentaire'])!.value,
      origine: this.editForm.get(['origine'])!.value,

   //   action: this.editForm.get(['action'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRisque>>): void {
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

  //  this.actionService .getActions()    .subscribe((res: String[]) => this.onSuccessActions(res));


  }

  private onSuccessLogins(pros: String[] | null): void {
    this.pros = pros;
  }

 
}
