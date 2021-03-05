import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IResultatIndicateur, ResultatIndicateur } from 'app/shared/model/resultat-indicateur.model';
import { ResultatIndicateurService } from './resultat-indicateur.service';
import { ResultatIndicateurComponent } from './resultat-indicateur.component';
import { ResultatIndicateurDetailComponent } from './resultat-indicateur-detail.component';
import { ResultatIndicateurUpdateComponent } from './resultat-indicateur-update.component';

@Injectable({ providedIn: 'root' })
export class ResultatIndicateurResolve implements Resolve<IResultatIndicateur> {
  constructor(private service: ResultatIndicateurService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IResultatIndicateur> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((resultatIndicateur: HttpResponse<ResultatIndicateur>) => {
          if (resultatIndicateur.body) {
            return of(resultatIndicateur.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ResultatIndicateur());
  }
}

export const resultatIndicateurRoute: Routes = [
  {
    path: '',
    component: ResultatIndicateurComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'ResultatIndicateurs',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ResultatIndicateurDetailComponent,
    resolve: {
      resultatIndicateur: ResultatIndicateurResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ResultatIndicateurs',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ResultatIndicateurUpdateComponent,
    resolve: {
      resultatIndicateur: ResultatIndicateurResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ResultatIndicateurs',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ResultatIndicateurUpdateComponent,
    resolve: {
      resultatIndicateur: ResultatIndicateurResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ResultatIndicateurs',
    },
    canActivate: [UserRouteAccessService],
  },
];
