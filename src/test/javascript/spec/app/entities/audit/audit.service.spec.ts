import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { AuditService } from 'app/entities/audit/audit.service';
import { IAudit, Audit } from 'app/shared/model/audit.model';
import { TypeAudit } from 'app/shared/model/enumerations/type-audit.model';
import { Standard } from 'app/shared/model/enumerations/standard.model';
import { StatutAudit } from 'app/shared/model/enumerations/statut-audit.model';

describe('Service Tests', () => {
  describe('Audit Service', () => {
    let injector: TestBed;
    let service: AuditService;
    let httpMock: HttpTestingController;
    let elemDefault: IAudit;
    let expectedResult: IAudit | IAudit[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(AuditService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Audit(0, currentDate, TypeAudit.Interne, 'AAAAAAA', Standard.ISO9001, StatutAudit.Retard, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dateAudit: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Audit', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dateAudit: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateAudit: currentDate,
          },
          returnedFromService
        );

        service.create(new Audit()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Audit', () => {
        const returnedFromService = Object.assign(
          {
            dateAudit: currentDate.format(DATE_FORMAT),
            typeAudit: 'BBBBBB',
            auditeur: 'BBBBBB',
            standard: 'BBBBBB',
            statut: 'BBBBBB',
            conclusion: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateAudit: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Audit', () => {
        const returnedFromService = Object.assign(
          {
            dateAudit: currentDate.format(DATE_FORMAT),
            typeAudit: 'BBBBBB',
            auditeur: 'BBBBBB',
            standard: 'BBBBBB',
            statut: 'BBBBBB',
            conclusion: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateAudit: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Audit', () => {
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
