import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IConstat, Constat } from 'app/shared/model/constat.model';
import { ConstatService } from './constat.service';
import { ConstatComponent } from './constat.component';
import { ConstatDetailComponent } from './constat-detail.component';
import { ConstatUpdateComponent } from './constat-update.component';

@Injectable({ providedIn: 'root' })
export class ConstatResolve implements Resolve<IConstat> {
  constructor(private service: ConstatService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IConstat> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((constat: HttpResponse<Constat>) => {
          if (constat.body) {
            return of(constat.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Constat());
  }
}

export const constatRoute: Routes = [
  {
    path: '',
    component: ConstatComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'Constats',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ConstatDetailComponent,
    resolve: {
      constat: ConstatResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Constats',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ConstatUpdateComponent,
    resolve: {
      constat: ConstatResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Constats',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ConstatUpdateComponent,
    resolve: {
      constat: ConstatResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Constats',
    },
    canActivate: [UserRouteAccessService],
  },
];
