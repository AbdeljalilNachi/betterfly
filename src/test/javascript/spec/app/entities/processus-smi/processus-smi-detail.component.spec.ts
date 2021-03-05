import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { BetterFlyTestModule } from '../../../test.module';
import { ProcessusSMIDetailComponent } from 'app/entities/processus-smi/processus-smi-detail.component';
import { ProcessusSMI } from 'app/shared/model/processus-smi.model';

describe('Component Tests', () => {
  describe('ProcessusSMI Management Detail Component', () => {
    let comp: ProcessusSMIDetailComponent;
    let fixture: ComponentFixture<ProcessusSMIDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ processusSMI: new ProcessusSMI(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BetterFlyTestModule],
        declarations: [ProcessusSMIDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ProcessusSMIDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProcessusSMIDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load processusSMI on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.processusSMI).toEqual(jasmine.objectContaining({ id: 123 }));
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
