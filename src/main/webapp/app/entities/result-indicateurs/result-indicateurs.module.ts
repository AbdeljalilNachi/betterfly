import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BetterFlySharedModule } from 'app/shared/shared.module';
import { ResultIndicateursComponent } from './result-indicateurs.component';
import { ResultIndicateursDetailComponent } from './result-indicateurs-detail.component';
import { ResultIndicateursUpdateComponent } from './result-indicateurs-update.component';
import { ResultIndicateursDeleteDialogComponent } from './result-indicateurs-delete-dialog.component';
import { resultIndicateursRoute } from './result-indicateurs.route';

@NgModule({
  imports: [BetterFlySharedModule, RouterModule.forChild(resultIndicateursRoute)],
  declarations: [
    ResultIndicateursComponent,
    ResultIndicateursDetailComponent,
    ResultIndicateursUpdateComponent,
    ResultIndicateursDeleteDialogComponent,
  ],
  entryComponents: [ResultIndicateursDeleteDialogComponent],
})
export class BetterFlyResultIndicateursModule {}
