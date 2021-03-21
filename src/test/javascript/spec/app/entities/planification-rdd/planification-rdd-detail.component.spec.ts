import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { BetterFlyTestModule } from '../../../test.module';
import { PlanificationRDDDetailComponent } from 'app/entities/planification-rdd/planification-rdd-detail.component';
import { PlanificationRDD } from 'app/shared/model/planification-rdd.model';

describe('Component Tests', () => {
  describe('PlanificationRDD Management Detail Component', () => {
    let comp: PlanificationRDDDetailComponent;
    let fixture: ComponentFixture<PlanificationRDDDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ planificationRDD: new PlanificationRDD(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BetterFlyTestModule],
        declarations: [PlanificationRDDDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PlanificationRDDDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PlanificationRDDDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load planificationRDD on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.planificationRDD).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });

    describe('byteSize', () => {
      it('Should call byteSize from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'byteSize');
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.byteSize(fakeBase64);

        // THEN
        expect(dataUtils.byteSize).toBeCalledWith(fakeBase64);
      });
    });

    describe('openFile', () => {
      it('Should call openFile from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'openFile');
        const fakeContentType = 'fake content type';
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.openFile(fakeContentType, fakeBase64);

        // THEN
        expect(dataUtils.openFile).toBeCalledWith(fakeContentType, fakeBase64);
      });
    });
  });
});
