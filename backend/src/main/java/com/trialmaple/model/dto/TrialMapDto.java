package com.trialmaple.model.dto;

import java.util.List;

public record TrialMapDto(
                String name,
                List<String> authors,
                int checkpointCount,
                String difficulty,
                int points,
                int finisherCount,
                String worldRecord // formatted string
) {
}
