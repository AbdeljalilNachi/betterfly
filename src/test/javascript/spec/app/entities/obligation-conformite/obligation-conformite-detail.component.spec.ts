import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BetterFlyTestModule } from '../../../test.module';
import { ObligationConformiteDetailComponent } from 'app/entities/obligation-conformite/obligation-conformite-detail.component';
import { ObligationConformite } from 'app/shared/model/obligation-conformite.model';

describe('Component Tests', () => {
  describe('ObligationConformite Management Detail Component', () => {
    let comp: ObligationConformiteDetailComponent;
    let fixture: ComponentFixture<ObligationConformiteDetailComponent>;
    const route = ({ data: of({ obligationConformite: new ObligationConformite(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BetterFlyTestModule],
        declarations: [ObligationConformiteDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ObligationConformiteDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ObligationConformiteDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load obligationConformite on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.obligationConformite).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
