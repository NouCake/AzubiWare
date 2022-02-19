package de.az.ware.common.model;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Entity;

import org.hibernate.annotations.Type;

@Entity
public class LobbyUser {

    private UUID id;
    private String username;
    private String googleId;

    public LobbyUser() {
    }

    public LobbyUser(UUID id, String username, String googleId) {
        this.id = id;
        this.username = username;
        this.googleId = googleId;
    }

    @Id
    @Type(type = "org.hibernate.type.UUIDCharType")
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Column(unique = true)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(unique = true)
    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

}
