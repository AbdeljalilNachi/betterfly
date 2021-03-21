import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAudit, Audit } from 'app/shared/model/audit.model';
import { AuditService } from './audit.service';
import { AuditComponent } from './audit.component';
import { AuditDetailComponent } from './audit-detail.component';
import { AuditUpdateComponent } from './audit-update.component';

@Injectable({ providedIn: 'root' })
export class AuditResolve implements Resolve<IAudit> {
  constructor(private service: AuditService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAudit> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((audit: HttpResponse<Audit>) => {
          if (audit.body) {
            return of(audit.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Audit());
  }
}

export const auditRoute: Routes = [
  {
    path: '',
    component: AuditComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'Audits',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AuditDetailComponent,
    resolve: {
      audit: AuditResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Audits',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AuditUpdateComponent,
    resolve: {
      audit: AuditResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Audits',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AuditUpdateComponent,
    resolve: {
      audit: AuditResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Audits',
    },
    canActivate: [UserRouteAccessService],
  },
];
