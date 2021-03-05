import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IObligationConformite, ObligationConformite } from 'app/shared/model/obligation-conformite.model';
import { ObligationConformiteService } from './obligation-conformite.service';
import { ObligationConformiteComponent } from './obligation-conformite.component';
import { ObligationConformiteDetailComponent } from './obligation-conformite-detail.component';
import { ObligationConformiteUpdateComponent } from './obligation-conformite-update.component';

@Injectable({ providedIn: 'root' })
export class ObligationConformiteResolve implements Resolve<IObligationConformite> {
  constructor(private service: ObligationConformiteService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IObligationConformite> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((obligationConformite: HttpResponse<ObligationConformite>) => {
          if (obligationConformite.body) {
            return of(obligationConformite.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ObligationConformite());
  }
}

export const obligationConformiteRoute: Routes = [
  {
    path: '',
    component: ObligationConformiteComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'ObligationConformites',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ObligationConformiteDetailComponent,
    resolve: {
      obligationConformite: ObligationConformiteResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ObligationConformites',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ObligationConformiteUpdateComponent,
    resolve: {
      obligationConformite: ObligationConformiteResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ObligationConformites',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ObligationConformiteUpdateComponent,
    resolve: {
      obligationConformite: ObligationConformiteResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ObligationConformites',
    },
    canActivate: [UserRouteAccessService],
  },
];
