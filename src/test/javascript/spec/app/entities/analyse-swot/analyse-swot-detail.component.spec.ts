import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BetterFlyTestModule } from '../../../test.module';
import { AnalyseSWOTDetailComponent } from 'app/entities/analyse-swot/analyse-swot-detail.component';
import { AnalyseSWOT } from 'app/shared/model/analyse-swot.model';

describe('Component Tests', () => {
  describe('AnalyseSWOT Management Detail Component', () => {
    let comp: AnalyseSWOTDetailComponent;
    let fixture: ComponentFixture<AnalyseSWOTDetailComponent>;
    const route = ({ data: of({ analyseSWOT: new AnalyseSWOT(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BetterFlyTestModule],
        declarations: [AnalyseSWOTDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(AnalyseSWOTDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AnalyseSWOTDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load analyseSWOT on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.analyseSWOT).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
