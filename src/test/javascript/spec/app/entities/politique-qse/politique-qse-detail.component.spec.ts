import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BetterFlyTestModule } from '../../../test.module';
import { PolitiqueQSEDetailComponent } from 'app/entities/politique-qse/politique-qse-detail.component';
import { PolitiqueQSE } from 'app/shared/model/politique-qse.model';

describe('Component Tests', () => {
  describe('PolitiqueQSE Management Detail Component', () => {
    let comp: PolitiqueQSEDetailComponent;
    let fixture: ComponentFixture<PolitiqueQSEDetailComponent>;
    const route = ({ data: of({ politiqueQSE: new PolitiqueQSE(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BetterFlyTestModule],
        declarations: [PolitiqueQSEDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PolitiqueQSEDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PolitiqueQSEDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load politiqueQSE on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.politiqueQSE).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
