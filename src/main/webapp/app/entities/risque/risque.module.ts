import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BetterFlySharedModule } from 'app/shared/shared.module';
import { RisqueComponent } from './risque.component';
import { RisqueDetailComponent } from './risque-detail.component';
import { RisqueUpdateComponent } from './risque-update.component';
import { RisqueDeleteDialogComponent } from './risque-delete-dialog.component';
import { risqueRoute } from './risque.route';

@NgModule({
  imports: [BetterFlySharedModule, RouterModule.forChild(risqueRoute)],
  declarations: [RisqueComponent, RisqueDetailComponent, RisqueUpdateComponent, RisqueDeleteDialogComponent],
  entryComponents: [RisqueDeleteDialogComponent],
})
export class BetterFlyRisqueModule {}
