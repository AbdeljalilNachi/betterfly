import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BetterFlySharedModule } from 'app/shared/shared.module';
import { NonConformiteComponent } from './non-conformite.component';
import { NonConformiteDetailComponent } from './non-conformite-detail.component';
import { NonConformiteUpdateComponent } from './non-conformite-update.component';
import { NonConformiteDeleteDialogComponent } from './non-conformite-delete-dialog.component';
import { nonConformiteRoute } from './non-conformite.route';

@NgModule({
  imports: [BetterFlySharedModule, RouterModule.forChild(nonConformiteRoute)],
  declarations: [NonConformiteComponent, NonConformiteDetailComponent, NonConformiteUpdateComponent, NonConformiteDeleteDialogComponent],
  entryComponents: [NonConformiteDeleteDialogComponent],
})
export class BetterFlyNonConformiteModule {}
