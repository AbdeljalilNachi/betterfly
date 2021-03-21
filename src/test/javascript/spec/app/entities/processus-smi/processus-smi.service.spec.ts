import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { ProcessusSMIService } from 'app/entities/processus-smi/processus-smi.service';
import { IProcessusSMI, ProcessusSMI } from 'app/shared/model/processus-smi.model';
import { TypeProcessus } from 'app/shared/model/enumerations/type-processus.model';

describe('Service Tests', () => {
  describe('ProcessusSMI Service', () => {
    let injector: TestBed;
    let service: ProcessusSMIService;
    let httpMock: HttpTestingController;
    let elemDefault: IProcessusSMI;
    let expectedResult: IProcessusSMI | IProcessusSMI[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(ProcessusSMIService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new ProcessusSMI(0, 'AAAAAAA', currentDate, 0, 'AAAAAAA', 'image/png', 'AAAAAAA', TypeProcessus.MANAGEMENT, false);
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

      it('should create a ProcessusSMI', () => {
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

        service.create(new ProcessusSMI()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a ProcessusSMI', () => {
        const returnedFromService = Object.assign(
          {
            processus: 'BBBBBB',
            date: currentDate.format(DATE_FORMAT),
            version: 1,
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

      it('should return a list of ProcessusSMI', () => {
        const returnedFromService = Object.assign(
          {
            processus: 'BBBBBB',
            date: currentDate.format(DATE_FORMAT),
            version: 1,
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

      it('should delete a ProcessusSMI', () => {
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
