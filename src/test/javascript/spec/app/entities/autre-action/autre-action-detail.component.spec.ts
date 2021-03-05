import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BetterFlyTestModule } from '../../../test.module';
import { AutreActionDetailComponent } from 'app/entities/autre-action/autre-action-detail.component';
import { AutreAction } from 'app/shared/model/autre-action.model';

describe('Component Tests', () => {
  describe('AutreAction Management Detail Component', () => {
    let comp: AutreActionDetailComponent;
    let fixture: ComponentFixture<AutreActionDetailComponent>;
    const route = ({ data: of({ autreAction: new AutreAction(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BetterFlyTestModule],
        declarations: [AutreActionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(AutreActionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AutreActionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load autreAction on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.autreAction).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
