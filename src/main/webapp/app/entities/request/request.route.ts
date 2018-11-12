import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Request } from 'app/shared/model/request.model';
import { RequestService } from './request.service';
import { RequestComponent } from './request.component';
import { RequestDetailComponent } from './request-detail.component';
import { RequestUpdateComponent } from './request-update.component';
import { RequestDeletePopupComponent } from './request-delete-dialog.component';
import { IRequest } from 'app/shared/model/request.model';

@Injectable({ providedIn: 'root' })
export class RequestResolve implements Resolve<IRequest> {
    constructor(private service: RequestService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((request: HttpResponse<Request>) => request.body));
        }
        return of(new Request());
    }
}

export const requestRoute: Routes = [
    {
        path: 'request',
        component: RequestComponent,
        data: {
            authorities: ['ROLE_USER', 'ROLE_BUSINESS_OWNER'],
            pageTitle: 'Requests'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'request/:id/view',
        component: RequestDetailComponent,
        resolve: {
            request: RequestResolve
        },
        data: {
            authorities: ['ROLE_USER', 'ROLE_BUSINESS_OWNER'],
            pageTitle: 'Requests'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'request/new',
        component: RequestUpdateComponent,
        resolve: {
            request: RequestResolve
        },
        data: {
            authorities: ['ROLE_USER', 'ROLE_BUSINESS_OWNER'],
            pageTitle: 'Requests'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'request/:id/edit',
        component: RequestUpdateComponent,
        resolve: {
            request: RequestResolve
        },
        data: {
            authorities: ['ROLE_USER', 'ROLE_BUSINESS_OWNER'],
            pageTitle: 'Requests'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const requestPopupRoute: Routes = [
    {
        path: 'request/:id/delete',
        component: RequestDeletePopupComponent,
        resolve: {
            request: RequestResolve
        },
        data: {
            authorities: ['ROLE_USER', 'ROLE_BUSINESS_OWNER'],
            pageTitle: 'Requests'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
