import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { BetterFlyTestModule } from '../../../test.module';
import { PlanificationRDDUpdateComponent } from 'app/entities/planification-rdd/planification-rdd-update.component';
import { PlanificationRDDService } from 'app/entities/planification-rdd/planification-rdd.service';
import { PlanificationRDD } from 'app/shared/model/planification-rdd.model';

describe('Component Tests', () => {
  describe('PlanificationRDD Management Update Component', () => {
    let comp: PlanificationRDDUpdateComponent;
    let fixture: ComponentFixture<PlanificationRDDUpdateComponent>;
    let service: PlanificationRDDService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BetterFlyTestModule],
        declarations: [PlanificationRDDUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PlanificationRDDUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PlanificationRDDUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PlanificationRDDService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PlanificationRDD(123);
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
        const entity = new PlanificationRDD();
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
