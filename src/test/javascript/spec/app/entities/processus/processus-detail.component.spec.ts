import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BetterFlyTestModule } from '../../../test.module';
import { ProcessusDetailComponent } from 'app/entities/processus/processus-detail.component';
import { Processus } from 'app/shared/model/processus.model';

describe('Component Tests', () => {
  describe('Processus Management Detail Component', () => {
    let comp: ProcessusDetailComponent;
    let fixture: ComponentFixture<ProcessusDetailComponent>;
    const route = ({ data: of({ processus: new Processus(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BetterFlyTestModule],
        declarations: [ProcessusDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ProcessusDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProcessusDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load processus on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.processus).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
