import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BetterFlySharedModule } from 'app/shared/shared.module';
import { ActionComponent } from './action.component';
import { ActionDetailComponent } from './action-detail.component';
import { ActionUpdateComponent } from './action-update.component';
import { ActionDeleteDialogComponent } from './action-delete-dialog.component';
import { actionRoute } from './action.route';

@NgModule({
  imports: [BetterFlySharedModule, RouterModule.forChild(actionRoute)],
  declarations: [ActionComponent, ActionDetailComponent, ActionUpdateComponent, ActionDeleteDialogComponent],
  entryComponents: [ActionDeleteDialogComponent],
})
export class BetterFlyActionModule {}
