import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { BetterFlySharedModule } from 'app/shared/shared.module';
import { BetterFlyCoreModule } from 'app/core/core.module';
import { BetterFlyAppRoutingModule } from './app-routing.module';
import { BetterFlyHomeModule } from './home/home.module';
import { BetterFlyEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';
import { LeftSideMenuComponent } from './layouts/left-side-menu/left-side-menu.component';

@NgModule({
  imports: [
    BrowserModule,
    BetterFlySharedModule,
    BetterFlyCoreModule,
    BetterFlyHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    BetterFlyEntityModule,
    BetterFlyAppRoutingModule,
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent, LeftSideMenuComponent],
  bootstrap: [MainComponent],
})
export class BetterFlyAppModule {}
