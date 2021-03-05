import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BetterFlyTestModule } from '../../../test.module';
import { ResultatIndicateurDetailComponent } from 'app/entities/resultat-indicateur/resultat-indicateur-detail.component';
import { ResultatIndicateur } from 'app/shared/model/resultat-indicateur.model';

describe('Component Tests', () => {
  describe('ResultatIndicateur Management Detail Component', () => {
    let comp: ResultatIndicateurDetailComponent;
    let fixture: ComponentFixture<ResultatIndicateurDetailComponent>;
    const route = ({ data: of({ resultatIndicateur: new ResultatIndicateur(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BetterFlyTestModule],
        declarations: [ResultatIndicateurDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ResultatIndicateurDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ResultatIndicateurDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load resultatIndicateur on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.resultatIndicateur).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
