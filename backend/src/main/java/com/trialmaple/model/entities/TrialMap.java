package com.trialmaple.model.entities;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import com.trialmaple.jpa.converter.DurationMillisConverter;
import com.trialmaple.model.enums.DifficultyCategory;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "trial_map")
public class TrialMap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "BIGINT")
    private Long id;

    @Column(name = "name", columnDefinition = "VARCHAR(255)")
    private String name;

    @ElementCollection
    @CollectionTable(name = "trial_map_authors", joinColumns = @JoinColumn(name = "trial_map_id"))
    @Column(name = "author", columnDefinition = "VARCHAR(255)")
    private List<String> authors;

    @Column(name = "checkpoint_count", columnDefinition = "INT")
    private int checkpointCount;

    @Enumerated(EnumType.STRING)
    @Column(name = "difficulty", columnDefinition = "VARCHAR(50)")
    private DifficultyCategory difficulty;

    @Column(name = "points", columnDefinition = "INT")
    private int points;

    @Column(name = "world_record", columnDefinition = "BIGINT")
    @Convert(converter = DurationMillisConverter.class)
    private Duration worldRecord;

    @Column(name = "finisher_count", columnDefinition = "INT")
    private int finisherCount;

    @Column(name = "active", columnDefinition = "BOOLEAN")
    private boolean active;

    protected TrialMap() {
    }

    public TrialMap(String name, List<String> authors, int checkpointCount, DifficultyCategory difficulty,
            int points, Duration worldRecord, int finisherCount, boolean active) {
        this.name = name;
        this.authors = authors;
        this.checkpointCount = checkpointCount;
        this.difficulty = difficulty;
        this.points = points;
        this.worldRecord = worldRecord;
        this.finisherCount = finisherCount;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<String> getAuthors() {
        return new ArrayList<>(authors);
    }

    public int getCheckpointCount() {
        return checkpointCount;
    }

    public DifficultyCategory getDifficulty() {
        return difficulty;
    }

    public int getPoints() {
        return points;
    }

    public Duration getWorldRecord() {
        return worldRecord;
    }

    public int getFinisherCount() {
        return finisherCount;
    }

    public boolean isActive() {
        return active;
    }
}