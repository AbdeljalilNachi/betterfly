import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { BetterFlyTestModule } from '../../../test.module';
import { AuditUpdateComponent } from 'app/entities/audit/audit-update.component';
import { AuditService } from 'app/entities/audit/audit.service';
import { Audit } from 'app/shared/model/audit.model';

describe('Component Tests', () => {
  describe('Audit Management Update Component', () => {
    let comp: AuditUpdateComponent;
    let fixture: ComponentFixture<AuditUpdateComponent>;
    let service: AuditService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BetterFlyTestModule],
        declarations: [AuditUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(AuditUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AuditUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AuditService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Audit(123);
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
        const entity = new Audit();
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
