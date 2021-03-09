import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { IProcessusSMI } from 'app/shared/model/processus-smi.model';

type EntityResponseType = HttpResponse<IProcessusSMI>;
type EntityArrayResponseType = HttpResponse<IProcessusSMI[]>;

@Injectable({ providedIn: 'root' })
export class ProcessusSMIService {
  public resourceUrl = SERVER_API_URL + 'api/processus-smis';
  public resourceUrlNames = SERVER_API_URL + 'api/processus-names';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/processus-smis';

  constructor(protected http: HttpClient) {}

  create(processusSMI: IProcessusSMI): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(processusSMI);
    return this.http
      .post<IProcessusSMI>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(processusSMI: IProcessusSMI): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(processusSMI);
    return this.http
      .put<IProcessusSMI>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IProcessusSMI>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IProcessusSMI[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  getProcs(): Observable<String[]> {
    
    return this.http.get< String[]>(`${this.resourceUrlNames}`);
  }


  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IProcessusSMI[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(processusSMI: IProcessusSMI): IProcessusSMI {
    const copy: IProcessusSMI = Object.assign({}, processusSMI, {
      date: processusSMI.date && processusSMI.date.isValid() ? processusSMI.date.format(DATE_FORMAT) : undefined,
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
      res.body.forEach((processusSMI: IProcessusSMI) => {
        processusSMI.date = processusSMI.date ? moment(processusSMI.date) : undefined;
      });
    }
    return res;
  }
}
