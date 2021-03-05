import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BetterFlySharedModule } from 'app/shared/shared.module';
import { ConstatComponent } from './constat.component';
import { ConstatDetailComponent } from './constat-detail.component';
import { ConstatUpdateComponent } from './constat-update.component';
import { ConstatDeleteDialogComponent } from './constat-delete-dialog.component';
import { constatRoute } from './constat.route';

@NgModule({
  imports: [BetterFlySharedModule, RouterModule.forChild(constatRoute)],
  declarations: [ConstatComponent, ConstatDetailComponent, ConstatUpdateComponent, ConstatDeleteDialogComponent],
  entryComponents: [ConstatDeleteDialogComponent],
})
export class BetterFlyConstatModule {}
