import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { CoolPassEnvironmentModule } from './environment/environment.module';
import { CoolPassSharedAccountModule } from './shared-account/shared-account.module';
import { CoolPassRequestModule } from './request/request.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        CoolPassEnvironmentModule,
        CoolPassSharedAccountModule,
        CoolPassRequestModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CoolPassEntityModule {}
