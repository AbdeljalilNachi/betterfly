import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { BetterFlyTestModule } from '../../../test.module';
import { AnalyseSWOTUpdateComponent } from 'app/entities/analyse-swot/analyse-swot-update.component';
import { AnalyseSWOTService } from 'app/entities/analyse-swot/analyse-swot.service';
import { AnalyseSWOT } from 'app/shared/model/analyse-swot.model';

describe('Component Tests', () => {
  describe('AnalyseSWOT Management Update Component', () => {
    let comp: AnalyseSWOTUpdateComponent;
    let fixture: ComponentFixture<AnalyseSWOTUpdateComponent>;
    let service: AnalyseSWOTService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BetterFlyTestModule],
        declarations: [AnalyseSWOTUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(AnalyseSWOTUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AnalyseSWOTUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AnalyseSWOTService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AnalyseSWOT(123);
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
        const entity = new AnalyseSWOT();
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
