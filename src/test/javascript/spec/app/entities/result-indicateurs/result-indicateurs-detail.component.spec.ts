import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BetterFlyTestModule } from '../../../test.module';
import { ResultIndicateursDetailComponent } from 'app/entities/result-indicateurs/result-indicateurs-detail.component';
import { ResultIndicateurs } from 'app/shared/model/result-indicateurs.model';

describe('Component Tests', () => {
  describe('ResultIndicateurs Management Detail Component', () => {
    let comp: ResultIndicateursDetailComponent;
    let fixture: ComponentFixture<ResultIndicateursDetailComponent>;
    const route = ({ data: of({ resultIndicateurs: new ResultIndicateurs(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BetterFlyTestModule],
        declarations: [ResultIndicateursDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ResultIndicateursDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ResultIndicateursDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load resultIndicateurs on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.resultIndicateurs).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
