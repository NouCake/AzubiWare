package de.az.ware.lobby.controller;

import de.az.ware.lobby.model.LobbyUserSession;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LobbyInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);
        if(session == null) return false;

        LobbyUserSession lobbySession = (LobbyUserSession) session.getAttribute("scopedTarget.lobbyUserSession");
        if(lobbySession == null || lobbySession.getUser() == null) {
            response.sendError(401, "please log in");
            return false;
        }

        return true;
    }
}
