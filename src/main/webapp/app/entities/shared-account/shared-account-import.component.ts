import { Component, OnInit } from '@angular/core';

import { IEnvironment } from 'app/shared/model/environment.model';
import { FileUploader } from 'ng2-file-upload';
import { SERVER_API_URL } from 'app/app.constants';
import { JhiAlertService } from 'ng-jhipster';

@Component({
    selector: 'jhi-shared-account-import',
    templateUrl: './shared-account-import.component.html'
})
export class SharedAccountImportComponent implements OnInit {
    isSaving: boolean;

    private resourceUrl = SERVER_API_URL + 'api/upload-file';
    environments: IEnvironment[];

    public uploader: FileUploader = new FileUploader({ url: this.resourceUrl, itemAlias: 'file' });

    constructor(private jhiAlertService: JhiAlertService) {}

    ngOnInit() {
        this.uploader.onAfterAddingFile = file => {
            file.withCredentials = false;
        };
        this.uploader.onCompleteItem = (item: any, response: any, status: any, headers: any) => {
            if (status === 417) {
                this.jhiAlertService.error('The shared account id already exist in the database!');
            } else {
                this.jhiAlertService.success('uploaded success!');
            }
        };
    }

    previousState() {
        window.history.back();
    }
}
