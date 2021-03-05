import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BetterFlyTestModule } from '../../../test.module';
import { AnalyseSSTDetailComponent } from 'app/entities/analyse-sst/analyse-sst-detail.component';
import { AnalyseSST } from 'app/shared/model/analyse-sst.model';

describe('Component Tests', () => {
  describe('AnalyseSST Management Detail Component', () => {
    let comp: AnalyseSSTDetailComponent;
    let fixture: ComponentFixture<AnalyseSSTDetailComponent>;
    const route = ({ data: of({ analyseSST: new AnalyseSST(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BetterFlyTestModule],
        declarations: [AnalyseSSTDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(AnalyseSSTDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AnalyseSSTDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load analyseSST on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.analyseSST).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
