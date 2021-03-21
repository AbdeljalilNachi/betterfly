import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { BetterFlyTestModule } from '../../../test.module';
import { ConstatAuditUpdateComponent } from 'app/entities/constat-audit/constat-audit-update.component';
import { ConstatAuditService } from 'app/entities/constat-audit/constat-audit.service';
import { ConstatAudit } from 'app/shared/model/constat-audit.model';

describe('Component Tests', () => {
  describe('ConstatAudit Management Update Component', () => {
    let comp: ConstatAuditUpdateComponent;
    let fixture: ComponentFixture<ConstatAuditUpdateComponent>;
    let service: ConstatAuditService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BetterFlyTestModule],
        declarations: [ConstatAuditUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ConstatAuditUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ConstatAuditUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ConstatAuditService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ConstatAudit(123);
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
        const entity = new ConstatAudit();
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
