import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { RisqueService } from 'app/entities/risque/risque.service';
import { IRisque, Risque } from 'app/shared/model/risque.model';
import { TypeRisque } from 'app/shared/model/enumerations/type-risque.model';
import { EnumFive } from 'app/shared/model/enumerations/enum-five.model';
import { Traitement } from 'app/shared/model/enumerations/traitement.model';

describe('Service Tests', () => {
  describe('Risque Service', () => {
    let injector: TestBed;
    let service: RisqueService;
    let httpMock: HttpTestingController;
    let elemDefault: IRisque;
    let expectedResult: IRisque | IRisque[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(RisqueService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Risque(
        0,
        'AAAAAAA',
        currentDate,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        TypeRisque.MENACE,
        EnumFive.ONE,
        EnumFive.ONE,
        0,
        Traitement.ACCEPTE,
        'AAAAAAA',
        'AAAAAAA'
      );
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

      it('should create a Risque', () => {
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

        service.create(new Risque()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Risque', () => {
        const returnedFromService = Object.assign(
          {
            processus: 'BBBBBB',
            dateIdentification: currentDate.format(DATE_FORMAT),
            description: 'BBBBBB',
            causePotentielle: 'BBBBBB',
            effetPotentiel: 'BBBBBB',
            type: 'BBBBBB',
            gravite: 'BBBBBB',
            probabilite: 'BBBBBB',
            criticite: 1,
            traitement: 'BBBBBB',
            commentaire: 'BBBBBB',
            origine: 'BBBBBB',
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

      it('should return a list of Risque', () => {
        const returnedFromService = Object.assign(
          {
            processus: 'BBBBBB',
            dateIdentification: currentDate.format(DATE_FORMAT),
            description: 'BBBBBB',
            causePotentielle: 'BBBBBB',
            effetPotentiel: 'BBBBBB',
            type: 'BBBBBB',
            gravite: 'BBBBBB',
            probabilite: 'BBBBBB',
            criticite: 1,
            traitement: 'BBBBBB',
            commentaire: 'BBBBBB',
            origine: 'BBBBBB',
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

      it('should delete a Risque', () => {
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
