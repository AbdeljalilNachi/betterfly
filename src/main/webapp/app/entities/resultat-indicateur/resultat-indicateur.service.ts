import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { IResultatIndicateur } from 'app/shared/model/resultat-indicateur.model';

type EntityResponseType = HttpResponse<IResultatIndicateur>;
type EntityArrayResponseType = HttpResponse<IResultatIndicateur[]>;

@Injectable({ providedIn: 'root' })
export class ResultatIndicateurService {
  public resourceUrl = SERVER_API_URL + 'api/resultat-indicateurs';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/resultat-indicateurs';

  constructor(protected http: HttpClient) {}

  create(resultatIndicateur: IResultatIndicateur): Observable<EntityResponseType> {
    return this.http.post<IResultatIndicateur>(this.resourceUrl, resultatIndicateur, { observe: 'response' });
  }

  update(resultatIndicateur: IResultatIndicateur): Observable<EntityResponseType> {
    return this.http.put<IResultatIndicateur>(this.resourceUrl, resultatIndicateur, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IResultatIndicateur>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IResultatIndicateur[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IResultatIndicateur[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
