import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IProcessus, Processus } from 'app/shared/model/processus.model';
import { ProcessusService } from './processus.service';
import { ProcessusComponent } from './processus.component';
import { ProcessusDetailComponent } from './processus-detail.component';
import { ProcessusUpdateComponent } from './processus-update.component';

@Injectable({ providedIn: 'root' })
export class ProcessusResolve implements Resolve<IProcessus> {
  constructor(private service: ProcessusService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProcessus> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((processus: HttpResponse<Processus>) => {
          if (processus.body) {
            return of(processus.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Processus());
  }
}

export const processusRoute: Routes = [
  {
    path: '',
    component: ProcessusComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'Processuses',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ProcessusDetailComponent,
    resolve: {
      processus: ProcessusResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Processuses',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ProcessusUpdateComponent,
    resolve: {
      processus: ProcessusResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Processuses',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ProcessusUpdateComponent,
    resolve: {
      processus: ProcessusResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Processuses',
    },
    canActivate: [UserRouteAccessService],
  },
];
