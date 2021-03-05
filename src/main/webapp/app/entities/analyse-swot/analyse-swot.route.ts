import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAnalyseSWOT, AnalyseSWOT } from 'app/shared/model/analyse-swot.model';
import { AnalyseSWOTService } from './analyse-swot.service';
import { AnalyseSWOTComponent } from './analyse-swot.component';
import { AnalyseSWOTDetailComponent } from './analyse-swot-detail.component';
import { AnalyseSWOTUpdateComponent } from './analyse-swot-update.component';

@Injectable({ providedIn: 'root' })
export class AnalyseSWOTResolve implements Resolve<IAnalyseSWOT> {
  constructor(private service: AnalyseSWOTService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAnalyseSWOT> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((analyseSWOT: HttpResponse<AnalyseSWOT>) => {
          if (analyseSWOT.body) {
            return of(analyseSWOT.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AnalyseSWOT());
  }
}

export const analyseSWOTRoute: Routes = [
  {
    path: '',
    component: AnalyseSWOTComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'AnalyseSWOTS',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AnalyseSWOTDetailComponent,
    resolve: {
      analyseSWOT: AnalyseSWOTResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'AnalyseSWOTS',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AnalyseSWOTUpdateComponent,
    resolve: {
      analyseSWOT: AnalyseSWOTResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'AnalyseSWOTS',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AnalyseSWOTUpdateComponent,
    resolve: {
      analyseSWOT: AnalyseSWOTResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'AnalyseSWOTS',
    },
    canActivate: [UserRouteAccessService],
  },
];
