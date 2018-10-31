import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Rx';

import { SERVER_API_URL } from 'app/app.constants';
import { map } from 'rxjs/operators';

@Injectable({ providedIn: 'root' })
export class UploadFileService {
    private resourceUrl = SERVER_API_URL + 'api/upload-file';

    constructor(private http: HttpClient) {}

    postFile(fileToUpload: File): Observable<boolean> {
        // This endpoint needs to be defined as a rest api that can save a file to disk
        // const endpoint = this.resourceUrl;
        // const formData: FormData = new FormData();
        // formData.append('fileKey', fileToUpload, fileToUpload.name);
        return this.http.post(this.resourceUrl, fileToUpload, { observe: 'response' }).pipe(map(() => true));
    }
}
