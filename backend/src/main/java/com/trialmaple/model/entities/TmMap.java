package com.trialmaple.model.entities;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import com.trialmaple.jpa.converter.DurationMillisConverter;
import com.trialmaple.model.enums.DifficultyCategory;
import com.trialmaple.model.enums.MapList;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tm_map")
public class TmMap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "BIGINT")
    private Long id;

    @Column(name = "active", columnDefinition = "BOOLEAN")
    private boolean active;

    @Column(name = "name", columnDefinition = "VARCHAR(255)")
    private String name;

    @ElementCollection
    @CollectionTable(name = "tm_map_authors", joinColumns = @JoinColumn(name = "tm_map_id"))
    @Column(name = "author", columnDefinition = "VARCHAR(255)")
    private List<String> authors;

    @Column(name = "checkpoint_count", columnDefinition = "INT")
    private int checkpointCount;

    @Enumerated(EnumType.STRING)
    @Column(name = "difficulty", columnDefinition = "VARCHAR(50)")
    private DifficultyCategory difficulty;

    @Column(name = "points", columnDefinition = "INT")
    private int points;

    @Column(name = "wr_time", columnDefinition = "BIGINT")
    @Convert(converter = DurationMillisConverter.class)
    private Duration wrTime;

    @Column(name = "wr_year", columnDefinition = "INT")
    private Integer wrYear;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "wr_holder_id")
    private TmUser wrHolder;

    @Column(name = "finisher_count", columnDefinition = "INT")
    private int finisherCount;

    @Column(name = "release_year", columnDefinition = "INT")
    private int releaseYear;

    @Enumerated(EnumType.STRING)
    @Column(name = "map_list", columnDefinition = "VARCHAR(50)")
    private MapList mapList;

    protected TmMap() {
    }

    // TODO Remove if useless?
    public TmMap(String name, List<String> authors, int checkpointCount, DifficultyCategory difficulty,
            int points, Duration wrTime, int finisherCount, boolean active, int releaseYear) {
        this.name = name;
        this.authors = authors;
        this.checkpointCount = checkpointCount;
        this.difficulty = difficulty;
        this.points = points;
        this.wrTime = wrTime;
        this.finisherCount = finisherCount;
        this.active = active;
        this.releaseYear = releaseYear;
    }

    public TmMap(
        String name, 
        List<String> authors, 
        int checkpointCount, 
        int points, 
        Duration wrTime, 
        Integer wrYear, 
        TmUser wrHolder, 
        int finisherCount, 
        int releaseYear,
        MapList mapList
    ) {
        this.name = name;
        this.authors = authors;
        this.checkpointCount = checkpointCount;
        this.points = points;
        this.wrTime = wrTime;
        this.wrYear = wrYear;
        this.wrHolder = wrHolder;
        this.finisherCount = finisherCount;
        this.releaseYear = releaseYear;
        this.mapList = mapList;
        this.active = true;
    }

    /** GETTERS */

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

    public Duration getWrTime() {
        return wrTime;
    }

    public Integer getWrYear() {
        return wrYear;
    }

    public TmUser getWrHolder() {
        return wrHolder;
    }

    public int getFinisherCount() {
        return finisherCount;
    }

    public boolean isActive() {
        return active;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public MapList getMapList() {
        return mapList;
    }

    /** SETTERS */

    public void setCheckpointCount(int checkpointCount) {
        this.checkpointCount = checkpointCount;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void setWrTime(Duration wrTime) {
        this.wrTime = wrTime;
    }

    public void setWrYear(Integer wrYear) {
        this.wrYear = wrYear;
    }

    public void setWrHolder(TmUser wrHolder) {
        this.wrHolder = wrHolder;
    }

    public void setFinisherCount(int finisherCount) {
        this.finisherCount = finisherCount;
    }
}