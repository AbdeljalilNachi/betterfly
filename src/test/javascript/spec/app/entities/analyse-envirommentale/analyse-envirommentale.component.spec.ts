import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, convertToParamMap } from '@angular/router';

import { BetterFlyTestModule } from '../../../test.module';
import { AnalyseEnvirommentaleComponent } from 'app/entities/analyse-envirommentale/analyse-envirommentale.component';
import { AnalyseEnvirommentaleService } from 'app/entities/analyse-envirommentale/analyse-envirommentale.service';
import { AnalyseEnvirommentale } from 'app/shared/model/analyse-envirommentale.model';

describe('Component Tests', () => {
  describe('AnalyseEnvirommentale Management Component', () => {
    let comp: AnalyseEnvirommentaleComponent;
    let fixture: ComponentFixture<AnalyseEnvirommentaleComponent>;
    let service: AnalyseEnvirommentaleService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BetterFlyTestModule],
        declarations: [AnalyseEnvirommentaleComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: {
              data: of({
                defaultSort: 'id,asc',
              }),
              queryParamMap: of(
                convertToParamMap({
                  page: '1',
                  size: '1',
                  sort: 'id,desc',
                })
              ),
            },
          },
        ],
      })
        .overrideTemplate(AnalyseEnvirommentaleComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AnalyseEnvirommentaleComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AnalyseEnvirommentaleService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new AnalyseEnvirommentale(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.analyseEnvirommentales && comp.analyseEnvirommentales[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new AnalyseEnvirommentale(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.analyseEnvirommentales && comp.analyseEnvirommentales[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should calculate the sort attribute for an id', () => {
      // WHEN
      comp.ngOnInit();
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['id,desc']);
    });

    it('should calculate the sort attribute for a non-id attribute', () => {
      // INIT
      comp.ngOnInit();

      // GIVEN
      comp.predicate = 'name';

      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['name,desc', 'id']);
    });
  });
});
