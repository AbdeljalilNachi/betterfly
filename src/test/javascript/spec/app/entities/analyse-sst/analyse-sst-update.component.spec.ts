import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { BetterFlyTestModule } from '../../../test.module';
import { AnalyseSSTUpdateComponent } from 'app/entities/analyse-sst/analyse-sst-update.component';
import { AnalyseSSTService } from 'app/entities/analyse-sst/analyse-sst.service';
import { AnalyseSST } from 'app/shared/model/analyse-sst.model';

describe('Component Tests', () => {
  describe('AnalyseSST Management Update Component', () => {
    let comp: AnalyseSSTUpdateComponent;
    let fixture: ComponentFixture<AnalyseSSTUpdateComponent>;
    let service: AnalyseSSTService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BetterFlyTestModule],
        declarations: [AnalyseSSTUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(AnalyseSSTUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AnalyseSSTUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AnalyseSSTService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AnalyseSST(123);
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
        const entity = new AnalyseSST();
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
