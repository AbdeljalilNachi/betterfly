import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BetterFlySharedModule } from 'app/shared/shared.module';
import { ConstatAuditComponent } from './constat-audit.component';
import { ConstatAuditDetailComponent } from './constat-audit-detail.component';
import { ConstatAuditUpdateComponent } from './constat-audit-update.component';
import { ConstatAuditDeleteDialogComponent } from './constat-audit-delete-dialog.component';
import { constatAuditRoute } from './constat-audit.route';

@NgModule({
  imports: [BetterFlySharedModule, RouterModule.forChild(constatAuditRoute)],
  declarations: [ConstatAuditComponent, ConstatAuditDetailComponent, ConstatAuditUpdateComponent, ConstatAuditDeleteDialogComponent],
  entryComponents: [ConstatAuditDeleteDialogComponent],
})
export class BetterFlyConstatAuditModule {}
