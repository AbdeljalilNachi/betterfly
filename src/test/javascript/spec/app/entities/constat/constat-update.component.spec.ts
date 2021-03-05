import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { BetterFlyTestModule } from '../../../test.module';
import { ConstatUpdateComponent } from 'app/entities/constat/constat-update.component';
import { ConstatService } from 'app/entities/constat/constat.service';
import { Constat } from 'app/shared/model/constat.model';

describe('Component Tests', () => {
  describe('Constat Management Update Component', () => {
    let comp: ConstatUpdateComponent;
    let fixture: ComponentFixture<ConstatUpdateComponent>;
    let service: ConstatService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BetterFlyTestModule],
        declarations: [ConstatUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ConstatUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ConstatUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ConstatService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Constat(123);
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
        const entity = new Constat();
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
