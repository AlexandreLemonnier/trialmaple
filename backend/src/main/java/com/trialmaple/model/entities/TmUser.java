package com.trialmaple.model.entities;

import com.trialmaple.model.enums.TmGame;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class TmUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String login;

    private String displayName;

    @Enumerated(EnumType.STRING)
    private TmGame game;

    public TmUser(String login, String displayName, TmGame game) {
        this.login = login;
        this.displayName = displayName;
        this.game = game;
    }
}
