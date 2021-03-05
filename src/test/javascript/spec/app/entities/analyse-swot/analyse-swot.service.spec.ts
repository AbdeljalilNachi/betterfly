import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { AnalyseSWOTService } from 'app/entities/analyse-swot/analyse-swot.service';
import { IAnalyseSWOT, AnalyseSWOT } from 'app/shared/model/analyse-swot.model';
import { TypeAnalyseSWOT } from 'app/shared/model/enumerations/type-analyse-swot.model';

describe('Service Tests', () => {
  describe('AnalyseSWOT Service', () => {
    let injector: TestBed;
    let service: AnalyseSWOTService;
    let httpMock: HttpTestingController;
    let elemDefault: IAnalyseSWOT;
    let expectedResult: IAnalyseSWOT | IAnalyseSWOT[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(AnalyseSWOTService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new AnalyseSWOT(0, 'AAAAAAA', currentDate, 'AAAAAAA', 'AAAAAAA', TypeAnalyseSWOT.FORCE, 'AAAAAAA', 'AAAAAAA', false);
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

      it('should create a AnalyseSWOT', () => {
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

        service.create(new AnalyseSWOT()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a AnalyseSWOT', () => {
        const returnedFromService = Object.assign(
          {
            processus: 'BBBBBB',
            dateIdentification: currentDate.format(DATE_FORMAT),
            description: 'BBBBBB',
            pilote: 'BBBBBB',
            type: 'BBBBBB',
            bu: 'BBBBBB',
            commentaire: 'BBBBBB',
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

      it('should return a list of AnalyseSWOT', () => {
        const returnedFromService = Object.assign(
          {
            processus: 'BBBBBB',
            dateIdentification: currentDate.format(DATE_FORMAT),
            description: 'BBBBBB',
            pilote: 'BBBBBB',
            type: 'BBBBBB',
            bu: 'BBBBBB',
            commentaire: 'BBBBBB',
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

      it('should delete a AnalyseSWOT', () => {
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
