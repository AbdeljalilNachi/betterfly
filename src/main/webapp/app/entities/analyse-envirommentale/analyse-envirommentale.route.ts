import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAnalyseEnvirommentale, AnalyseEnvirommentale } from 'app/shared/model/analyse-envirommentale.model';
import { AnalyseEnvirommentaleService } from './analyse-envirommentale.service';
import { AnalyseEnvirommentaleComponent } from './analyse-envirommentale.component';
import { AnalyseEnvirommentaleDetailComponent } from './analyse-envirommentale-detail.component';
import { AnalyseEnvirommentaleUpdateComponent } from './analyse-envirommentale-update.component';

@Injectable({ providedIn: 'root' })
export class AnalyseEnvirommentaleResolve implements Resolve<IAnalyseEnvirommentale> {
  constructor(private service: AnalyseEnvirommentaleService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAnalyseEnvirommentale> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((analyseEnvirommentale: HttpResponse<AnalyseEnvirommentale>) => {
          if (analyseEnvirommentale.body) {
            return of(analyseEnvirommentale.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AnalyseEnvirommentale());
  }
}

export const analyseEnvirommentaleRoute: Routes = [
  {
    path: '',
    component: AnalyseEnvirommentaleComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'AnalyseEnvirommentales',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AnalyseEnvirommentaleDetailComponent,
    resolve: {
      analyseEnvirommentale: AnalyseEnvirommentaleResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'AnalyseEnvirommentales',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AnalyseEnvirommentaleUpdateComponent,
    resolve: {
      analyseEnvirommentale: AnalyseEnvirommentaleResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'AnalyseEnvirommentales',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AnalyseEnvirommentaleUpdateComponent,
    resolve: {
      analyseEnvirommentale: AnalyseEnvirommentaleResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'AnalyseEnvirommentales',
    },
    canActivate: [UserRouteAccessService],
  },
];
