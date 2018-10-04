export interface IEnvironment {
    id?: number;
    server?: string;
    name?: string;
}

export class Environment implements IEnvironment {
    constructor(public id?: number, public server?: string, public name?: string) {}
}
