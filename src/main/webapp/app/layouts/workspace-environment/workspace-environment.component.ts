import {Component, OnDestroy, OnInit} from '@angular/core';
import {HttpErrorResponse, HttpHeaders, HttpResponse} from '@angular/common/http';
import {EnvironmentService} from 'app/entities/environment/environment.service';
import {Principal} from 'app/core';
import {JhiAlertService, JhiEventManager, JhiParseLinks} from 'ng-jhipster';
import {Subscription} from 'rxjs/';
import {IEnvironment} from 'app/shared/model/environment.model';
import {ITEMS_PER_PAGE} from 'app/shared';
@Component({
  selector: 'jhi-workspace-environment',
  templateUrl: './workspace-environment.component.html',
  styles: []
})
export class WorkspaceEnvironmentComponent implements OnInit, OnDestroy {

    environments: IEnvironment[];
    currentAccount: any;
    eventSubscriber: Subscription;
    itemsPerPage: number;
    links: any;
    page: any;
    predicate: any;
    queryCount: any;
    reverse: any;
    totalItems: number;

    tableRowOpen = false;

    constructor(
        private environmentService: EnvironmentService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private parseLinks: JhiParseLinks,
        private principal: Principal
    ) {
        this.environments = [];
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.page = 0;
        this.links = {
            last: 0
        };
        this.predicate = 'id';
        this.reverse = true;
    }

    loadAll() {
        this.environmentService
            .query({
                page: this.page,
                size: this.itemsPerPage,
                sort: this.sort()
            })
            .subscribe(
                (res: HttpResponse<IEnvironment[]>) => this.paginateEnvironments(res.body, res.headers),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    reset() {
        this.page = 0;
        this.environments = [];
        this.loadAll();
    }

    loadPage(page) {
        this.page = page;
        this.loadAll();
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInEnvironments();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IEnvironment) {
        return item.id;
    }

    registerChangeInEnvironments() {
        this.eventSubscriber = this.eventManager.subscribe('environmentListModification', response => this.reset());
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    private paginateEnvironments(data: IEnvironment[], headers: HttpHeaders) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
        for (let i = 0; i < data.length; i++) {
            this.environments.push(data[i]);
        }
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    toggleRow() {
        this.tableRowOpen = !this.tableRowOpen;
    }
}
