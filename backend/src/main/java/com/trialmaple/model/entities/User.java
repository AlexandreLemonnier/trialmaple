package com.trialmaple.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private Long discordId;

    private String username;

    private String globalName;

    private String avatar;

    private String discriminator;

    private LocalDate creationDate;

    public User(Long discordId, String username, String globalName, String avatar, String discriminator) {
        this.discordId = discordId;
        this.username = username;
        this.globalName = globalName;
        this.avatar = avatar;
        this.discriminator = discriminator;
    }
}