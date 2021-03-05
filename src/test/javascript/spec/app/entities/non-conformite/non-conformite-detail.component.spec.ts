import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BetterFlyTestModule } from '../../../test.module';
import { NonConformiteDetailComponent } from 'app/entities/non-conformite/non-conformite-detail.component';
import { NonConformite } from 'app/shared/model/non-conformite.model';

describe('Component Tests', () => {
  describe('NonConformite Management Detail Component', () => {
    let comp: NonConformiteDetailComponent;
    let fixture: ComponentFixture<NonConformiteDetailComponent>;
    const route = ({ data: of({ nonConformite: new NonConformite(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BetterFlyTestModule],
        declarations: [NonConformiteDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(NonConformiteDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(NonConformiteDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load nonConformite on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.nonConformite).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
