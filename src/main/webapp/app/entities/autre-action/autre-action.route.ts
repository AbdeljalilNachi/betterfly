import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAutreAction, AutreAction } from 'app/shared/model/autre-action.model';
import { AutreActionService } from './autre-action.service';
import { AutreActionComponent } from './autre-action.component';
import { AutreActionDetailComponent } from './autre-action-detail.component';
import { AutreActionUpdateComponent } from './autre-action-update.component';

@Injectable({ providedIn: 'root' })
export class AutreActionResolve implements Resolve<IAutreAction> {
  constructor(private service: AutreActionService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAutreAction> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((autreAction: HttpResponse<AutreAction>) => {
          if (autreAction.body) {
            return of(autreAction.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AutreAction());
  }
}

export const autreActionRoute: Routes = [
  {
    path: '',
    component: AutreActionComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'AutreActions',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AutreActionDetailComponent,
    resolve: {
      autreAction: AutreActionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'AutreActions',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AutreActionUpdateComponent,
    resolve: {
      autreAction: AutreActionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'AutreActions',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AutreActionUpdateComponent,
    resolve: {
      autreAction: AutreActionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'AutreActions',
    },
    canActivate: [UserRouteAccessService],
  },
];
