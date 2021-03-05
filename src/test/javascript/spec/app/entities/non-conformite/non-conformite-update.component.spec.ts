import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { BetterFlyTestModule } from '../../../test.module';
import { NonConformiteUpdateComponent } from 'app/entities/non-conformite/non-conformite-update.component';
import { NonConformiteService } from 'app/entities/non-conformite/non-conformite.service';
import { NonConformite } from 'app/shared/model/non-conformite.model';

describe('Component Tests', () => {
  describe('NonConformite Management Update Component', () => {
    let comp: NonConformiteUpdateComponent;
    let fixture: ComponentFixture<NonConformiteUpdateComponent>;
    let service: NonConformiteService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BetterFlyTestModule],
        declarations: [NonConformiteUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(NonConformiteUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(NonConformiteUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(NonConformiteService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new NonConformite(123);
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
        const entity = new NonConformite();
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
