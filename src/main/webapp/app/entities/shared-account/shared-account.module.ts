import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CoolPassSharedModule } from 'app/shared';
import {
    SharedAccountComponent,
    SharedAccountDetailComponent,
    SharedAccountUpdateComponent,
    SharedAccountDeletePopupComponent,
    SharedAccountDeleteDialogComponent,
    sharedAccountRoute,
    sharedAccountPopupRoute
} from './';

const ENTITY_STATES = [...sharedAccountRoute, ...sharedAccountPopupRoute];

@NgModule({
    imports: [CoolPassSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        SharedAccountComponent,
        SharedAccountDetailComponent,
        SharedAccountUpdateComponent,
        SharedAccountDeleteDialogComponent,
        SharedAccountDeletePopupComponent
    ],
    entryComponents: [
        SharedAccountComponent,
        SharedAccountUpdateComponent,
        SharedAccountDeleteDialogComponent,
        SharedAccountDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CoolPassSharedAccountModule {}
