import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { IResultIndicateurs } from 'app/shared/model/result-indicateurs.model';

type EntityResponseType = HttpResponse<IResultIndicateurs>;
type EntityArrayResponseType = HttpResponse<IResultIndicateurs[]>;

@Injectable({ providedIn: 'root' })
export class ResultIndicateursService {
  public resourceUrl = SERVER_API_URL + 'api/result-indicateurs';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/result-indicateurs';

  constructor(protected http: HttpClient) {}

  create(resultIndicateurs: IResultIndicateurs): Observable<EntityResponseType> {
    return this.http.post<IResultIndicateurs>(this.resourceUrl, resultIndicateurs, { observe: 'response' });
  }

  update(resultIndicateurs: IResultIndicateurs): Observable<EntityResponseType> {
    return this.http.put<IResultIndicateurs>(this.resourceUrl, resultIndicateurs, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IResultIndicateurs>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IResultIndicateurs[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IResultIndicateurs[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
