import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IIndicateurSMI, IndicateurSMI } from 'app/shared/model/indicateur-smi.model';
import { IndicateurSMIService } from './indicateur-smi.service';
import { IndicateurSMIComponent } from './indicateur-smi.component';
import { IndicateurSMIDetailComponent } from './indicateur-smi-detail.component';
import { IndicateurSMIUpdateComponent } from './indicateur-smi-update.component';

@Injectable({ providedIn: 'root' })
export class IndicateurSMIResolve implements Resolve<IIndicateurSMI> {
  constructor(private service: IndicateurSMIService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IIndicateurSMI> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((indicateurSMI: HttpResponse<IndicateurSMI>) => {
          if (indicateurSMI.body) {
            return of(indicateurSMI.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new IndicateurSMI());
  }
}

export const indicateurSMIRoute: Routes = [
  {
    path: '',
    component: IndicateurSMIComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'IndicateurSMIS',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: IndicateurSMIDetailComponent,
    resolve: {
      indicateurSMI: IndicateurSMIResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'IndicateurSMIS',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: IndicateurSMIUpdateComponent,
    resolve: {
      indicateurSMI: IndicateurSMIResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'IndicateurSMIS',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: IndicateurSMIUpdateComponent,
    resolve: {
      indicateurSMI: IndicateurSMIResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'IndicateurSMIS',
    },
    canActivate: [UserRouteAccessService],
  },
];
