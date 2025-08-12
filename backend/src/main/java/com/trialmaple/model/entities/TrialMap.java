package com.trialmaple.model.entities;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import com.trialmaple.jpa.converter.DurationStringConverter;
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

    @Column(name = "nb_checkpoints", columnDefinition = "INT")
    private int nbCheckpoints;

    @Enumerated(EnumType.STRING)
    @Column(name = "difficulty", columnDefinition = "VARCHAR(50)")
    private DifficultyCategory difficulty;

    @Column(name = "points", columnDefinition = "INT")
    private int points;

    @Column(name = "world_record", columnDefinition = "VARCHAR(50)")
    @Convert(converter = DurationStringConverter.class)
    private Duration worldRecord;

    @Column(name = "nb_finishers", columnDefinition = "INT")
    private int nbFinishers;

    @ElementCollection
    @CollectionTable(name = "trial_map_accepted_answers", joinColumns = @JoinColumn(name = "trial_map_id"))
    @Column(name = "accepted_answer", columnDefinition = "VARCHAR(255)")
    private List<String> acceptedAnswers;

    @Column(name = "active", columnDefinition = "BOOLEAN")
    private boolean active;

    protected TrialMap() {
    }

    public TrialMap(String name, List<String> authors, int nbCheckpoints, DifficultyCategory difficulty,
            int points, Duration worldRecord, int nbFinishers, boolean active) {
        this.name = name;
        this.authors = authors;
        this.nbCheckpoints = nbCheckpoints;
        this.difficulty = difficulty;
        this.points = points;
        this.worldRecord = worldRecord;
        this.nbFinishers = nbFinishers;
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

    public int getNbCheckpoints() {
        return nbCheckpoints;
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

    public int getNbFinishers() {
        return nbFinishers;
    }

    public List<String> getAcceptedAnswers() {
        return new ArrayList<>(acceptedAnswers);
    }

    public boolean isActive() {
        return active;
    }
}