import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { ConstatAuditService } from 'app/entities/constat-audit/constat-audit.service';
import { IConstatAudit, ConstatAudit } from 'app/shared/model/constat-audit.model';
import { TypeConstatAudit } from 'app/shared/model/enumerations/type-constat-audit.model';

describe('Service Tests', () => {
  describe('ConstatAudit Service', () => {
    let injector: TestBed;
    let service: ConstatAuditService;
    let httpMock: HttpTestingController;
    let elemDefault: IConstatAudit;
    let expectedResult: IConstatAudit | IConstatAudit[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(ConstatAuditService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new ConstatAudit(0, TypeConstatAudit.PF, 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a ConstatAudit', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new ConstatAudit()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a ConstatAudit', () => {
        const returnedFromService = Object.assign(
          {
            type: 'BBBBBB',
            constat: 'BBBBBB',
            origine: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of ConstatAudit', () => {
        const returnedFromService = Object.assign(
          {
            type: 'BBBBBB',
            constat: 'BBBBBB',
            origine: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a ConstatAudit', () => {
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
