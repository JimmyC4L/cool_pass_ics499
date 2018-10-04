import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISharedAccount } from 'app/shared/model/shared-account.model';
import { SharedAccountService } from './shared-account.service';

@Component({
    selector: 'jhi-shared-account-delete-dialog',
    templateUrl: './shared-account-delete-dialog.component.html'
})
export class SharedAccountDeleteDialogComponent {
    sharedAccount: ISharedAccount;

    constructor(
        private sharedAccountService: SharedAccountService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.sharedAccountService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'sharedAccountListModification',
                content: 'Deleted an sharedAccount'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-shared-account-delete-popup',
    template: ''
})
export class SharedAccountDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ sharedAccount }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(SharedAccountDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.sharedAccount = sharedAccount;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
