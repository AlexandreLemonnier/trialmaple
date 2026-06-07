package com.trialmaple.model.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TmCategory {
    TRIAL("Trial"),
    RPG("RPG");

    private final String label;

    TmCategory(String label) {
        this.label = label;
    }

    @JsonValue
    public String getLabel() {
        return label;
    }
}