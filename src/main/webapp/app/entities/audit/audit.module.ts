import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BetterFlySharedModule } from 'app/shared/shared.module';
import { AuditComponent } from './audit.component';
import { AuditDetailComponent } from './audit-detail.component';
import { AuditUpdateComponent } from './audit-update.component';
import { AuditDeleteDialogComponent } from './audit-delete-dialog.component';
import { auditRoute } from './audit.route';

@NgModule({
  imports: [BetterFlySharedModule, RouterModule.forChild(auditRoute)],
  declarations: [AuditComponent, AuditDetailComponent, AuditUpdateComponent, AuditDeleteDialogComponent],
  entryComponents: [AuditDeleteDialogComponent],
})
export class BetterFlyAuditModule {}
