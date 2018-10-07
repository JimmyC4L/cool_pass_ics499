import { ISharedAccount } from 'app/shared/model//shared-account.model';

export interface IEnvironment {
    id?: number;
    server?: string;
    name?: string;
    authority_name?: string;
    sharedAccounts?: ISharedAccount[];
}

export class Environment implements IEnvironment {
    constructor(
        public id?: number,
        public server?: string,
        public name?: string,
        public authority_name?: string,
        public sharedAccounts?: ISharedAccount[]
    ) {}
}
