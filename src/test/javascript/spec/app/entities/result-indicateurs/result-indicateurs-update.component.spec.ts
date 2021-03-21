import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { BetterFlyTestModule } from '../../../test.module';
import { ResultIndicateursUpdateComponent } from 'app/entities/result-indicateurs/result-indicateurs-update.component';
import { ResultIndicateursService } from 'app/entities/result-indicateurs/result-indicateurs.service';
import { ResultIndicateurs } from 'app/shared/model/result-indicateurs.model';

describe('Component Tests', () => {
  describe('ResultIndicateurs Management Update Component', () => {
    let comp: ResultIndicateursUpdateComponent;
    let fixture: ComponentFixture<ResultIndicateursUpdateComponent>;
    let service: ResultIndicateursService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BetterFlyTestModule],
        declarations: [ResultIndicateursUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ResultIndicateursUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ResultIndicateursUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ResultIndicateursService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ResultIndicateurs(123);
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
        const entity = new ResultIndicateurs();
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
