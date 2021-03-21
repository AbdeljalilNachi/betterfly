import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IResultIndicateurs, ResultIndicateurs } from 'app/shared/model/result-indicateurs.model';
import { ResultIndicateursService } from './result-indicateurs.service';
import { ResultIndicateursComponent } from './result-indicateurs.component';
import { ResultIndicateursDetailComponent } from './result-indicateurs-detail.component';
import { ResultIndicateursUpdateComponent } from './result-indicateurs-update.component';

@Injectable({ providedIn: 'root' })
export class ResultIndicateursResolve implements Resolve<IResultIndicateurs> {
  constructor(private service: ResultIndicateursService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IResultIndicateurs> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((resultIndicateurs: HttpResponse<ResultIndicateurs>) => {
          if (resultIndicateurs.body) {
            return of(resultIndicateurs.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ResultIndicateurs());
  }
}

export const resultIndicateursRoute: Routes = [
  {
    path: '',
    component: ResultIndicateursComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'ResultIndicateurs',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ResultIndicateursDetailComponent,
    resolve: {
      resultIndicateurs: ResultIndicateursResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ResultIndicateurs',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ResultIndicateursUpdateComponent,
    resolve: {
      resultIndicateurs: ResultIndicateursResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ResultIndicateurs',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ResultIndicateursUpdateComponent,
    resolve: {
      resultIndicateurs: ResultIndicateursResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ResultIndicateurs',
    },
    canActivate: [UserRouteAccessService],
  },
];
