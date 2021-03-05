import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { ObligationConformiteService } from 'app/entities/obligation-conformite/obligation-conformite.service';
import { IObligationConformite, ObligationConformite } from 'app/shared/model/obligation-conformite.model';
import { Rubrique } from 'app/shared/model/enumerations/rubrique.model';

describe('Service Tests', () => {
  describe('ObligationConformite Service', () => {
    let injector: TestBed;
    let service: ObligationConformiteService;
    let httpMock: HttpTestingController;
    let elemDefault: IObligationConformite;
    let expectedResult: IObligationConformite | IObligationConformite[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(ObligationConformiteService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new ObligationConformite(
        0,
        currentDate,
        Rubrique.RUBRIQUE,
        'AAAAAAA',
        0,
        'AAAAAAA',
        false,
        false,
        0,
        'AAAAAAA',
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

      it('should create a ObligationConformite', () => {
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

        service.create(new ObligationConformite()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a ObligationConformite', () => {
        const returnedFromService = Object.assign(
          {
            date: currentDate.format(DATE_FORMAT),
            rubrique: 'BBBBBB',
            reference: 'BBBBBB',
            num: 1,
            exigence: 'BBBBBB',
            applicable: true,
            conforme: true,
            statut: 1,
            observation: 'BBBBBB',
            processus: 'BBBBBB',
            oRIGINE: 'BBBBBB',
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

      it('should return a list of ObligationConformite', () => {
        const returnedFromService = Object.assign(
          {
            date: currentDate.format(DATE_FORMAT),
            rubrique: 'BBBBBB',
            reference: 'BBBBBB',
            num: 1,
            exigence: 'BBBBBB',
            applicable: true,
            conforme: true,
            statut: 1,
            observation: 'BBBBBB',
            processus: 'BBBBBB',
            oRIGINE: 'BBBBBB',
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

      it('should delete a ObligationConformite', () => {
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
