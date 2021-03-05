import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { IAutreAction } from 'app/shared/model/autre-action.model';

type EntityResponseType = HttpResponse<IAutreAction>;
type EntityArrayResponseType = HttpResponse<IAutreAction[]>;

@Injectable({ providedIn: 'root' })
export class AutreActionService {
  public resourceUrl = SERVER_API_URL + 'api/autre-actions';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/autre-actions';

  constructor(protected http: HttpClient) {}

  create(autreAction: IAutreAction): Observable<EntityResponseType> {
    return this.http.post<IAutreAction>(this.resourceUrl, autreAction, { observe: 'response' });
  }

  update(autreAction: IAutreAction): Observable<EntityResponseType> {
    return this.http.put<IAutreAction>(this.resourceUrl, autreAction, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAutreAction>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAutreAction[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAutreAction[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
