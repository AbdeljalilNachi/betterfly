import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { INonConformite } from 'app/shared/model/non-conformite.model';

type EntityResponseType = HttpResponse<INonConformite>;
type EntityArrayResponseType = HttpResponse<INonConformite[]>;

@Injectable({ providedIn: 'root' })
export class NonConformiteService {
  public resourceUrl = SERVER_API_URL + 'api/non-conformites';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/non-conformites';

  constructor(protected http: HttpClient) {}

  create(nonConformite: INonConformite): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(nonConformite);
    return this.http
      .post<INonConformite>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(nonConformite: INonConformite): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(nonConformite);
    return this.http
      .put<INonConformite>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<INonConformite>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<INonConformite[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<INonConformite[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(nonConformite: INonConformite): INonConformite {
    const copy: INonConformite = Object.assign({}, nonConformite, {
      date: nonConformite.date && nonConformite.date.isValid() ? nonConformite.date.format(DATE_FORMAT) : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.date = res.body.date ? moment(res.body.date) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((nonConformite: INonConformite) => {
        nonConformite.date = nonConformite.date ? moment(nonConformite.date) : undefined;
      });
    }
    return res;
  }
}
