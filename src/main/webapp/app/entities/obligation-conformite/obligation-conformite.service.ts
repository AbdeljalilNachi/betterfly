import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { IObligationConformite } from 'app/shared/model/obligation-conformite.model';

type EntityResponseType = HttpResponse<IObligationConformite>;
type EntityArrayResponseType = HttpResponse<IObligationConformite[]>;

@Injectable({ providedIn: 'root' })
export class ObligationConformiteService {
  public resourceUrl = SERVER_API_URL + 'api/obligation-conformites';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/obligation-conformites';

  constructor(protected http: HttpClient) {}

  create(obligationConformite: IObligationConformite): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(obligationConformite);
    return this.http
      .post<IObligationConformite>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(obligationConformite: IObligationConformite): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(obligationConformite);
    return this.http
      .put<IObligationConformite>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IObligationConformite>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IObligationConformite[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IObligationConformite[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(obligationConformite: IObligationConformite): IObligationConformite {
    const copy: IObligationConformite = Object.assign({}, obligationConformite, {
      date: obligationConformite.date && obligationConformite.date.isValid() ? obligationConformite.date.format(DATE_FORMAT) : undefined,
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
      res.body.forEach((obligationConformite: IObligationConformite) => {
        obligationConformite.date = obligationConformite.date ? moment(obligationConformite.date) : undefined;
      });
    }
    return res;
  }
}
