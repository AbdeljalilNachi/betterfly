import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { BetterFlyTestModule } from '../../../test.module';
import { ReclamationDetailComponent } from 'app/entities/reclamation/reclamation-detail.component';
import { Reclamation } from 'app/shared/model/reclamation.model';

describe('Component Tests', () => {
  describe('Reclamation Management Detail Component', () => {
    let comp: ReclamationDetailComponent;
    let fixture: ComponentFixture<ReclamationDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ reclamation: new Reclamation(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BetterFlyTestModule],
        declarations: [ReclamationDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ReclamationDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ReclamationDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load reclamation on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.reclamation).toEqual(jasmine.objectContaining({ id: 123 }));
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
