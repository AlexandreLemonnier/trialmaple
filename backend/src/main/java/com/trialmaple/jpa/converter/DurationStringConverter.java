package com.trialmaple.jpa.converter;

import java.time.Duration;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class DurationStringConverter implements AttributeConverter<Duration, String> {

    @Override
    public String convertToDatabaseColumn(Duration duration) {
        return duration != null ? duration.toString() : null;
    }

    @Override
    public Duration convertToEntityAttribute(String dbData) {
        return dbData != null ? Duration.parse(dbData) : null;
    }
}
