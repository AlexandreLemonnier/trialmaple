import { ErrorCode } from "#/types/api/errorCode";

export interface ErrorResponse {
    timestamp: string;
    error: ErrorCode;
    message: string;
}