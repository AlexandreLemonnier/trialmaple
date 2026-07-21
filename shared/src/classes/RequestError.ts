export class RequestError extends Error {
    public errorMessage: string;
    public statusCode: number;

    public constructor(message: string, statusCode: number) {
        super();
        Object.setPrototypeOf(this, RequestError.prototype);
        this.errorMessage = message;
        this.statusCode = statusCode;
    }
}
