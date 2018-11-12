import { Component, OnInit } from '@angular/core';

import { IEnvironment } from 'app/shared/model/environment.model';
import { FileUploader } from 'ng2-file-upload';
import { SERVER_API_URL } from 'app/app.constants';

@Component({
    selector: 'jhi-shared-account-import',
    templateUrl: './shared-account-import.component.html'
})
export class SharedAccountImportComponent implements OnInit {
    isSaving: boolean;

    private resourceUrl = SERVER_API_URL + 'api/upload-file';
    environments: IEnvironment[];

    public uploader: FileUploader = new FileUploader({ url: this.resourceUrl, itemAlias: 'file' });

    constructor() {}

    ngOnInit() {
        this.uploader.onAfterAddingFile = file => {
            file.withCredentials = false;
        };
        this.uploader.onCompleteItem = (item: any, response: any, status: any, headers: any) => {
            console.log('ImageUpload:uploaded:', item, status, response);
            alert('File uploaded successfully');
        };
    }

    previousState() {
        window.history.back();
    }
}
