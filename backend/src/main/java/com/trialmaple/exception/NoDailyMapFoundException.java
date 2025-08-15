package com.trialmaple.exception;

import java.time.LocalDateTime;

public class NoDailyMapFoundException extends RuntimeException {
    private final String code = "NO_DAILY_MAP_FOUND";

    public NoDailyMapFoundException(LocalDateTime currentPeriodStart) {
        super("No daily map found for: " + currentPeriodStart);
    }

    public String getCode() {
        return code;
    }
}