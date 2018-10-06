import { IEnvironment } from 'app/shared/model//environment.model';

export interface ISharedAccount {
    id?: number;
    login?: string;
    password?: string;
    environment?: IEnvironment;
}

export class SharedAccount implements ISharedAccount {
    constructor(public id?: number, public login?: string, public password?: string, public environment?: IEnvironment) {}
}
