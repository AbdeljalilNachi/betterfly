import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { IConstat } from 'app/shared/model/constat.model';

type EntityResponseType = HttpResponse<IConstat>;
type EntityArrayResponseType = HttpResponse<IConstat[]>;

@Injectable({ providedIn: 'root' })
export class ConstatService {
  public resourceUrl = SERVER_API_URL + 'api/constats';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/constats';

  constructor(protected http: HttpClient) {}

  create(constat: IConstat): Observable<EntityResponseType> {
    return this.http.post<IConstat>(this.resourceUrl, constat, { observe: 'response' });
  }

  update(constat: IConstat): Observable<EntityResponseType> {
    return this.http.put<IConstat>(this.resourceUrl, constat, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IConstat>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IConstat[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IConstat[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
