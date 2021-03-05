import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAnalyseSST, AnalyseSST } from 'app/shared/model/analyse-sst.model';
import { AnalyseSSTService } from './analyse-sst.service';
import { AnalyseSSTComponent } from './analyse-sst.component';
import { AnalyseSSTDetailComponent } from './analyse-sst-detail.component';
import { AnalyseSSTUpdateComponent } from './analyse-sst-update.component';

@Injectable({ providedIn: 'root' })
export class AnalyseSSTResolve implements Resolve<IAnalyseSST> {
  constructor(private service: AnalyseSSTService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAnalyseSST> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((analyseSST: HttpResponse<AnalyseSST>) => {
          if (analyseSST.body) {
            return of(analyseSST.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AnalyseSST());
  }
}

export const analyseSSTRoute: Routes = [
  {
    path: '',
    component: AnalyseSSTComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'AnalyseSSTS',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AnalyseSSTDetailComponent,
    resolve: {
      analyseSST: AnalyseSSTResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'AnalyseSSTS',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AnalyseSSTUpdateComponent,
    resolve: {
      analyseSST: AnalyseSSTResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'AnalyseSSTS',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AnalyseSSTUpdateComponent,
    resolve: {
      analyseSST: AnalyseSSTResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'AnalyseSSTS',
    },
    canActivate: [UserRouteAccessService],
  },
];
