import {Component, OnInit} from '@angular/core';
import {DataSource} from '@angular/cdk/collections';
import {Observable, of} from 'rxjs';
import {animate, state, style, transition, trigger} from '@angular/animations';
import {HttpErrorResponse, HttpHeaders, HttpResponse} from '@angular/common/http';
import {IEnvironment} from 'app/shared/model/environment.model';
import {EnvironmentService} from 'app/entities/environment/environment.service';
import {JhiAlertService, JhiParseLinks} from 'ng-jhipster';
import {ITEMS_PER_PAGE} from 'app/shared';
import {Principal} from 'app/core';
import {SharedAccountService} from 'app/entities/shared-account';
import {ISharedAccount} from 'app/shared/model/shared-account.model';

@Component({
    selector: 'app-table',
    templateUrl: './table.component.html',
    styleUrls: ['./table.component.scss'],
    animations: [
        trigger('detailExpand', [
            state('collapsed', style({height: '0px', minHeight: '0', visibility: 'hidden'})),
            state('expanded', style({height: '*', visibility: 'visible'})),
            transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
        ]),
    ],
})
export class TableComponent implements OnInit {
    data: IEnvironment[] = [];
    sharedAccounts: ISharedAccount[];
    currentAccount: any;
    displayedColumns = ['id', 'server', 'name'];
    dataSource: ExampleDataSource;
    expandedElement: any;
    allEnvironments: IEnvironment[];
    authorityNames: string[];

    queryCount: any;
    itemsPerPage: number;
    links: any;
    page: any;
    predicate: any;
    reverse: any;
    totalItems: number;

    isExpansionDetailRow = (i: number, row: Object) => row.hasOwnProperty('detailRow');

    constructor(private environmentService: EnvironmentService,
                private sharedAccountService: SharedAccountService,
                private jhiAlertService: JhiAlertService,
                private parseLinks: JhiParseLinks,
                private principal: Principal) {
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.page = 0;
        this.links = {
            last: 0
        };
        this.predicate = 'id';
        this.reverse = true;
    }

    loadAllEnvironmentsAndFilterBasedOnAuthority() {
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

    private paginateEnvironments(data: IEnvironment[], headers: HttpHeaders) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
        for (let i = 0; i < data.length; i++) {
            this.allEnvironments.push(data[i]);
        }
        this.getEnvironmentsByAuthority();
    }

    loadAllSharedAccount() {
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

    private paginateSharedAccounts(data: ISharedAccount[], headers: HttpHeaders) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
        this.queryCount = this.totalItems;
        this.sharedAccounts = data;
        this.getSharedAccountsByEnvironments();
    }

    private getSharedAccountsByEnvironments() {
        for (let environment of this.data) {
            var sharedAccountsByEnvironment : ISharedAccount[] = [];
            for (let sharedAccount  of this.sharedAccounts) {
                if(sharedAccount.environment.id == environment.id){
                    sharedAccountsByEnvironment.push(sharedAccount);
                }
            }
            environment.sharedAccounts = sharedAccountsByEnvironment;
        }
        this.dataSource = new ExampleDataSource(this.data);
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    private getEnvironmentsByAuthority() {
        for (let environment of this.allEnvironments) {
            this.authorityNames = environment.authority_name.split('/');
            for (let authority  of this.currentAccount.authorities) {
                if (this.authorityNames.includes(authority)) {
                    this.data.push(environment);
                    break;
                }
            }
        }
    }

    private generateSharedAccountsListForDisplay(detail : any) {
        let finalString : string[] = ["\n","The list of shared accounts for this environment:\n"];
        for (let sharedAccount of detail.element.sharedAccounts) {
            finalString.push("Login: " + sharedAccount.login + "    ");
            finalString.push("Password: " + sharedAccount.password + "\n");
        }
        return finalString.join("");
    }

    ngOnInit(): void {
        this.allEnvironments = [];
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.loadAllEnvironmentsAndFilterBasedOnAuthority();
        this.loadAllSharedAccount();
        // this.getEnvironmentsByAuthority();
    }

}
/**
 * Data source to provide what data should be rendered in the table. The observable provided
 * in connect should emit exactly the data that should be rendered by the table. If the data is
 * altered, the observable should emit that new set of data on the stream. In our case here,
 * we return a stream that contains only one set of data that doesn't change.
 */
export class ExampleDataSource extends DataSource<any> {
    data: IEnvironment[];

    constructor(data: IEnvironment[]) {
        super();
        this.data = data;
    }

    /** Connect function called by the ta ble to retrieve one stream containing the data to render. */
    connect(): Observable<IEnvironment[]> {
        const rows = [];
        this.data.forEach(element => rows.push(element, {detailRow: true, element}));
        return of(rows);
    }

    disconnect() {
    }
}
