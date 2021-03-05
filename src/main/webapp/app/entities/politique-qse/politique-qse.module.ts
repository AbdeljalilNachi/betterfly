import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BetterFlySharedModule } from 'app/shared/shared.module';
import { PolitiqueQSEComponent } from './politique-qse.component';
import { PolitiqueQSEDetailComponent } from './politique-qse-detail.component';
import { PolitiqueQSEUpdateComponent } from './politique-qse-update.component';
import { PolitiqueQSEDeleteDialogComponent } from './politique-qse-delete-dialog.component';
import { politiqueQSERoute } from './politique-qse.route';

@NgModule({
  imports: [BetterFlySharedModule, RouterModule.forChild(politiqueQSERoute)],
  declarations: [PolitiqueQSEComponent, PolitiqueQSEDetailComponent, PolitiqueQSEUpdateComponent, PolitiqueQSEDeleteDialogComponent],
  entryComponents: [PolitiqueQSEDeleteDialogComponent],
})
export class BetterFlyPolitiqueQSEModule {}
