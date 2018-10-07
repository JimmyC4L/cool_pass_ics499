import { UserRouteAccessService } from 'app/core';
import {WorkspaceEnvironmentComponent} from 'app/layouts/workspace-environment/workspace-environment.component';
import {Route} from '@angular/router';

export const workspaceEnvironmentRoute: Route  = {
        path: 'workspace-environment',
        component: WorkspaceEnvironmentComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Workspace Environments'
        },
        canActivate: [UserRouteAccessService]
};
