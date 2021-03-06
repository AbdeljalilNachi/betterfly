import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { ProcessussmiService } from 'app/entities/processussmi/processussmi.service';
import { IProcessussmi, Processussmi } from 'app/shared/model/processussmi.model';
import { TypeProcessus } from 'app/shared/model/enumerations/type-processus.model';

describe('Service Tests', () => {
  describe('Processussmi Service', () => {
    let injector: TestBed;
    let service: ProcessussmiService;
    let httpMock: HttpTestingController;
    let elemDefault: IProcessussmi;
    let expectedResult: IProcessussmi | IProcessussmi[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(ProcessussmiService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Processussmi(
        0,
        'AAAAAAA',
        currentDate,
        0,
        'AAAAAAA',
        'AAAAAAA',
        'image/png',
        'AAAAAAA',
        TypeProcessus.MANAGEMENT,
        false
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

      it('should create a Processussmi', () => {
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

        service.create(new Processussmi()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Processussmi', () => {
        const returnedFromService = Object.assign(
          {
            processus: 'BBBBBB',
            date: currentDate.format(DATE_FORMAT),
            version: 1,
            pilote: 'BBBBBB',
            finalite: 'BBBBBB',
            ficheProcessus: 'BBBBBB',
            type: 'BBBBBB',
            vigueur: true,
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

      it('should return a list of Processussmi', () => {
        const returnedFromService = Object.assign(
          {
            processus: 'BBBBBB',
            date: currentDate.format(DATE_FORMAT),
            version: 1,
            pilote: 'BBBBBB',
            finalite: 'BBBBBB',
            ficheProcessus: 'BBBBBB',
            type: 'BBBBBB',
            vigueur: true,
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

      it('should delete a Processussmi', () => {
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
