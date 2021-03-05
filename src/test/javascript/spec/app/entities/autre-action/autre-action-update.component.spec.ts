import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { BetterFlyTestModule } from '../../../test.module';
import { AutreActionUpdateComponent } from 'app/entities/autre-action/autre-action-update.component';
import { AutreActionService } from 'app/entities/autre-action/autre-action.service';
import { AutreAction } from 'app/shared/model/autre-action.model';

describe('Component Tests', () => {
  describe('AutreAction Management Update Component', () => {
    let comp: AutreActionUpdateComponent;
    let fixture: ComponentFixture<AutreActionUpdateComponent>;
    let service: AutreActionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BetterFlyTestModule],
        declarations: [AutreActionUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(AutreActionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AutreActionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AutreActionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AutreAction(123);
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
        const entity = new AutreAction();
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
