package de.az.ware.lobby.controller;

import de.az.ware.common.model.MatchType;
import de.az.ware.common.packets.LobbyQueue;
import de.az.ware.common.packets.LobbyQueuePoll;
import de.az.ware.lobby.model.LobbyUserSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/lobby")
public class LobbyController {

    private final static int MAX_QUEUE_TIME = 5000; //in millis

    @Autowired private LobbyUserSession session;

    private final Map<MatchType, MatchQueue> queues;
    private final Map<MatchType, Integer> inQueue;

    public LobbyController() {
        queues = new HashMap<>();
        inQueue = new HashMap<>();
        Arrays.stream(MatchType.values()).forEach(type -> queues.put(type, new MatchQueue(type)));
    }

    @GetMapping("/poll")
    public LobbyQueuePoll.Response poll(){
        return new LobbyQueuePoll.Response(inQueue);
    }

    @PostMapping("/queue")
    public void queue(@RequestBody @Valid LobbyQueue.Request request) {
        if(request.queue == session.getCurrentQueue()) return;

        if(session.getCurrentQueue() != null) {
            MatchQueue queue = queues.get(session.getCurrentQueue());
            synchronized (queue) {
                queue.dequeue(session);
                inQueue.put(queue.getType(), queue.getInQueue());
            }
        }

        session.setCurrentQueue(request.queue);
        MatchQueue queue = queues.get(request.queue);
        synchronized (queue) {
            queue.enqueue(session, request.queue);
            inQueue.put(queue.getType(), queue.getInQueue());
        }
    }

    private void check(){
        queues.values().stream().parallel().forEach(q -> {
            var users = q.getUsers();
            synchronized (users){
                users.stream().filter(this::checkUserQueuedTooLong).forEach(u -> {
                    users.remove(u);
                    synchronized (u) {
                        u.setCurrentQueue(null);
                    }
                });
            }
        });
    }

    private boolean checkUserQueuedTooLong(LobbyUserSession u) {
        return System.currentTimeMillis() - u.getLastQueuePacket() > MAX_QUEUE_TIME;
    }

}
