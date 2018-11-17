import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { SharedAccount } from 'app/shared/model/shared-account.model';
import { SharedAccountService } from './shared-account.service';
import { SharedAccountComponent } from './shared-account.component';
import { SharedAccountDetailComponent } from './shared-account-detail.component';
import { SharedAccountUpdateComponent } from './shared-account-update.component';
import { SharedAccountDeletePopupComponent } from './shared-account-delete-dialog.component';
import { ISharedAccount } from 'app/shared/model/shared-account.model';
import { SharedAccountImportComponent } from 'app/entities/shared-account/shared-account-import.component';

@Injectable({ providedIn: 'root' })
export class SharedAccountResolve implements Resolve<ISharedAccount> {
    constructor(private service: SharedAccountService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((sharedAccount: HttpResponse<SharedAccount>) => sharedAccount.body));
        }
        return of(new SharedAccount());
    }
}

export const sharedAccountRoute: Routes = [
    {
        path: 'shared-account',
        component: SharedAccountComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER', 'ROLE_BUSINESS_OWNER'],
            defaultSort: 'id,asc',
            pageTitle: 'SharedAccounts'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'shared-account/:id/view',
        component: SharedAccountDetailComponent,
        resolve: {
            sharedAccount: SharedAccountResolve
        },
        data: {
            authorities: ['ROLE_USER', 'ROLE_BUSINESS_OWNER'],
            pageTitle: 'SharedAccounts'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'shared-account/new',
        component: SharedAccountUpdateComponent,
        resolve: {
            sharedAccount: SharedAccountResolve
        },
        data: {
            authorities: ['ROLE_USER', 'ROLE_BUSINESS_OWNER'],
            pageTitle: 'SharedAccounts'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'shared-account/import',
        component: SharedAccountImportComponent,
        resolve: {
            sharedAccount: SharedAccountResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SharedAccounts'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'shared-account/:id/edit',
        component: SharedAccountUpdateComponent,
        resolve: {
            sharedAccount: SharedAccountResolve
        },
        data: {
            authorities: ['ROLE_USER', 'ROLE_BUSINESS_OWNER'],
            pageTitle: 'SharedAccounts'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const sharedAccountPopupRoute: Routes = [
    {
        path: 'shared-account/:id/delete',
        component: SharedAccountDeletePopupComponent,
        resolve: {
            sharedAccount: SharedAccountResolve
        },
        data: {
            authorities: ['ROLE_USER', 'ROLE_BUSINESS_OWNER'],
            pageTitle: 'SharedAccounts'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
