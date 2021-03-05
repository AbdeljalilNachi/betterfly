import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { PolitiqueQSEService } from 'app/entities/politique-qse/politique-qse.service';
import { IPolitiqueQSE, PolitiqueQSE } from 'app/shared/model/politique-qse.model';

describe('Service Tests', () => {
  describe('PolitiqueQSE Service', () => {
    let injector: TestBed;
    let service: PolitiqueQSEService;
    let httpMock: HttpTestingController;
    let elemDefault: IPolitiqueQSE;
    let expectedResult: IPolitiqueQSE | IPolitiqueQSE[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(PolitiqueQSEService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new PolitiqueQSE(0, 'AAAAAAA', currentDate, 'AAAAAAA', 'AAAAAAA', false, 'AAAAAAA');
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

      it('should create a PolitiqueQSE', () => {
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

        service.create(new PolitiqueQSE()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a PolitiqueQSE', () => {
        const returnedFromService = Object.assign(
          {
            processus: 'BBBBBB',
            date: currentDate.format(DATE_FORMAT),
            axePolitiqueQSE: 'BBBBBB',
            objectifQSE: 'BBBBBB',
            vigueur: true,
            indicateur: 'BBBBBB',
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

      it('should return a list of PolitiqueQSE', () => {
        const returnedFromService = Object.assign(
          {
            processus: 'BBBBBB',
            date: currentDate.format(DATE_FORMAT),
            axePolitiqueQSE: 'BBBBBB',
            objectifQSE: 'BBBBBB',
            vigueur: true,
            indicateur: 'BBBBBB',
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

      it('should delete a PolitiqueQSE', () => {
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
