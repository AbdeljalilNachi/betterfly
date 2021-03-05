import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { IProcessus } from 'app/shared/model/processus.model';

type EntityResponseType = HttpResponse<IProcessus>;
type EntityArrayResponseType = HttpResponse<IProcessus[]>;

@Injectable({ providedIn: 'root' })
export class ProcessusService {
  public resourceUrl = SERVER_API_URL + 'api/processuses';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/processuses';

  constructor(protected http: HttpClient) {}

  create(processus: IProcessus): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(processus);
    return this.http
      .post<IProcessus>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(processus: IProcessus): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(processus);
    return this.http
      .put<IProcessus>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IProcessus>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IProcessus[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IProcessus[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(processus: IProcessus): IProcessus {
    const copy: IProcessus = Object.assign({}, processus, {
      date: processus.date && processus.date.isValid() ? processus.date.format(DATE_FORMAT) : undefined,
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
      res.body.forEach((processus: IProcessus) => {
        processus.date = processus.date ? moment(processus.date) : undefined;
      });
    }
    return res;
  }
}
