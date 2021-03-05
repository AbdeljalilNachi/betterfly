import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BetterFlySharedModule } from 'app/shared/shared.module';
import { ResultatIndicateurComponent } from './resultat-indicateur.component';
import { ResultatIndicateurDetailComponent } from './resultat-indicateur-detail.component';
import { ResultatIndicateurUpdateComponent } from './resultat-indicateur-update.component';
import { ResultatIndicateurDeleteDialogComponent } from './resultat-indicateur-delete-dialog.component';
import { resultatIndicateurRoute } from './resultat-indicateur.route';

@NgModule({
  imports: [BetterFlySharedModule, RouterModule.forChild(resultatIndicateurRoute)],
  declarations: [
    ResultatIndicateurComponent,
    ResultatIndicateurDetailComponent,
    ResultatIndicateurUpdateComponent,
    ResultatIndicateurDeleteDialogComponent,
  ],
  entryComponents: [ResultatIndicateurDeleteDialogComponent],
})
export class BetterFlyResultatIndicateurModule {}
