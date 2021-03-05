import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { BetterFlyTestModule } from '../../../test.module';
import { ProcessusSMIUpdateComponent } from 'app/entities/processus-smi/processus-smi-update.component';
import { ProcessusSMIService } from 'app/entities/processus-smi/processus-smi.service';
import { ProcessusSMI } from 'app/shared/model/processus-smi.model';

describe('Component Tests', () => {
  describe('ProcessusSMI Management Update Component', () => {
    let comp: ProcessusSMIUpdateComponent;
    let fixture: ComponentFixture<ProcessusSMIUpdateComponent>;
    let service: ProcessusSMIService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BetterFlyTestModule],
        declarations: [ProcessusSMIUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ProcessusSMIUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProcessusSMIUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProcessusSMIService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ProcessusSMI(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new ProcessusSMI();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
