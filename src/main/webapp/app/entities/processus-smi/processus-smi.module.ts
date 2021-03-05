import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BetterFlySharedModule } from 'app/shared/shared.module';
import { ProcessusSMIComponent } from './processus-smi.component';
import { ProcessusSMIDetailComponent } from './processus-smi-detail.component';
import { ProcessusSMIUpdateComponent } from './processus-smi-update.component';
import { ProcessusSMIDeleteDialogComponent } from './processus-smi-delete-dialog.component';
import { processusSMIRoute } from './processus-smi.route';

@NgModule({
  imports: [BetterFlySharedModule, RouterModule.forChild(processusSMIRoute)],
  declarations: [ProcessusSMIComponent, ProcessusSMIDetailComponent, ProcessusSMIUpdateComponent, ProcessusSMIDeleteDialogComponent],
  entryComponents: [ProcessusSMIDeleteDialogComponent],
})
export class BetterFlyProcessusSMIModule {}
