package de.az.ware.lobby.controller;

import de.az.ware.lobby.model.LobbySession;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LobbyInterceptor implements HandlerInterceptor {

    public static final String SESSION_ATTRIBUTE_NAME = "scopedTarget.lobbySession";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);
        if(session == null) return false;

        LobbySession lobbySession = (LobbySession) session.getAttribute(SESSION_ATTRIBUTE_NAME);
        if(lobbySession == null || lobbySession.getUser() == null) {
            response.sendError(401, "please log in");
            return false;
        }

        return true;
    }
}
