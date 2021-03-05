import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IObjectif, Objectif } from 'app/shared/model/objectif.model';
import { ObjectifService } from './objectif.service';
import { ObjectifComponent } from './objectif.component';
import { ObjectifDetailComponent } from './objectif-detail.component';
import { ObjectifUpdateComponent } from './objectif-update.component';

@Injectable({ providedIn: 'root' })
export class ObjectifResolve implements Resolve<IObjectif> {
  constructor(private service: ObjectifService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IObjectif> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((objectif: HttpResponse<Objectif>) => {
          if (objectif.body) {
            return of(objectif.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Objectif());
  }
}

export const objectifRoute: Routes = [
  {
    path: '',
    component: ObjectifComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'Objectifs',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ObjectifDetailComponent,
    resolve: {
      objectif: ObjectifResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Objectifs',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ObjectifUpdateComponent,
    resolve: {
      objectif: ObjectifResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Objectifs',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ObjectifUpdateComponent,
    resolve: {
      objectif: ObjectifResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Objectifs',
    },
    canActivate: [UserRouteAccessService],
  },
];
