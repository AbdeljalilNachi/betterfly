import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { BetterFlyTestModule } from '../../../test.module';
import { AnalyseEnvirommentaleUpdateComponent } from 'app/entities/analyse-envirommentale/analyse-envirommentale-update.component';
import { AnalyseEnvirommentaleService } from 'app/entities/analyse-envirommentale/analyse-envirommentale.service';
import { AnalyseEnvirommentale } from 'app/shared/model/analyse-envirommentale.model';

describe('Component Tests', () => {
  describe('AnalyseEnvirommentale Management Update Component', () => {
    let comp: AnalyseEnvirommentaleUpdateComponent;
    let fixture: ComponentFixture<AnalyseEnvirommentaleUpdateComponent>;
    let service: AnalyseEnvirommentaleService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BetterFlyTestModule],
        declarations: [AnalyseEnvirommentaleUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(AnalyseEnvirommentaleUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AnalyseEnvirommentaleUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AnalyseEnvirommentaleService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AnalyseEnvirommentale(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new AnalyseEnvirommentale();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
