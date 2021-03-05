import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { IIndicateurSMI } from 'app/shared/model/indicateur-smi.model';

type EntityResponseType = HttpResponse<IIndicateurSMI>;
type EntityArrayResponseType = HttpResponse<IIndicateurSMI[]>;

@Injectable({ providedIn: 'root' })
export class IndicateurSMIService {
  public resourceUrl = SERVER_API_URL + 'api/indicateur-smis';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/indicateur-smis';

  constructor(protected http: HttpClient) {}

  create(indicateurSMI: IIndicateurSMI): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(indicateurSMI);
    return this.http
      .post<IIndicateurSMI>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(indicateurSMI: IIndicateurSMI): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(indicateurSMI);
    return this.http
      .put<IIndicateurSMI>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IIndicateurSMI>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IIndicateurSMI[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IIndicateurSMI[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(indicateurSMI: IIndicateurSMI): IIndicateurSMI {
    const copy: IIndicateurSMI = Object.assign({}, indicateurSMI, {
      dateIdentification:
        indicateurSMI.dateIdentification && indicateurSMI.dateIdentification.isValid()
          ? indicateurSMI.dateIdentification.format(DATE_FORMAT)
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
      res.body.forEach((indicateurSMI: IIndicateurSMI) => {
        indicateurSMI.dateIdentification = indicateurSMI.dateIdentification ? moment(indicateurSMI.dateIdentification) : undefined;
      });
    }
    return res;
  }
}
