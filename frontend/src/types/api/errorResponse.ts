import { ErrorCode } from "./errorCode";

export type ErrorResponse = {
    timestamp: string;
    error: ErrorCode;
    message: string;
}