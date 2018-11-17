import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { CoolPassSharedModule } from 'app/shared';
import {
    SharedAccountComponent,
    SharedAccountDetailComponent,
    SharedAccountUpdateComponent,
    SharedAccountDeletePopupComponent,
    SharedAccountDeleteDialogComponent,
    SharedAccountImportComponent,
    sharedAccountRoute,
    sharedAccountPopupRoute
} from './';
import {FileUploadModule} from "ng2-file-upload";

const ENTITY_STATES = [...sharedAccountRoute, ...sharedAccountPopupRoute];

@NgModule({
    imports: [CoolPassSharedModule, RouterModule.forChild(ENTITY_STATES), HttpClientModule, FileUploadModule],
    declarations: [
        SharedAccountComponent,
        SharedAccountDetailComponent,
        SharedAccountUpdateComponent,
        SharedAccountImportComponent,
        SharedAccountDeleteDialogComponent,
        SharedAccountDeletePopupComponent
    ],
    entryComponents: [
        SharedAccountComponent,
        SharedAccountUpdateComponent,
        SharedAccountImportComponent,
        SharedAccountDeleteDialogComponent,
        SharedAccountDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CoolPassSharedAccountModule {}
