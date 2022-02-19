package de.az.ware.lobby.controller;

import de.az.ware.common.model.LobbyUser;
import de.az.ware.common.model.MatchType;
import de.az.ware.lobby.model.LobbyUserSession;

import java.util.HashSet;
import java.util.Set;

public class MatchQueue {

    private final MatchType type;
    private final Set<LobbyUserSession> users;

    public MatchQueue(MatchType type) {
        this.type = type;
        this.users = new HashSet<>();
    }

    public MatchType getType() {
        return type;
    }

    public Set<LobbyUserSession> getUsers() {
        return users;
    }

    public int getInQueue() {
        return users.size();
    }

    public void enqueue(LobbyUserSession user, MatchType type) {

    }

    public void dequeue(LobbyUserSession user) {

    }

}
