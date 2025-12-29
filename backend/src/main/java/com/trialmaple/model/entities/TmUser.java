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

@Entity
@Table(name = "tm_user")
public class TmUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "BIGINT")
    private Long id;

    @Column(name = "login", columnDefinition = "VARCHAR(255)")
    private String login;

    @Column(name = "display_name", columnDefinition = "VARCHAR(255)")
    private String displayName;

    @Enumerated(EnumType.STRING)
    @Column(name = "game", columnDefinition = "VARCHAR(50)")
    private TmGame game;

    protected TmUser() {
    }

    public TmUser(String login, String displayName, TmGame game) {
        this.login = login;
        this.displayName = displayName;
        this.game = game;
    }

    public String getLogin() {
        return login;
    }

    public String getDisplayName() {
        return displayName;
    }

    public TmGame getGame() {
        return game;
    }
}
