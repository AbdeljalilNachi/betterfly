import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BetterFlySharedModule } from 'app/shared/shared.module';
import { AutreActionComponent } from './autre-action.component';
import { AutreActionDetailComponent } from './autre-action-detail.component';
import { AutreActionUpdateComponent } from './autre-action-update.component';
import { AutreActionDeleteDialogComponent } from './autre-action-delete-dialog.component';
import { autreActionRoute } from './autre-action.route';

@NgModule({
  imports: [BetterFlySharedModule, RouterModule.forChild(autreActionRoute)],
  declarations: [AutreActionComponent, AutreActionDetailComponent, AutreActionUpdateComponent, AutreActionDeleteDialogComponent],
  entryComponents: [AutreActionDeleteDialogComponent],
})
export class BetterFlyAutreActionModule {}
