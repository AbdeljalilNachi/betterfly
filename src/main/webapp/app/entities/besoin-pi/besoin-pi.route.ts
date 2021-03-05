import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IBesoinPI, BesoinPI } from 'app/shared/model/besoin-pi.model';
import { BesoinPIService } from './besoin-pi.service';
import { BesoinPIComponent } from './besoin-pi.component';
import { BesoinPIDetailComponent } from './besoin-pi-detail.component';
import { BesoinPIUpdateComponent } from './besoin-pi-update.component';

@Injectable({ providedIn: 'root' })
export class BesoinPIResolve implements Resolve<IBesoinPI> {
  constructor(private service: BesoinPIService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBesoinPI> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((besoinPI: HttpResponse<BesoinPI>) => {
          if (besoinPI.body) {
            return of(besoinPI.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new BesoinPI());
  }
}

export const besoinPIRoute: Routes = [
  {
    path: '',
    component: BesoinPIComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'BesoinPIS',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: BesoinPIDetailComponent,
    resolve: {
      besoinPI: BesoinPIResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'BesoinPIS',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: BesoinPIUpdateComponent,
    resolve: {
      besoinPI: BesoinPIResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'BesoinPIS',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: BesoinPIUpdateComponent,
    resolve: {
      besoinPI: BesoinPIResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'BesoinPIS',
    },
    canActivate: [UserRouteAccessService],
  },
];
