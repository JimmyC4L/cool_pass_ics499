import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IEnvironment } from 'app/shared/model/environment.model';
import { EnvironmentService } from './environment.service';

@Component({
    selector: 'jhi-environment-update',
    templateUrl: './environment-update.component.html'
})
export class EnvironmentUpdateComponent implements OnInit {
    private _environment: IEnvironment;
    isSaving: boolean;

    constructor(private environmentService: EnvironmentService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ environment }) => {
            this.environment = environment;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.environment.id !== undefined) {
            this.subscribeToSaveResponse(this.environmentService.update(this.environment));
        } else {
            this.subscribeToSaveResponse(this.environmentService.create(this.environment));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IEnvironment>>) {
        result.subscribe((res: HttpResponse<IEnvironment>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get environment() {
        return this._environment;
    }

    set environment(environment: IEnvironment) {
        this._environment = environment;
    }
}
