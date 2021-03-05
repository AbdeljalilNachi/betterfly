import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { ResultatIndicateurService } from 'app/entities/resultat-indicateur/resultat-indicateur.service';
import { IResultatIndicateur, ResultatIndicateur } from 'app/shared/model/resultat-indicateur.model';
import { Mois } from 'app/shared/model/enumerations/mois.model';

describe('Service Tests', () => {
  describe('ResultatIndicateur Service', () => {
    let injector: TestBed;
    let service: ResultatIndicateurService;
    let httpMock: HttpTestingController;
    let elemDefault: IResultatIndicateur;
    let expectedResult: IResultatIndicateur | IResultatIndicateur[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(ResultatIndicateurService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new ResultatIndicateur(0, Mois.JAN, 0, 0, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a ResultatIndicateur', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new ResultatIndicateur()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a ResultatIndicateur', () => {
        const returnedFromService = Object.assign(
          {
            mois: 'BBBBBB',
            cible: 1,
            resultat: 1,
            indicateur: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of ResultatIndicateur', () => {
        const returnedFromService = Object.assign(
          {
            mois: 'BBBBBB',
            cible: 1,
            resultat: 1,
            indicateur: 'BBBBBB',
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

      it('should delete a ResultatIndicateur', () => {
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
