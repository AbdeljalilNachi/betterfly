import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BetterFlySharedModule } from 'app/shared/shared.module';
import { ProcessusComponent } from './processus.component';
import { ProcessusDetailComponent } from './processus-detail.component';
import { ProcessusUpdateComponent } from './processus-update.component';
import { ProcessusDeleteDialogComponent } from './processus-delete-dialog.component';
import { processusRoute } from './processus.route';

@NgModule({
  imports: [BetterFlySharedModule, RouterModule.forChild(processusRoute)],
  declarations: [ProcessusComponent, ProcessusDetailComponent, ProcessusUpdateComponent, ProcessusDeleteDialogComponent],
  entryComponents: [ProcessusDeleteDialogComponent],
})
export class BetterFlyProcessusModule {}
