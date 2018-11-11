import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';

import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { ISharedAccount } from 'app/shared/model/shared-account.model';
import { Principal } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { SharedAccountService } from './shared-account.service';
import { IEnvironment } from 'app/shared/model/environment.model';
import { EnvironmentService } from 'app/entities/environment';

@Component({
    selector: 'jhi-shared-account',
    templateUrl: './shared-account.component.html'
})
export class SharedAccountComponent implements OnInit, OnDestroy {
    currentAccount: any;
    envId: number;
    login: string;
    password: string;
    sharedAccounts: ISharedAccount[];
    environments: IEnvironment[];
    error: any;
    success: any;
    eventSubscriber: Subscription;
    routeData: any;
    links: any;
    totalItems: any;
    queryCount: any;
    itemsPerPage: any;
    page: any;
    predicate: any;
    previousPage: any;
    reverse: any;

    constructor(
        private sharedAccountService: SharedAccountService,
        private parseLinks: JhiParseLinks,
        private jhiAlertService: JhiAlertService,
        private principal: Principal,
        private activatedRoute: ActivatedRoute,
        private environmentService: EnvironmentService,
        private router: Router,
        private eventManager: JhiEventManager
    ) {
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.routeData = this.activatedRoute.data.subscribe(data => {
            this.page = data.pagingParams.page;
            this.environments = [];
            this.previousPage = data.pagingParams.page;
            this.reverse = data.pagingParams.ascending;
            this.predicate = data.pagingParams.predicate;
        });
    }

    loadAll() {
        this.sharedAccountService
            .query({
                page: this.page - 1,
                size: this.itemsPerPage,
                sort: this.sort()
            })
            .subscribe(
                (res: HttpResponse<ISharedAccount[]>) => this.paginateSharedAccounts(res.body, res.headers),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    search() {
        if (this.envId != null) {
            this.sharedAccountService
                .findAllByEnvironment(this.envId)
                .subscribe(
                    (res: HttpResponse<ISharedAccount[]>) => this.paginateSharedAccounts(res.body, res.headers),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
        } else if (this.login != null) {
            this.sharedAccountService
                .findAllByLogin(this.login)
                .subscribe(
                    (res: HttpResponse<ISharedAccount[]>) => this.paginateSharedAccounts(res.body, res.headers),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
        }
    }

    loadPage(page: number) {
        if (page !== this.previousPage) {
            this.previousPage = page;
            this.transition();
        }
    }

    transition() {
        this.router.navigate(['/shared-account'], {
            queryParams: {
                page: this.page,
                size: this.itemsPerPage,
                sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
            }
        });
        this.loadAll();
    }

    clear() {
        this.page = 0;
        this.router.navigate([
            '/shared-account',
            {
                page: this.page,
                sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
            }
        ]);
        this.environmentService.query().subscribe(
            (res: HttpResponse<IEnvironment[]>) => {
                this.environments = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.loadAll();
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInSharedAccounts();
        this.environmentService.query().subscribe(
            (res: HttpResponse<IEnvironment[]>) => {
                this.environments = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ISharedAccount) {
        return item.id;
    }

    trackEnvironmentById(index: number, item: IEnvironment) {
        return item.id;
    }

    registerChangeInSharedAccounts() {
        this.eventSubscriber = this.eventManager.subscribe('sharedAccountListModification', response => this.loadAll());
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    private paginateSharedAccounts(data: ISharedAccount[], headers: HttpHeaders) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
        this.queryCount = this.totalItems;
        this.sharedAccounts = data;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
