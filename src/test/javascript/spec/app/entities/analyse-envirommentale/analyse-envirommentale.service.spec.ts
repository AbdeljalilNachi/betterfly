import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { AnalyseEnvirommentaleService } from 'app/entities/analyse-envirommentale/analyse-envirommentale.service';
import { IAnalyseEnvirommentale, AnalyseEnvirommentale } from 'app/shared/model/analyse-envirommentale.model';
import { Situation } from 'app/shared/model/enumerations/situation.model';
import { EnumFive } from 'app/shared/model/enumerations/enum-five.model';

describe('Service Tests', () => {
  describe('AnalyseEnvirommentale Service', () => {
    let injector: TestBed;
    let service: AnalyseEnvirommentaleService;
    let httpMock: HttpTestingController;
    let elemDefault: IAnalyseEnvirommentale;
    let expectedResult: IAnalyseEnvirommentale | IAnalyseEnvirommentale[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(AnalyseEnvirommentaleService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new AnalyseEnvirommentale(
        0,
        currentDate,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        Situation.Normale,
        EnumFive.ONE,
        EnumFive.ONE,
        EnumFive.ONE,
        EnumFive.ONE,
        0,
        'AAAAAAA',
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            date: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a AnalyseEnvirommentale', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            date: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            date: currentDate,
          },
          returnedFromService
        );

        service.create(new AnalyseEnvirommentale()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a AnalyseEnvirommentale', () => {
        const returnedFromService = Object.assign(
          {
            date: currentDate.format(DATE_FORMAT),
            businessUnit: 'BBBBBB',
            activite: 'BBBBBB',
            aspectEnvironnemental: 'BBBBBB',
            impactEnvironnemental: 'BBBBBB',
            competencesRequises: 'BBBBBB',
            situation: 'BBBBBB',
            frequence: 'BBBBBB',
            sensibiliteMilieu: 'BBBBBB',
            coefficientMaitrise: 'BBBBBB',
            gravite: 'BBBBBB',
            criticite: 1,
            maitriseExistante: 'BBBBBB',
            origine: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            date: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of AnalyseEnvirommentale', () => {
        const returnedFromService = Object.assign(
          {
            date: currentDate.format(DATE_FORMAT),
            businessUnit: 'BBBBBB',
            activite: 'BBBBBB',
            aspectEnvironnemental: 'BBBBBB',
            impactEnvironnemental: 'BBBBBB',
            competencesRequises: 'BBBBBB',
            situation: 'BBBBBB',
            frequence: 'BBBBBB',
            sensibiliteMilieu: 'BBBBBB',
            coefficientMaitrise: 'BBBBBB',
            gravite: 'BBBBBB',
            criticite: 1,
            maitriseExistante: 'BBBBBB',
            origine: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            date: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a AnalyseEnvirommentale', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
