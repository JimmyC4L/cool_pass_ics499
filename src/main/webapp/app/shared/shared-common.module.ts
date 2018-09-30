import { NgModule } from '@angular/core';

import { CoolPassSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [CoolPassSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [CoolPassSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class CoolPassSharedCommonModule {}
