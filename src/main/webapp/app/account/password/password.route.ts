import { Route } from '@angular/router';

import { UserRouteAccessService } from 'app/core';
import { PasswordComponent } from './password.component';

export const passwordRoute: Route = {
    path: 'password',
    component: PasswordComponent,
    data: {
        authorities: ['ROLE_USER','ROLE_BUSINESS_OWNER'],
        pageTitle: 'Password'
    },
    canActivate: [UserRouteAccessService]
};
