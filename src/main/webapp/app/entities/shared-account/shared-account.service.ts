import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Rx';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISharedAccount } from 'app/shared/model/shared-account.model';
import { IEnvironment } from 'app/shared/model/environment.model';

type EntityResponseType = HttpResponse<ISharedAccount>;
type EntityArrayResponseType = HttpResponse<ISharedAccount[]>;

@Injectable({ providedIn: 'root' })
export class SharedAccountService {
    private resourceUrl = SERVER_API_URL + 'api/shared-accounts';

    constructor(private http: HttpClient) {}

    create(sharedAccount: ISharedAccount): Observable<EntityResponseType> {
        return this.http.post<ISharedAccount>(this.resourceUrl, sharedAccount, { observe: 'response' });
    }

    update(sharedAccount: ISharedAccount): Observable<EntityResponseType> {
        return this.http.put<ISharedAccount>(this.resourceUrl, sharedAccount, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ISharedAccount>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ISharedAccount[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    findAll(): Observable<EntityArrayResponseType> {
        return this.http.get<IEnvironment[]>(this.resourceUrl + '-no-page', { observe: 'response' });
    }

    findAllByEnvironment(environmentId: number): Observable<EntityArrayResponseType> {
        return this.http.get<ISharedAccount[]>(this.resourceUrl + '/get-all-by-env-id/' + environmentId, { observe: 'response' });
    }

    findAllByLogin(login: string): Observable<EntityArrayResponseType> {
        return this.http.get<ISharedAccount[]>(this.resourceUrl + '/get-all-by-login/' + login, { observe: 'response' });
    }

    findAllByEnvironmentAndByLogin (login: string, environmentId: number): Observable<EntityArrayResponseType>{
        return this.http.get<ISharedAccount[]>(this.resourceUrl + '/get-all-by-login-and-id/' + login + '/' + environmentId, { observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
