import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BetterFlyTestModule } from '../../../test.module';
import { IndicateurSMIDetailComponent } from 'app/entities/indicateur-smi/indicateur-smi-detail.component';
import { IndicateurSMI } from 'app/shared/model/indicateur-smi.model';

describe('Component Tests', () => {
  describe('IndicateurSMI Management Detail Component', () => {
    let comp: IndicateurSMIDetailComponent;
    let fixture: ComponentFixture<IndicateurSMIDetailComponent>;
    const route = ({ data: of({ indicateurSMI: new IndicateurSMI(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BetterFlyTestModule],
        declarations: [IndicateurSMIDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(IndicateurSMIDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(IndicateurSMIDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load indicateurSMI on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.indicateurSMI).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
