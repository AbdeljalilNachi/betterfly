import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IProcessusSMI, ProcessusSMI } from 'app/shared/model/processus-smi.model';
import { ProcessusSMIService } from './processus-smi.service';
import { ProcessusSMIComponent } from './processus-smi.component';
import { ProcessusSMIDetailComponent } from './processus-smi-detail.component';
import { ProcessusSMIUpdateComponent } from './processus-smi-update.component';

@Injectable({ providedIn: 'root' })
export class ProcessusSMIResolve implements Resolve<IProcessusSMI> {
  constructor(private service: ProcessusSMIService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProcessusSMI> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((processusSMI: HttpResponse<ProcessusSMI>) => {
          if (processusSMI.body) {
            return of(processusSMI.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ProcessusSMI());
  }
}

export const processusSMIRoute: Routes = [
  {
    path: '',
    component: ProcessusSMIComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'ProcessusSMIS',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ProcessusSMIDetailComponent,
    resolve: {
      processusSMI: ProcessusSMIResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ProcessusSMIS',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ProcessusSMIUpdateComponent,
    resolve: {
      processusSMI: ProcessusSMIResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ProcessusSMIS',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ProcessusSMIUpdateComponent,
    resolve: {
      processusSMI: ProcessusSMIResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ProcessusSMIS',
    },
    canActivate: [UserRouteAccessService],
  },
];
