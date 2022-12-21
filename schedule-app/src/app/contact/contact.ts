export class Contact {
    id?: number;
    name?: string;
    email?: string;
    favorite?: boolean;
    picture?: any;

    constructor( name: string, email:string) {
        this.name = name;
        this.email = email;
    }
}