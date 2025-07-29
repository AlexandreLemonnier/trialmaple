package com.trialmaple.model.dto;

import java.util.ArrayList;
import java.util.List;

public class TrialMapDto {
    private String name;
    private List<String> authors;
    private int nbCheckpoints;
    private String difficulty;
    private int points;
    private int nbFinishers;
    private List<String> acceptedAnswers;
    private String worldRecord; // formatted string

    // Constructor
    public TrialMapDto(String name, List<String> authors, int nbCheckpoints, String difficulty, int points,
            int nbFinishers, List<String> acceptedAnswers, String worldRecord) {
        this.name = name;
        this.authors = authors;
        this.nbCheckpoints = nbCheckpoints;
        this.difficulty = difficulty;
        this.points = points;
        this.nbFinishers = nbFinishers;
        this.acceptedAnswers = acceptedAnswers;
        this.worldRecord = worldRecord;
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

    public String getDifficulty() {
        return difficulty;
    }

    public int getPoints() {
        return points;
    }

    public String getWorldRecord() {
        return worldRecord;
    }

    public int getNbFinishers() {
        return nbFinishers;
    }

    public List<String> getAcceptedAnswers() {
        return new ArrayList<>(acceptedAnswers);
    }
}
