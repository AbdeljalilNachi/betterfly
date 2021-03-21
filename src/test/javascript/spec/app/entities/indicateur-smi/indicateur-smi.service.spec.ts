import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { IndicateurSMIService } from 'app/entities/indicateur-smi/indicateur-smi.service';
import { IIndicateurSMI, IndicateurSMI } from 'app/shared/model/indicateur-smi.model';

describe('Service Tests', () => {
  describe('IndicateurSMI Service', () => {
    let injector: TestBed;
    let service: IndicateurSMIService;
    let httpMock: HttpTestingController;
    let elemDefault: IIndicateurSMI;
    let expectedResult: IIndicateurSMI | IIndicateurSMI[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(IndicateurSMIService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new IndicateurSMI(0, currentDate, 'AAAAAAA', 'AAAAAAA', 0, 0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', false);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dateIdentification: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a IndicateurSMI', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dateIdentification: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateIdentification: currentDate,
          },
          returnedFromService
        );

        service.create(new IndicateurSMI()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a IndicateurSMI', () => {
        const returnedFromService = Object.assign(
          {
            dateIdentification: currentDate.format(DATE_FORMAT),
            indicateur: 'BBBBBB',
            formuleCalcul: 'BBBBBB',
            cible: 1,
            seuilTolerance: 1,
            unite: 'BBBBBB',
            periodicite: 'BBBBBB',
            responsableCalcul: 'BBBBBB',
            observations: 'BBBBBB',
            vigueur: true,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateIdentification: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of IndicateurSMI', () => {
        const returnedFromService = Object.assign(
          {
            dateIdentification: currentDate.format(DATE_FORMAT),
            indicateur: 'BBBBBB',
            formuleCalcul: 'BBBBBB',
            cible: 1,
            seuilTolerance: 1,
            unite: 'BBBBBB',
            periodicite: 'BBBBBB',
            responsableCalcul: 'BBBBBB',
            observations: 'BBBBBB',
            vigueur: true,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateIdentification: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a IndicateurSMI', () => {
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
