import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { BetterFlyTestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { AnalyseEnvirommentaleDeleteDialogComponent } from 'app/entities/analyse-envirommentale/analyse-envirommentale-delete-dialog.component';
import { AnalyseEnvirommentaleService } from 'app/entities/analyse-envirommentale/analyse-envirommentale.service';

describe('Component Tests', () => {
  describe('AnalyseEnvirommentale Management Delete Component', () => {
    let comp: AnalyseEnvirommentaleDeleteDialogComponent;
    let fixture: ComponentFixture<AnalyseEnvirommentaleDeleteDialogComponent>;
    let service: AnalyseEnvirommentaleService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BetterFlyTestModule],
        declarations: [AnalyseEnvirommentaleDeleteDialogComponent],
      })
        .overrideTemplate(AnalyseEnvirommentaleDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AnalyseEnvirommentaleDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AnalyseEnvirommentaleService);
      mockEventManager = TestBed.get(JhiEventManager);
      mockActiveModal = TestBed.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.closeSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));

      it('Should not call delete service on clear', () => {
        // GIVEN
        spyOn(service, 'delete');

        // WHEN
        comp.cancel();

        // THEN
        expect(service.delete).not.toHaveBeenCalled();
        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
      });
    });
  });
});
