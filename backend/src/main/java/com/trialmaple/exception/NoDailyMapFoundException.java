package com.trialmaple.exception;

import java.time.LocalDate;

public class NoDailyMapFoundException extends RuntimeException {
    private final String code = "NO_DAILY_MAP_FOUND";

    public NoDailyMapFoundException(LocalDate currentPeriodStart) {
        super("No daily map found for: " + currentPeriodStart);
    }

    public String getCode() {
        return code;
    }
}