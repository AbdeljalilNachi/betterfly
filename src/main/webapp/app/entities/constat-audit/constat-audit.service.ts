import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { IConstatAudit } from 'app/shared/model/constat-audit.model';

type EntityResponseType = HttpResponse<IConstatAudit>;
type EntityArrayResponseType = HttpResponse<IConstatAudit[]>;

@Injectable({ providedIn: 'root' })
export class ConstatAuditService {
  public resourceUrl = SERVER_API_URL + 'api/constat-audits';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/constat-audits';

  constructor(protected http: HttpClient) {}

  create(constatAudit: IConstatAudit): Observable<EntityResponseType> {
    return this.http.post<IConstatAudit>(this.resourceUrl, constatAudit, { observe: 'response' });
  }

  update(constatAudit: IConstatAudit): Observable<EntityResponseType> {
    return this.http.put<IConstatAudit>(this.resourceUrl, constatAudit, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IConstatAudit>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IConstatAudit[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IConstatAudit[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
