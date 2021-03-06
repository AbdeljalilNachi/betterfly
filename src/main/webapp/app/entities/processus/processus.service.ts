import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

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
    return this.http.post<IProcessus>(this.resourceUrl, processus, { observe: 'response' });
  }

  update(processus: IProcessus): Observable<EntityResponseType> {
    return this.http.put<IProcessus>(this.resourceUrl, processus, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IProcessus>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProcessus[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProcessus[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
