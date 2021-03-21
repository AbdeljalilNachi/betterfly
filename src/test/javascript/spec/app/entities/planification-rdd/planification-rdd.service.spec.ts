import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { PlanificationRDDService } from 'app/entities/planification-rdd/planification-rdd.service';
import { IPlanificationRDD, PlanificationRDD } from 'app/shared/model/planification-rdd.model';
import { Standard } from 'app/shared/model/enumerations/standard.model';

describe('Service Tests', () => {
  describe('PlanificationRDD Service', () => {
    let injector: TestBed;
    let service: PlanificationRDDService;
    let httpMock: HttpTestingController;
    let elemDefault: IPlanificationRDD;
    let expectedResult: IPlanificationRDD | IPlanificationRDD[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(PlanificationRDDService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new PlanificationRDD(0, 0, currentDate, false, 'image/png', 'AAAAAAA', Standard.ISO9001);
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

      it('should create a PlanificationRDD', () => {
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

        service.create(new PlanificationRDD()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a PlanificationRDD', () => {
        const returnedFromService = Object.assign(
          {
            nRdd: 1,
            date: currentDate.format(DATE_FORMAT),
            realisee: true,
            presentation: 'BBBBBB',
            standard: 'BBBBBB',
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

      it('should return a list of PlanificationRDD', () => {
        const returnedFromService = Object.assign(
          {
            nRdd: 1,
            date: currentDate.format(DATE_FORMAT),
            realisee: true,
            presentation: 'BBBBBB',
            standard: 'BBBBBB',
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

      it('should delete a PlanificationRDD', () => {
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
