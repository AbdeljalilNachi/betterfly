import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BetterFlySharedModule } from 'app/shared/shared.module';
import { AnalyseSSTComponent } from './analyse-sst.component';
import { AnalyseSSTDetailComponent } from './analyse-sst-detail.component';
import { AnalyseSSTUpdateComponent } from './analyse-sst-update.component';
import { AnalyseSSTDeleteDialogComponent } from './analyse-sst-delete-dialog.component';
import { analyseSSTRoute } from './analyse-sst.route';

@NgModule({
  imports: [BetterFlySharedModule, RouterModule.forChild(analyseSSTRoute)],
  declarations: [AnalyseSSTComponent, AnalyseSSTDetailComponent, AnalyseSSTUpdateComponent, AnalyseSSTDeleteDialogComponent],
  entryComponents: [AnalyseSSTDeleteDialogComponent],
})
export class BetterFlyAnalyseSSTModule {}
