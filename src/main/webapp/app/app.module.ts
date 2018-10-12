import './vendor.ts';

import { NgModule, Injector } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import {NgbDatepickerConfig, NgbModule} from '@ng-bootstrap/ng-bootstrap';
import { Ng2Webstorage } from 'ngx-webstorage';
import { JhiEventManager } from 'ng-jhipster';

import { AuthExpiredInterceptor } from './blocks/interceptor/auth-expired.interceptor';
import { ErrorHandlerInterceptor } from './blocks/interceptor/errorhandler.interceptor';
import { NotificationInterceptor } from './blocks/interceptor/notification.interceptor';
import { CoolPassSharedModule } from 'app/shared';
import { CoolPassCoreModule } from 'app/core';
import { CoolPassAppRoutingModule } from './app-routing.module';
import { CoolPassHomeModule } from 'app/home';
import { CoolPassAccountModule } from './account/account.module';
import { CoolPassEntityModule } from './entities/entity.module';
import { StateStorageService } from 'app/core/auth/state-storage.service';

import * as moment from 'moment';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { JhiMainComponent, NavbarComponent, FooterComponent, PageRibbonComponent, ErrorComponent } from './layouts';
import { WorkspaceEnvironmentComponent } from 'app/layouts';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatTableModule, MatToolbarModule} from '@angular/material';
import {TableComponent} from 'app/layouts/workspace-environment/table.component';

@NgModule({
    imports: [
        BrowserModule,
        BrowserAnimationsModule,
        MatToolbarModule,
        MatTableModule,
        CoolPassAppRoutingModule,
        Ng2Webstorage.forRoot({ prefix: 'jhi', separator: '-' }),
        NgbModule,
        CoolPassSharedModule,
        CoolPassCoreModule,
        CoolPassHomeModule,
        CoolPassAccountModule,
        CoolPassEntityModule,
        // jhipster-needle-angular-add-module JHipster will add new module here
    ],
    declarations: [JhiMainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent, WorkspaceEnvironmentComponent, TableComponent],
    providers: [
        {
            provide: HTTP_INTERCEPTORS,
            useClass: AuthExpiredInterceptor,
            multi: true,
            deps: [StateStorageService, Injector]
        },
        {
            provide: HTTP_INTERCEPTORS,
            useClass: ErrorHandlerInterceptor,
            multi: true,
            deps: [JhiEventManager]
        },
        {
            provide: HTTP_INTERCEPTORS,
            useClass: NotificationInterceptor,
            multi: true,
            deps: [Injector]
        }
    ],
    bootstrap: [JhiMainComponent]
})
export class CoolPassAppModule {
    constructor(private dpConfig: NgbDatepickerConfig) {
        this.dpConfig.minDate = { year: moment().year() - 100, month: 1, day: 1 };
    }
}
