import { ErrorCode } from "#/types/api/errorCode";

export type ErrorResponse = {
    timestamp: string;
    error: ErrorCode;
    message: string;
}