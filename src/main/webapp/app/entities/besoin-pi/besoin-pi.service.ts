import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { IBesoinPI } from 'app/shared/model/besoin-pi.model';

type EntityResponseType = HttpResponse<IBesoinPI>;
type EntityArrayResponseType = HttpResponse<IBesoinPI[]>;

@Injectable({ providedIn: 'root' })
export class BesoinPIService {
  public resourceUrl = SERVER_API_URL + 'api/besoin-pis';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/besoin-pis';

  constructor(protected http: HttpClient) {}

  create(besoinPI: IBesoinPI): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(besoinPI);
    return this.http
      .post<IBesoinPI>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(besoinPI: IBesoinPI): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(besoinPI);
    return this.http
      .put<IBesoinPI>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IBesoinPI>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IBesoinPI[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IBesoinPI[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(besoinPI: IBesoinPI): IBesoinPI {
    const copy: IBesoinPI = Object.assign({}, besoinPI, {
      dateIdentification:
        besoinPI.dateIdentification && besoinPI.dateIdentification.isValid() ? besoinPI.dateIdentification.format(DATE_FORMAT) : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateIdentification = res.body.dateIdentification ? moment(res.body.dateIdentification) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((besoinPI: IBesoinPI) => {
        besoinPI.dateIdentification = besoinPI.dateIdentification ? moment(besoinPI.dateIdentification) : undefined;
      });
    }
    return res;
  }
}
