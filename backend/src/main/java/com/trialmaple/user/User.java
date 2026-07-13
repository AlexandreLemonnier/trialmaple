package com.trialmaple.user;

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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(20) default 'USER'")
    private UserType userType = UserType.USER;

    public User(Long discordId, String username, String globalName, String avatar, String discriminator) {
        this.discordId = discordId;
        this.username = username;
        this.globalName = globalName;
        this.avatar = avatar;
        this.discriminator = discriminator;
    }
}
