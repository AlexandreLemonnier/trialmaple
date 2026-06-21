package com.trialmaple.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class DailyPictures {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String mapName;

    @ElementCollection
    @OrderColumn(name = "picture_order")
    private List<String> picturesName;

    public DailyPictures(String mapName, List<String> picturesName) {
        this.mapName = mapName;
        this.picturesName = picturesName;
    }
}
