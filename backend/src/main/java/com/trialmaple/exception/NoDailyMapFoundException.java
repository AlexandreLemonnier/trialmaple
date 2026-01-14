package com.trialmaple.exception;

import java.time.LocalDate;

public class NoDailyMapFoundException extends RuntimeException {
    private static final ErrorCode CODE = ErrorCode.NO_DAILY_MAP_FOUND;

    public NoDailyMapFoundException(LocalDate currentPeriodStart) {
        super("No daily map found for: " + currentPeriodStart);
    }

    public ErrorCode getCode() {
        return CODE;
    }
}