import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ISharedAccount } from 'app/shared/model/shared-account.model';
import { IEnvironment } from 'app/shared/model/environment.model';
import { EnvironmentService } from 'app/entities/environment';
import { SharedAccountService } from './shared-account.service';
import { UploadFileService } from './upload-file.service';

@Component({
    selector: 'jhi-shared-account-import',
    templateUrl: './shared-account-import.component.html'
})
export class SharedAccountImportComponent implements OnInit {
    private _sharedAccount: ISharedAccount;
    isSaving: boolean;

    environments: IEnvironment[];
    fileToUpload: File = null;

    constructor(private uploadFileService: UploadFileService) {}

    ngOnInit() {
        // this.isSaving = false;
        // this.activatedRoute.data.subscribe(({ sharedAccount }) => {
        //     this.sharedAccount = sharedAccount;
        // });
        // this.environmentService.query().subscribe(
        //     (res: HttpResponse<IEnvironment[]>) => {
        //         this.environments = res.body;
        //     },
        //     (res: HttpErrorResponse) => this.onError(res.message)
        // );
    }
    handleFileInput(files: FileList) {
        this.fileToUpload = files.item(0);
    }
    // uploadFileToActivity() {
    //     this.fileUploadService.postFile(this.fileToUpload).subscribe(data => {
    //         // do something, if upload success
    //     }, error => {
    //         console.log(error);
    //     });
    // }

    previousState() {
        window.history.back();
    }

    save() {
        this.uploadFileService.postFile(this.fileToUpload).subscribe(
            data => {
                console.log('Successful upload');
            },
            error => {
                console.log(error);
            }
        );
        // this.isSaving = true;
        // if (this.sharedAccount.id !== undefined) {
        //     this.subscribeToSaveResponse(this.sharedAccountService.update(this.sharedAccount));
        // } else {
        //     this.subscribeToSaveResponse(this.sharedAccountService.create(this.sharedAccount));
        // }
    }

    // private subscribeToSaveResponse(result: Observable<HttpResponse<ISharedAccount>>) {
    //     result.subscribe((res: HttpResponse<ISharedAccount>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    // }
    //
    // private onSaveSuccess() {
    //     this.isSaving = false;
    //     this.previousState();
    // }
    //
    // private onSaveError() {
    //     this.isSaving = false;
    // }
    //
    // private onError(errorMessage: string) {
    //     this.jhiAlertService.error(errorMessage, null, null);
    // }
}
