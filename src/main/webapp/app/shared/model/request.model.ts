export interface IRequest {
    id?: number;
    description?: string;
    requesterId?: number;
    authorityName?: string;
    status?: string;
}

export class Request implements IRequest {
    constructor(
        public id?: number,
        public description?: string,
        public requesterId?: number,
        public authorityName?: string,
        public status?: string
    ) {}
}
