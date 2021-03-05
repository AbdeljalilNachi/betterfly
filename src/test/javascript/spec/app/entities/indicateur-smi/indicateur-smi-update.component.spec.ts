import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { BetterFlyTestModule } from '../../../test.module';
import { IndicateurSMIUpdateComponent } from 'app/entities/indicateur-smi/indicateur-smi-update.component';
import { IndicateurSMIService } from 'app/entities/indicateur-smi/indicateur-smi.service';
import { IndicateurSMI } from 'app/shared/model/indicateur-smi.model';

describe('Component Tests', () => {
  describe('IndicateurSMI Management Update Component', () => {
    let comp: IndicateurSMIUpdateComponent;
    let fixture: ComponentFixture<IndicateurSMIUpdateComponent>;
    let service: IndicateurSMIService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BetterFlyTestModule],
        declarations: [IndicateurSMIUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(IndicateurSMIUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(IndicateurSMIUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(IndicateurSMIService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new IndicateurSMI(123);
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
        const entity = new IndicateurSMI();
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
