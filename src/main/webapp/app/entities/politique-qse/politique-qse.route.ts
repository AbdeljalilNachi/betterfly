import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPolitiqueQSE, PolitiqueQSE } from 'app/shared/model/politique-qse.model';
import { PolitiqueQSEService } from './politique-qse.service';
import { PolitiqueQSEComponent } from './politique-qse.component';
import { PolitiqueQSEDetailComponent } from './politique-qse-detail.component';
import { PolitiqueQSEUpdateComponent } from './politique-qse-update.component';

@Injectable({ providedIn: 'root' })
export class PolitiqueQSEResolve implements Resolve<IPolitiqueQSE> {
  constructor(private service: PolitiqueQSEService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPolitiqueQSE> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((politiqueQSE: HttpResponse<PolitiqueQSE>) => {
          if (politiqueQSE.body) {
            return of(politiqueQSE.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PolitiqueQSE());
  }
}

export const politiqueQSERoute: Routes = [
  {
    path: '',
    component: PolitiqueQSEComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'PolitiqueQSES',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PolitiqueQSEDetailComponent,
    resolve: {
      politiqueQSE: PolitiqueQSEResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'PolitiqueQSES',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PolitiqueQSEUpdateComponent,
    resolve: {
      politiqueQSE: PolitiqueQSEResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'PolitiqueQSES',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PolitiqueQSEUpdateComponent,
    resolve: {
      politiqueQSE: PolitiqueQSEResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'PolitiqueQSES',
    },
    canActivate: [UserRouteAccessService],
  },
];
