import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { IRisque } from 'app/shared/model/risque.model';

type EntityResponseType = HttpResponse<IRisque>;
type EntityArrayResponseType = HttpResponse<IRisque[]>;

@Injectable({ providedIn: 'root' })
export class RisqueService {
  public resourceUrl = SERVER_API_URL + 'api/risques';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/risques';

  constructor(protected http: HttpClient) {}

  create(risque: IRisque): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(risque);
    return this.http
      .post<IRisque>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(risque: IRisque): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(risque);
    return this.http
      .put<IRisque>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IRisque>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IRisque[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IRisque[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(risque: IRisque): IRisque {
    const copy: IRisque = Object.assign({}, risque, {
      dateIdentification:
        risque.dateIdentification && risque.dateIdentification.isValid() ? risque.dateIdentification.format(DATE_FORMAT) : undefined,
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
      res.body.forEach((risque: IRisque) => {
        risque.dateIdentification = risque.dateIdentification ? moment(risque.dateIdentification) : undefined;
      });
    }
    return res;
  }
}
