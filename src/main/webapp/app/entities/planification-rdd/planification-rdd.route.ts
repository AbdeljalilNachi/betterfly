import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPlanificationRDD, PlanificationRDD } from 'app/shared/model/planification-rdd.model';
import { PlanificationRDDService } from './planification-rdd.service';
import { PlanificationRDDComponent } from './planification-rdd.component';
import { PlanificationRDDDetailComponent } from './planification-rdd-detail.component';
import { PlanificationRDDUpdateComponent } from './planification-rdd-update.component';

@Injectable({ providedIn: 'root' })
export class PlanificationRDDResolve implements Resolve<IPlanificationRDD> {
  constructor(private service: PlanificationRDDService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPlanificationRDD> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((planificationRDD: HttpResponse<PlanificationRDD>) => {
          if (planificationRDD.body) {
            return of(planificationRDD.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PlanificationRDD());
  }
}

export const planificationRDDRoute: Routes = [
  {
    path: '',
    component: PlanificationRDDComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'PlanificationRDDS',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PlanificationRDDDetailComponent,
    resolve: {
      planificationRDD: PlanificationRDDResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'PlanificationRDDS',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PlanificationRDDUpdateComponent,
    resolve: {
      planificationRDD: PlanificationRDDResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'PlanificationRDDS',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PlanificationRDDUpdateComponent,
    resolve: {
      planificationRDD: PlanificationRDDResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'PlanificationRDDS',
    },
    canActivate: [UserRouteAccessService],
  },
];
