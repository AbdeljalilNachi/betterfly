import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { BetterFlyTestModule } from '../../../test.module';
import { BesoinPIUpdateComponent } from 'app/entities/besoin-pi/besoin-pi-update.component';
import { BesoinPIService } from 'app/entities/besoin-pi/besoin-pi.service';
import { BesoinPI } from 'app/shared/model/besoin-pi.model';

describe('Component Tests', () => {
  describe('BesoinPI Management Update Component', () => {
    let comp: BesoinPIUpdateComponent;
    let fixture: ComponentFixture<BesoinPIUpdateComponent>;
    let service: BesoinPIService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BetterFlyTestModule],
        declarations: [BesoinPIUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(BesoinPIUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BesoinPIUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BesoinPIService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new BesoinPI(123);
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
        const entity = new BesoinPI();
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
