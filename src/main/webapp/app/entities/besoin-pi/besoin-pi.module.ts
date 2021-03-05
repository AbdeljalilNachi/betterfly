import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BetterFlySharedModule } from 'app/shared/shared.module';
import { BesoinPIComponent } from './besoin-pi.component';
import { BesoinPIDetailComponent } from './besoin-pi-detail.component';
import { BesoinPIUpdateComponent } from './besoin-pi-update.component';
import { BesoinPIDeleteDialogComponent } from './besoin-pi-delete-dialog.component';
import { besoinPIRoute } from './besoin-pi.route';

@NgModule({
  imports: [BetterFlySharedModule, RouterModule.forChild(besoinPIRoute)],
  declarations: [BesoinPIComponent, BesoinPIDetailComponent, BesoinPIUpdateComponent, BesoinPIDeleteDialogComponent],
  entryComponents: [BesoinPIDeleteDialogComponent],
})
export class BetterFlyBesoinPIModule {}
