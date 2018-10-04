import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IRequest } from 'app/shared/model/request.model';
import { RequestService } from './request.service';

@Component({
    selector: 'jhi-request-update',
    templateUrl: './request-update.component.html'
})
export class RequestUpdateComponent implements OnInit {
    private _request: IRequest;
    isSaving: boolean;

    constructor(private requestService: RequestService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ request }) => {
            this.request = request;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.request.id !== undefined) {
            this.subscribeToSaveResponse(this.requestService.update(this.request));
        } else {
            this.subscribeToSaveResponse(this.requestService.create(this.request));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IRequest>>) {
        result.subscribe((res: HttpResponse<IRequest>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get request() {
        return this._request;
    }

    set request(request: IRequest) {
        this._request = request;
    }
}
