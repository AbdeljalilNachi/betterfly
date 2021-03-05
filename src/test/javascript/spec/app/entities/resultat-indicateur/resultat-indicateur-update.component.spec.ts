import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { BetterFlyTestModule } from '../../../test.module';
import { ResultatIndicateurUpdateComponent } from 'app/entities/resultat-indicateur/resultat-indicateur-update.component';
import { ResultatIndicateurService } from 'app/entities/resultat-indicateur/resultat-indicateur.service';
import { ResultatIndicateur } from 'app/shared/model/resultat-indicateur.model';

describe('Component Tests', () => {
  describe('ResultatIndicateur Management Update Component', () => {
    let comp: ResultatIndicateurUpdateComponent;
    let fixture: ComponentFixture<ResultatIndicateurUpdateComponent>;
    let service: ResultatIndicateurService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BetterFlyTestModule],
        declarations: [ResultatIndicateurUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ResultatIndicateurUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ResultatIndicateurUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ResultatIndicateurService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ResultatIndicateur(123);
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
        const entity = new ResultatIndicateur();
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
