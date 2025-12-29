package com.trialmaple.model.dto;

import java.util.List;

public record TmMapDto(
                String name,
                List<String> authors,
                int checkpointCount,
                String difficulty,
                int points,
                int finisherCount,
                String wrTime, // formatted string,
                int releaseYear
) {
}
