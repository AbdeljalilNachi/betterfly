import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BetterFlyTestModule } from '../../../test.module';
import { AuditDetailComponent } from 'app/entities/audit/audit-detail.component';
import { Audit } from 'app/shared/model/audit.model';

describe('Component Tests', () => {
  describe('Audit Management Detail Component', () => {
    let comp: AuditDetailComponent;
    let fixture: ComponentFixture<AuditDetailComponent>;
    const route = ({ data: of({ audit: new Audit(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BetterFlyTestModule],
        declarations: [AuditDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(AuditDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AuditDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load audit on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.audit).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
