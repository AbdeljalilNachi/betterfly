import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { IAnalyseEnvirommentale } from 'app/shared/model/analyse-envirommentale.model';

type EntityResponseType = HttpResponse<IAnalyseEnvirommentale>;
type EntityArrayResponseType = HttpResponse<IAnalyseEnvirommentale[]>;

@Injectable({ providedIn: 'root' })
export class AnalyseEnvirommentaleService {
  public resourceUrl = SERVER_API_URL + 'api/analyse-envirommentales';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/analyse-envirommentales';

  constructor(protected http: HttpClient) {}

  create(analyseEnvirommentale: IAnalyseEnvirommentale): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(analyseEnvirommentale);
    return this.http
      .post<IAnalyseEnvirommentale>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(analyseEnvirommentale: IAnalyseEnvirommentale): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(analyseEnvirommentale);
    return this.http
      .put<IAnalyseEnvirommentale>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAnalyseEnvirommentale>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAnalyseEnvirommentale[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAnalyseEnvirommentale[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(analyseEnvirommentale: IAnalyseEnvirommentale): IAnalyseEnvirommentale {
    const copy: IAnalyseEnvirommentale = Object.assign({}, analyseEnvirommentale, {
      date: analyseEnvirommentale.date && analyseEnvirommentale.date.isValid() ? analyseEnvirommentale.date.format(DATE_FORMAT) : undefined,
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
      res.body.forEach((analyseEnvirommentale: IAnalyseEnvirommentale) => {
        analyseEnvirommentale.date = analyseEnvirommentale.date ? moment(analyseEnvirommentale.date) : undefined;
      });
    }
    return res;
  }
}
