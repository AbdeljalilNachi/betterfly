import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { IAudit } from 'app/shared/model/audit.model';

type EntityResponseType = HttpResponse<IAudit>;
type EntityArrayResponseType = HttpResponse<IAudit[]>;

@Injectable({ providedIn: 'root' })
export class AuditService {
  public resourceUrl = SERVER_API_URL + 'api/audits';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/audits';

  constructor(protected http: HttpClient) {}

  create(audit: IAudit): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(audit);
    return this.http
      .post<IAudit>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(audit: IAudit): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(audit);
    return this.http
      .put<IAudit>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAudit>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAudit[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAudit[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(audit: IAudit): IAudit {
    const copy: IAudit = Object.assign({}, audit, {
      dateAudit: audit.dateAudit && audit.dateAudit.isValid() ? audit.dateAudit.format(DATE_FORMAT) : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateAudit = res.body.dateAudit ? moment(res.body.dateAudit) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((audit: IAudit) => {
        audit.dateAudit = audit.dateAudit ? moment(audit.dateAudit) : undefined;
      });
    }
    return res;
  }
}
