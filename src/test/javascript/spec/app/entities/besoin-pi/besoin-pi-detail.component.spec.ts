import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BetterFlyTestModule } from '../../../test.module';
import { BesoinPIDetailComponent } from 'app/entities/besoin-pi/besoin-pi-detail.component';
import { BesoinPI } from 'app/shared/model/besoin-pi.model';

describe('Component Tests', () => {
  describe('BesoinPI Management Detail Component', () => {
    let comp: BesoinPIDetailComponent;
    let fixture: ComponentFixture<BesoinPIDetailComponent>;
    const route = ({ data: of({ besoinPI: new BesoinPI(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BetterFlyTestModule],
        declarations: [BesoinPIDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(BesoinPIDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BesoinPIDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load besoinPI on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.besoinPI).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
