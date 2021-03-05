import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BetterFlyTestModule } from '../../../test.module';
import { AnalyseEnvirommentaleDetailComponent } from 'app/entities/analyse-envirommentale/analyse-envirommentale-detail.component';
import { AnalyseEnvirommentale } from 'app/shared/model/analyse-envirommentale.model';

describe('Component Tests', () => {
  describe('AnalyseEnvirommentale Management Detail Component', () => {
    let comp: AnalyseEnvirommentaleDetailComponent;
    let fixture: ComponentFixture<AnalyseEnvirommentaleDetailComponent>;
    const route = ({ data: of({ analyseEnvirommentale: new AnalyseEnvirommentale(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BetterFlyTestModule],
        declarations: [AnalyseEnvirommentaleDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(AnalyseEnvirommentaleDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AnalyseEnvirommentaleDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load analyseEnvirommentale on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.analyseEnvirommentale).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
