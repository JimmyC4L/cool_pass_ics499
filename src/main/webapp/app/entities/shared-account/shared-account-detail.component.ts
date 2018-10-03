import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISharedAccount } from 'app/shared/model/shared-account.model';

@Component({
    selector: 'jhi-shared-account-detail',
    templateUrl: './shared-account-detail.component.html'
})
export class SharedAccountDetailComponent implements OnInit {
    sharedAccount: ISharedAccount;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ sharedAccount }) => {
            this.sharedAccount = sharedAccount;
        });
    }

    previousState() {
        window.history.back();
    }
}
