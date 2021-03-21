import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'analyse-swot',
        loadChildren: () => import('./analyse-swot/analyse-swot.module').then(m => m.BetterFlyAnalyseSWOTModule),
      },
      {
        path: 'besoin-pi',
        loadChildren: () => import('./besoin-pi/besoin-pi.module').then(m => m.BetterFlyBesoinPIModule),
      },
      {
        path: 'politique-qse',
        loadChildren: () => import('./politique-qse/politique-qse.module').then(m => m.BetterFlyPolitiqueQSEModule),
      },
      {
        path: 'risque',
        loadChildren: () => import('./risque/risque.module').then(m => m.BetterFlyRisqueModule),
      },
      {
        path: 'analyse-sst',
        loadChildren: () => import('./analyse-sst/analyse-sst.module').then(m => m.BetterFlyAnalyseSSTModule),
      },
      {
        path: 'constat',
        loadChildren: () => import('./constat/constat.module').then(m => m.BetterFlyConstatModule),
      },
      {
        path: 'action',
        loadChildren: () => import('./action/action.module').then(m => m.BetterFlyActionModule),
      },
      {
        path: 'non-conformite',
        loadChildren: () => import('./non-conformite/non-conformite.module').then(m => m.BetterFlyNonConformiteModule),
      },
      {
        path: 'objectif',
        loadChildren: () => import('./objectif/objectif.module').then(m => m.BetterFlyObjectifModule),
      },
      {
        path: 'reclamation',
        loadChildren: () => import('./reclamation/reclamation.module').then(m => m.BetterFlyReclamationModule),
      },
      {
        path: 'obligation-conformite',
        loadChildren: () => import('./obligation-conformite/obligation-conformite.module').then(m => m.BetterFlyObligationConformiteModule),
      },
      {
        path: 'analyse-envirommentale',
        loadChildren: () =>
          import('./analyse-envirommentale/analyse-envirommentale.module').then(m => m.BetterFlyAnalyseEnvirommentaleModule),
      },
      {
        path: 'autre-action',
        loadChildren: () => import('./autre-action/autre-action.module').then(m => m.BetterFlyAutreActionModule),
      },
      {
        path: 'processus-smi',
        loadChildren: () => import('./processus-smi/processus-smi.module').then(m => m.BetterFlyProcessusSMIModule),
      },
      {
        path: 'resultat-indicateur',
        loadChildren: () => import('./resultat-indicateur/resultat-indicateur.module').then(m => m.BetterFlyResultatIndicateurModule),
      },
      {
        path: 'indicateur-smi',
        loadChildren: () => import('./indicateur-smi/indicateur-smi.module').then(m => m.BetterFlyIndicateurSMIModule),
      },
      {
        path: 'result-indicateurs',
        loadChildren: () => import('./result-indicateurs/result-indicateurs.module').then(m => m.BetterFlyResultIndicateursModule),
      },
      {
        path: 'audit',
        loadChildren: () => import('./audit/audit.module').then(m => m.BetterFlyAuditModule),
      },
      {
        path: 'constat-audit',
        loadChildren: () => import('./constat-audit/constat-audit.module').then(m => m.BetterFlyConstatAuditModule),
      },
      {
        path: 'planification-rdd',
        loadChildren: () => import('./planification-rdd/planification-rdd.module').then(m => m.BetterFlyPlanificationRDDModule),
      },
      {
        path: 'document',
        loadChildren: () => import('./document/document.module').then(m => m.BetterFlyDocumentModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class BetterFlyEntityModule {}
