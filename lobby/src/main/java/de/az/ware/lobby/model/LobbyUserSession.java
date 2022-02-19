package de.az.ware.lobby.model;

import de.az.ware.common.model.LobbyUser;
import de.az.ware.common.model.MatchType;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class LobbyUserSession {

    private LobbyUser user;

    private MatchType currentQueue;
    private long lastQueuePacket;

    public MatchType getCurrentQueue() {
        return currentQueue;
    }

    public void setCurrentQueue(MatchType currentQueue) {
        this.currentQueue = currentQueue;
        lastQueuePacket = System.currentTimeMillis();
    }

    public long getLastQueuePacket() {
        return lastQueuePacket;
    }

    public LobbyUser getUser() {
        return user;
    }

    public void setUser(LobbyUser user) {
        this.user = user;
    }

}
