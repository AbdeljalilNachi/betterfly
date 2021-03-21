import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IConstatAudit, ConstatAudit } from 'app/shared/model/constat-audit.model';
import { ConstatAuditService } from './constat-audit.service';
import { ConstatAuditComponent } from './constat-audit.component';
import { ConstatAuditDetailComponent } from './constat-audit-detail.component';
import { ConstatAuditUpdateComponent } from './constat-audit-update.component';

@Injectable({ providedIn: 'root' })
export class ConstatAuditResolve implements Resolve<IConstatAudit> {
  constructor(private service: ConstatAuditService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IConstatAudit> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((constatAudit: HttpResponse<ConstatAudit>) => {
          if (constatAudit.body) {
            return of(constatAudit.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ConstatAudit());
  }
}

export const constatAuditRoute: Routes = [
  {
    path: '',
    component: ConstatAuditComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'ConstatAudits',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ConstatAuditDetailComponent,
    resolve: {
      constatAudit: ConstatAuditResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ConstatAudits',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ConstatAuditUpdateComponent,
    resolve: {
      constatAudit: ConstatAuditResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ConstatAudits',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ConstatAuditUpdateComponent,
    resolve: {
      constatAudit: ConstatAuditResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ConstatAudits',
    },
    canActivate: [UserRouteAccessService],
  },
];
