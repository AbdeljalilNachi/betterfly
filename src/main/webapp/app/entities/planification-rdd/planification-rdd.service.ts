import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { IPlanificationRDD } from 'app/shared/model/planification-rdd.model';

type EntityResponseType = HttpResponse<IPlanificationRDD>;
type EntityArrayResponseType = HttpResponse<IPlanificationRDD[]>;

@Injectable({ providedIn: 'root' })
export class PlanificationRDDService {
  public resourceUrl = SERVER_API_URL + 'api/planification-rdds';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/planification-rdds';

  constructor(protected http: HttpClient) {}

  create(planificationRDD: IPlanificationRDD): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(planificationRDD);
    return this.http
      .post<IPlanificationRDD>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(planificationRDD: IPlanificationRDD): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(planificationRDD);
    return this.http
      .put<IPlanificationRDD>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPlanificationRDD>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPlanificationRDD[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPlanificationRDD[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(planificationRDD: IPlanificationRDD): IPlanificationRDD {
    const copy: IPlanificationRDD = Object.assign({}, planificationRDD, {
      date: planificationRDD.date && planificationRDD.date.isValid() ? planificationRDD.date.format(DATE_FORMAT) : undefined,
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
      res.body.forEach((planificationRDD: IPlanificationRDD) => {
        planificationRDD.date = planificationRDD.date ? moment(planificationRDD.date) : undefined;
      });
    }
    return res;
  }
}
