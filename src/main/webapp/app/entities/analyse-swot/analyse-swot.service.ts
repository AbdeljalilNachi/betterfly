import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { IAnalyseSWOT } from 'app/shared/model/analyse-swot.model';

type EntityResponseType = HttpResponse<IAnalyseSWOT>;
type EntityArrayResponseType = HttpResponse<IAnalyseSWOT[]>;

@Injectable({ providedIn: 'root' })
export class AnalyseSWOTService {
  public resourceUrl = SERVER_API_URL + 'api/analyse-swots';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/analyse-swots';

  constructor(protected http: HttpClient) {}

  create(analyseSWOT: IAnalyseSWOT): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(analyseSWOT);
    return this.http
      .post<IAnalyseSWOT>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(analyseSWOT: IAnalyseSWOT): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(analyseSWOT);
    return this.http
      .put<IAnalyseSWOT>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAnalyseSWOT>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAnalyseSWOT[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAnalyseSWOT[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(analyseSWOT: IAnalyseSWOT): IAnalyseSWOT {
    const copy: IAnalyseSWOT = Object.assign({}, analyseSWOT, {
      dateIdentification:
        analyseSWOT.dateIdentification && analyseSWOT.dateIdentification.isValid()
          ? analyseSWOT.dateIdentification.format(DATE_FORMAT)
          : undefined,
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
      res.body.forEach((analyseSWOT: IAnalyseSWOT) => {
        analyseSWOT.dateIdentification = analyseSWOT.dateIdentification ? moment(analyseSWOT.dateIdentification) : undefined;
      });
    }
    return res;
  }
}
