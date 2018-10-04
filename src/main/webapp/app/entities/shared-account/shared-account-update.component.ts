import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ISharedAccount } from 'app/shared/model/shared-account.model';
import { SharedAccountService } from './shared-account.service';

@Component({
    selector: 'jhi-shared-account-update',
    templateUrl: './shared-account-update.component.html'
})
export class SharedAccountUpdateComponent implements OnInit {
    private _sharedAccount: ISharedAccount;
    isSaving: boolean;

    constructor(private sharedAccountService: SharedAccountService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ sharedAccount }) => {
            this.sharedAccount = sharedAccount;
        });
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
    get sharedAccount() {
        return this._sharedAccount;
    }

    set sharedAccount(sharedAccount: ISharedAccount) {
        this._sharedAccount = sharedAccount;
    }
}
