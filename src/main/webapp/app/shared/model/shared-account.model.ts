export interface ISharedAccount {
    id?: number;
    login?: string;
    password?: string;
    envID?: number;
}

export class SharedAccount implements ISharedAccount {
    constructor(public id?: number, public login?: string, public password?: string, public envID?: number) {}
}
