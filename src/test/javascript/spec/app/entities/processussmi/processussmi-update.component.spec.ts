import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { BetterFlyTestModule } from '../../../test.module';
import { ProcessussmiUpdateComponent } from 'app/entities/processussmi/processussmi-update.component';
import { ProcessussmiService } from 'app/entities/processussmi/processussmi.service';
import { Processussmi } from 'app/shared/model/processussmi.model';

describe('Component Tests', () => {
  describe('Processussmi Management Update Component', () => {
    let comp: ProcessussmiUpdateComponent;
    let fixture: ComponentFixture<ProcessussmiUpdateComponent>;
    let service: ProcessussmiService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BetterFlyTestModule],
        declarations: [ProcessussmiUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ProcessussmiUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProcessussmiUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProcessussmiService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Processussmi(123);
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
        const entity = new Processussmi();
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
