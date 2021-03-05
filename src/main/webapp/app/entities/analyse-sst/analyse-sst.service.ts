import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { IAnalyseSST } from 'app/shared/model/analyse-sst.model';

type EntityResponseType = HttpResponse<IAnalyseSST>;
type EntityArrayResponseType = HttpResponse<IAnalyseSST[]>;

@Injectable({ providedIn: 'root' })
export class AnalyseSSTService {
  public resourceUrl = SERVER_API_URL + 'api/analyse-ssts';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/analyse-ssts';

  constructor(protected http: HttpClient) {}

  create(analyseSST: IAnalyseSST): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(analyseSST);
    return this.http
      .post<IAnalyseSST>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(analyseSST: IAnalyseSST): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(analyseSST);
    return this.http
      .put<IAnalyseSST>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAnalyseSST>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAnalyseSST[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAnalyseSST[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(analyseSST: IAnalyseSST): IAnalyseSST {
    const copy: IAnalyseSST = Object.assign({}, analyseSST, {
      date: analyseSST.date && analyseSST.date.isValid() ? analyseSST.date.format(DATE_FORMAT) : undefined,
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
      res.body.forEach((analyseSST: IAnalyseSST) => {
        analyseSST.date = analyseSST.date ? moment(analyseSST.date) : undefined;
      });
    }
    return res;
  }
}
