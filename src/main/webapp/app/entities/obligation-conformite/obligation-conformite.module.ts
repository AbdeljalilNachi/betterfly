import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BetterFlySharedModule } from 'app/shared/shared.module';
import { ObligationConformiteComponent } from './obligation-conformite.component';
import { ObligationConformiteDetailComponent } from './obligation-conformite-detail.component';
import { ObligationConformiteUpdateComponent } from './obligation-conformite-update.component';
import { ObligationConformiteDeleteDialogComponent } from './obligation-conformite-delete-dialog.component';
import { obligationConformiteRoute } from './obligation-conformite.route';

@NgModule({
  imports: [BetterFlySharedModule, RouterModule.forChild(obligationConformiteRoute)],
  declarations: [
    ObligationConformiteComponent,
    ObligationConformiteDetailComponent,
    ObligationConformiteUpdateComponent,
    ObligationConformiteDeleteDialogComponent,
  ],
  entryComponents: [ObligationConformiteDeleteDialogComponent],
})
export class BetterFlyObligationConformiteModule {}
