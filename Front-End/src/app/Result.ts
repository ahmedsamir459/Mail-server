export class result{
    public error: boolean;
    public message: string;
    constructor(error: boolean, message : string){
        this.error = error;
        this.message = message;}
}