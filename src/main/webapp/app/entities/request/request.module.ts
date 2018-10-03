import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CoolPassSharedModule } from 'app/shared';
import {
    RequestComponent,
    RequestDetailComponent,
    RequestUpdateComponent,
    RequestDeletePopupComponent,
    RequestDeleteDialogComponent,
    requestRoute,
    requestPopupRoute
} from './';

const ENTITY_STATES = [...requestRoute, ...requestPopupRoute];

@NgModule({
    imports: [CoolPassSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        RequestComponent,
        RequestDetailComponent,
        RequestUpdateComponent,
        RequestDeleteDialogComponent,
        RequestDeletePopupComponent
    ],
    entryComponents: [RequestComponent, RequestUpdateComponent, RequestDeleteDialogComponent, RequestDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CoolPassRequestModule {}
