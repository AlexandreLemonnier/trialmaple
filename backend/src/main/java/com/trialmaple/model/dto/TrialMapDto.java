package com.trialmaple.model.dto;

import java.util.List;

public record TrialMapDto(
                String name,
                List<String> authors,
                int nbCheckpoints,
                String difficulty,
                int points,
                int nbFinishers,
                String worldRecord // formatted string
) {
}
