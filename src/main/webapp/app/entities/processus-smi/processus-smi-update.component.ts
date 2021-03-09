import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IProcessusSMI, ProcessusSMI } from 'app/shared/model/processus-smi.model';
import { IIndicateurSMI } from 'app/shared/model/indicateur-smi.model';
import { ProcessusSMIService } from './processus-smi.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { UserService } from 'app/core/user/user.service';


@Component({
  selector: 'jhi-processus-smi-update',
  templateUrl: './processus-smi-update.component.html',
})
export class ProcessusSMIUpdateComponent implements OnInit {
  isSaving = false;
  dateDp: any;
  users: String[] | null = null;

  indicateurs: IIndicateurSMI[] = [];


  editForm = this.fb.group({
    id: [],
    processus: [],
    date: [],
    version: [],
    pilote: [],
    finalite: [],
    ficheProcessus: [],
    ficheProcessusContentType: [],
    type: [],
    vigueur: [],
    indicateurs: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected processusSMIService: ProcessusSMIService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
    private userService: UserService
  ) {}

  ngOnInit(): void {
    this.loadAll();
    this.activatedRoute.data.subscribe(({ processusSMI }) => {
      this.updateForm(processusSMI);
    });
  }

  updateForm(processusSMI: IProcessusSMI): void {
    this.editForm.patchValue({
      id: processusSMI.id,
      processus: processusSMI.processus,
      date: processusSMI.date,
      version: processusSMI.version,
      pilote: processusSMI.pilote,
      finalite: processusSMI.finalite,
      ficheProcessus: processusSMI.ficheProcessus,
      ficheProcessusContentType: processusSMI.ficheProcessusContentType,
      type: processusSMI.type,
      vigueur: processusSMI.vigueur,
      indicateurs: processusSMI.indicateurs,
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: any, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('betterFlyApp.error', { message: err.message })
      );
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const processusSMI = this.createFromForm();
    if (processusSMI.id !== undefined) {
      this.subscribeToSaveResponse(this.processusSMIService.update(processusSMI));
    } else {
      this.subscribeToSaveResponse(this.processusSMIService.create(processusSMI));
    }
  }

  private createFromForm(): IProcessusSMI {
    return {
      ...new ProcessusSMI(),
      id: this.editForm.get(['id'])!.value,
      processus: this.editForm.get(['processus'])!.value,
      date: this.editForm.get(['date'])!.value,
      version: this.editForm.get(['version'])!.value,
      pilote: this.editForm.get(['pilote'])!.value,
      finalite: this.editForm.get(['finalite'])!.value,
      ficheProcessusContentType: this.editForm.get(['ficheProcessusContentType'])!.value,
      ficheProcessus: this.editForm.get(['ficheProcessus'])!.value,
      type: this.editForm.get(['type'])!.value,
      vigueur: this.editForm.get(['vigueur'])!.value,
      indicateurs: this.editForm.get(['indicateurs'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProcessusSMI>>): void {
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
    this.userService .getLogins()
      .subscribe((res: String[]) => this.onSuccessLogins(res));

  }

  private onSuccessLogins(users: String[] | null): void {
    this.users = users;
  }

  trackById(index: number, item: IIndicateurSMI): any {
    return item.id;
  }


  getSelected(selectedVals: IIndicateurSMI[], option: IIndicateurSMI): IIndicateurSMI {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }

 



}
