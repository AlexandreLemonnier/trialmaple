package com.trialmaple.core.exception;

public class BackofficeAccessDeniedException extends RuntimeException {
    private static final ErrorCode CODE = ErrorCode.BACKOFFICE_ACCESS_DENIED;

    public BackofficeAccessDeniedException() {
        super("Backoffice access requires an administrator account.");
    }

    public ErrorCode getCode() {
        return CODE;
    }
}
