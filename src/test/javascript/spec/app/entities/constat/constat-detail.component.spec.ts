import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BetterFlyTestModule } from '../../../test.module';
import { ConstatDetailComponent } from 'app/entities/constat/constat-detail.component';
import { Constat } from 'app/shared/model/constat.model';

describe('Component Tests', () => {
  describe('Constat Management Detail Component', () => {
    let comp: ConstatDetailComponent;
    let fixture: ComponentFixture<ConstatDetailComponent>;
    const route = ({ data: of({ constat: new Constat(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BetterFlyTestModule],
        declarations: [ConstatDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ConstatDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ConstatDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load constat on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.constat).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
