import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BetterFlySharedModule } from 'app/shared/shared.module';
import { AnalyseEnvirommentaleComponent } from './analyse-envirommentale.component';
import { AnalyseEnvirommentaleDetailComponent } from './analyse-envirommentale-detail.component';
import { AnalyseEnvirommentaleUpdateComponent } from './analyse-envirommentale-update.component';
import { AnalyseEnvirommentaleDeleteDialogComponent } from './analyse-envirommentale-delete-dialog.component';
import { analyseEnvirommentaleRoute } from './analyse-envirommentale.route';

@NgModule({
  imports: [BetterFlySharedModule, RouterModule.forChild(analyseEnvirommentaleRoute)],
  declarations: [
    AnalyseEnvirommentaleComponent,
    AnalyseEnvirommentaleDetailComponent,
    AnalyseEnvirommentaleUpdateComponent,
    AnalyseEnvirommentaleDeleteDialogComponent,
  ],
  entryComponents: [AnalyseEnvirommentaleDeleteDialogComponent],
})
export class BetterFlyAnalyseEnvirommentaleModule {}
