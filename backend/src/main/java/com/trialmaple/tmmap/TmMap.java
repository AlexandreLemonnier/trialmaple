package com.trialmaple.tmmap;

import java.time.Duration;
import java.util.List;
import java.util.UUID;

import com.trialmaple.core.utils.DurationMillisConverter;

import com.trialmaple.tmmap.tmuser.TmUser;
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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class TmMap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uuid", unique = true, nullable = false, updatable = false)
    private final UUID uuid = UUID.randomUUID();

    private Long tmxId;

    private boolean active;

    private String name;

    private String displayName;

    @ElementCollection
    @CollectionTable(name = "tm_map_authors", joinColumns = @JoinColumn(name = "tm_map_id"))
    @Column(name = "author")
    private List<String> authors;

    private int checkpointCount;

    @Enumerated(EnumType.STRING)
    private DifficultyCategory difficulty;

    private int points;

    @Convert(converter = DurationMillisConverter.class)
    private Duration wrTime;

    private Integer wrYear;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wr_holder_id")
    private TmUser wrHolder;

    private int finisherCount;

    private int releaseYear;

    @Enumerated(EnumType.STRING)
    private MapList mapList;

    private boolean classic;

    public TmMap(
        Long tmxId,
        String name,
        String displayName,
        List<String> authors, 
        int checkpointCount, 
        int points, 
        Duration wrTime, 
        Integer wrYear, 
        TmUser wrHolder, 
        int finisherCount, 
        int releaseYear,
        MapList mapList,
        boolean classic
    ) {
        this.tmxId = tmxId;
        this.name = name;
        this.displayName = displayName;
        this.authors = authors;
        this.checkpointCount = checkpointCount;
        this.points = points;
        this.wrTime = wrTime;
        this.wrYear = wrYear;
        this.wrHolder = wrHolder;
        this.finisherCount = finisherCount;
        this.releaseYear = releaseYear;
        this.mapList = mapList;
        this.classic = classic;
        this.active = true;
    }
}