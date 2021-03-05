import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { BetterFlyTestModule } from '../../../test.module';
import { PolitiqueQSEUpdateComponent } from 'app/entities/politique-qse/politique-qse-update.component';
import { PolitiqueQSEService } from 'app/entities/politique-qse/politique-qse.service';
import { PolitiqueQSE } from 'app/shared/model/politique-qse.model';

describe('Component Tests', () => {
  describe('PolitiqueQSE Management Update Component', () => {
    let comp: PolitiqueQSEUpdateComponent;
    let fixture: ComponentFixture<PolitiqueQSEUpdateComponent>;
    let service: PolitiqueQSEService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BetterFlyTestModule],
        declarations: [PolitiqueQSEUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PolitiqueQSEUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PolitiqueQSEUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PolitiqueQSEService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PolitiqueQSE(123);
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
        const entity = new PolitiqueQSE();
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
