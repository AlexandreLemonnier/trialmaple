export class RequestError extends Error {
    public errorCode: string;
    public statusCode: number;

    public constructor(errorCode: string, statusCode: number) {
        super();
        Object.setPrototypeOf(this, RequestError.prototype);
        this.errorCode = errorCode;
        this.statusCode = statusCode;
    }
}