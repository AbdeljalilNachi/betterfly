import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BetterFlySharedModule } from 'app/shared/shared.module';
import { IndicateurSMIComponent } from './indicateur-smi.component';
import { IndicateurSMIDetailComponent } from './indicateur-smi-detail.component';
import { IndicateurSMIUpdateComponent } from './indicateur-smi-update.component';
import { IndicateurSMIDeleteDialogComponent } from './indicateur-smi-delete-dialog.component';
import { indicateurSMIRoute } from './indicateur-smi.route';

@NgModule({
  imports: [BetterFlySharedModule, RouterModule.forChild(indicateurSMIRoute)],
  declarations: [IndicateurSMIComponent, IndicateurSMIDetailComponent, IndicateurSMIUpdateComponent, IndicateurSMIDeleteDialogComponent],
  entryComponents: [IndicateurSMIDeleteDialogComponent],
})
export class BetterFlyIndicateurSMIModule {}
