import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BetterFlySharedModule } from 'app/shared/shared.module';
import { PlanificationRDDComponent } from './planification-rdd.component';
import { PlanificationRDDDetailComponent } from './planification-rdd-detail.component';
import { PlanificationRDDUpdateComponent } from './planification-rdd-update.component';
import { PlanificationRDDDeleteDialogComponent } from './planification-rdd-delete-dialog.component';
import { planificationRDDRoute } from './planification-rdd.route';

@NgModule({
  imports: [BetterFlySharedModule, RouterModule.forChild(planificationRDDRoute)],
  declarations: [
    PlanificationRDDComponent,
    PlanificationRDDDetailComponent,
    PlanificationRDDUpdateComponent,
    PlanificationRDDDeleteDialogComponent,
  ],
  entryComponents: [PlanificationRDDDeleteDialogComponent],
})
export class BetterFlyPlanificationRDDModule {}
