import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { BetterFlyTestModule } from '../../../test.module';
import { ObligationConformiteUpdateComponent } from 'app/entities/obligation-conformite/obligation-conformite-update.component';
import { ObligationConformiteService } from 'app/entities/obligation-conformite/obligation-conformite.service';
import { ObligationConformite } from 'app/shared/model/obligation-conformite.model';

describe('Component Tests', () => {
  describe('ObligationConformite Management Update Component', () => {
    let comp: ObligationConformiteUpdateComponent;
    let fixture: ComponentFixture<ObligationConformiteUpdateComponent>;
    let service: ObligationConformiteService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BetterFlyTestModule],
        declarations: [ObligationConformiteUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ObligationConformiteUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ObligationConformiteUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ObligationConformiteService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ObligationConformite(123);
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
        const entity = new ObligationConformite();
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
