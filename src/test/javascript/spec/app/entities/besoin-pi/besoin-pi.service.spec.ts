import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { BesoinPIService } from 'app/entities/besoin-pi/besoin-pi.service';
import { IBesoinPI, BesoinPI } from 'app/shared/model/besoin-pi.model';

describe('Service Tests', () => {
  describe('BesoinPI Service', () => {
    let injector: TestBed;
    let service: BesoinPIService;
    let httpMock: HttpTestingController;
    let elemDefault: IBesoinPI;
    let expectedResult: IBesoinPI | IBesoinPI[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(BesoinPIService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new BesoinPI(0, 'AAAAAAA', currentDate, 'AAAAAAA', false, false, false);
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

      it('should create a BesoinPI', () => {
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

        service.create(new BesoinPI()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a BesoinPI', () => {
        const returnedFromService = Object.assign(
          {
            processus: 'BBBBBB',
            dateIdentification: currentDate.format(DATE_FORMAT),
            piPertinentes: 'BBBBBB',
            pertinente: true,
            priseEnCharge: true,
            afficher: true,
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

      it('should return a list of BesoinPI', () => {
        const returnedFromService = Object.assign(
          {
            processus: 'BBBBBB',
            dateIdentification: currentDate.format(DATE_FORMAT),
            piPertinentes: 'BBBBBB',
            pertinente: true,
            priseEnCharge: true,
            afficher: true,
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

      it('should delete a BesoinPI', () => {
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
