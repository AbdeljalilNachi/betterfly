import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { INonConformite, NonConformite } from 'app/shared/model/non-conformite.model';
import { NonConformiteService } from './non-conformite.service';
import { NonConformiteComponent } from './non-conformite.component';
import { NonConformiteDetailComponent } from './non-conformite-detail.component';
import { NonConformiteUpdateComponent } from './non-conformite-update.component';

@Injectable({ providedIn: 'root' })
export class NonConformiteResolve implements Resolve<INonConformite> {
  constructor(private service: NonConformiteService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<INonConformite> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((nonConformite: HttpResponse<NonConformite>) => {
          if (nonConformite.body) {
            return of(nonConformite.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new NonConformite());
  }
}

export const nonConformiteRoute: Routes = [
  {
    path: '',
    component: NonConformiteComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'NonConformites',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: NonConformiteDetailComponent,
    resolve: {
      nonConformite: NonConformiteResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'NonConformites',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: NonConformiteUpdateComponent,
    resolve: {
      nonConformite: NonConformiteResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'NonConformites',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: NonConformiteUpdateComponent,
    resolve: {
      nonConformite: NonConformiteResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'NonConformites',
    },
    canActivate: [UserRouteAccessService],
  },
];
