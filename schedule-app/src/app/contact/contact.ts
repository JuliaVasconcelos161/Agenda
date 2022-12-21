export class Contact {
    id?: number;
    name?: string;
    email?: string;
    favorite?: boolean;

    constructor( name: string, email:string) {
        this.name = name;
        this.email = email;
    }
}