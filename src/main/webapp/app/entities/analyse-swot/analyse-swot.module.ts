import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BetterFlySharedModule } from 'app/shared/shared.module';
import { AnalyseSWOTComponent } from './analyse-swot.component';
import { AnalyseSWOTDetailComponent } from './analyse-swot-detail.component';
import { AnalyseSWOTUpdateComponent } from './analyse-swot-update.component';
import { AnalyseSWOTDeleteDialogComponent } from './analyse-swot-delete-dialog.component';
import { analyseSWOTRoute } from './analyse-swot.route';

@NgModule({
  imports: [BetterFlySharedModule, RouterModule.forChild(analyseSWOTRoute)],
  declarations: [AnalyseSWOTComponent, AnalyseSWOTDetailComponent, AnalyseSWOTUpdateComponent, AnalyseSWOTDeleteDialogComponent],
  entryComponents: [AnalyseSWOTDeleteDialogComponent],
})
export class BetterFlyAnalyseSWOTModule {}
