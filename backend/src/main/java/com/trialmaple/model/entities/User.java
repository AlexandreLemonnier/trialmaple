package com.trialmaple.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private Long discordId;

    private String username;

    private String avatar;

    private String discriminator;

    public User(Long discordId, String username, String avatar, String discriminator) {
        this.discordId = discordId;
        this.username = username;
        this.avatar = avatar;
        this.discriminator = discriminator;
    }
}