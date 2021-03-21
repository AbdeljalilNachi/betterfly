import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BetterFlyTestModule } from '../../../test.module';
import { ConstatAuditDetailComponent } from 'app/entities/constat-audit/constat-audit-detail.component';
import { ConstatAudit } from 'app/shared/model/constat-audit.model';

describe('Component Tests', () => {
  describe('ConstatAudit Management Detail Component', () => {
    let comp: ConstatAuditDetailComponent;
    let fixture: ComponentFixture<ConstatAuditDetailComponent>;
    const route = ({ data: of({ constatAudit: new ConstatAudit(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BetterFlyTestModule],
        declarations: [ConstatAuditDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ConstatAuditDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ConstatAuditDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load constatAudit on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.constatAudit).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
