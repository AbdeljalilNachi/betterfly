import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BetterFlySharedModule } from 'app/shared/shared.module';
import { ObjectifComponent } from './objectif.component';
import { ObjectifDetailComponent } from './objectif-detail.component';
import { ObjectifUpdateComponent } from './objectif-update.component';
import { ObjectifDeleteDialogComponent } from './objectif-delete-dialog.component';
import { objectifRoute } from './objectif.route';

@NgModule({
  imports: [BetterFlySharedModule, RouterModule.forChild(objectifRoute)],
  declarations: [ObjectifComponent, ObjectifDetailComponent, ObjectifUpdateComponent, ObjectifDeleteDialogComponent],
  entryComponents: [ObjectifDeleteDialogComponent],
})
export class BetterFlyObjectifModule {}
