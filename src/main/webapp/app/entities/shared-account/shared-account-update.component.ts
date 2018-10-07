import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ISharedAccount } from 'app/shared/model/shared-account.model';
import { SharedAccountService } from './shared-account.service';
import { IEnvironment } from 'app/shared/model/environment.model';
import { EnvironmentService } from 'app/entities/environment';

@Component({
    selector: 'jhi-shared-account-update',
    templateUrl: './shared-account-update.component.html'
})
export class SharedAccountUpdateComponent implements OnInit {
    private _sharedAccount: ISharedAccount;
    isSaving: boolean;

    environments: IEnvironment[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private sharedAccountService: SharedAccountService,
        private environmentService: EnvironmentService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ sharedAccount }) => {
            this.sharedAccount = sharedAccount;
        });
        this.environmentService.query().subscribe(
            (res: HttpResponse<IEnvironment[]>) => {
                this.environments = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.sharedAccount.id !== undefined) {
            this.subscribeToSaveResponse(this.sharedAccountService.update(this.sharedAccount));
        } else {
            this.subscribeToSaveResponse(this.sharedAccountService.create(this.sharedAccount));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ISharedAccount>>) {
        result.subscribe((res: HttpResponse<ISharedAccount>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackEnvironmentById(index: number, item: IEnvironment) {
        return item.id;
    }
    get sharedAccount() {
        return this._sharedAccount;
    }

    set sharedAccount(sharedAccount: ISharedAccount) {
        this._sharedAccount = sharedAccount;
    }
}
