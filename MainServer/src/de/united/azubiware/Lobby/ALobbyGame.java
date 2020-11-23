package de.united.azubiware.Lobby;

import de.united.azubiware.ILobbyGame;
import de.united.azubiware.User.IUserConnection;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public abstract class ALobbyGame implements ILobbyGame {

    private final List<IUserConnection> queue;

    protected ALobbyGame() {
        queue = new LinkedList<>();
    }

    private void tryMatchMaking(){
        if(isUserCounterValid(getQueueCount())){
            synchronized (queue){
                startMatch(queue.get(0), queue.get(1));
            }
        }
    }

    @Override
    public void removeFromQueue(IUserConnection... connections) {
        synchronized (queue){
            queue.removeAll(Arrays.asList(connections));
        }
    }

    @Override
    public void addToQueue(IUserConnection connection) {
        synchronized (queue){
            for(IUserConnection c : queue){
                if(c.getId().equals(connection.getId())){
                    System.out.println("User Queued double? " + connection.getName());
                    return;
                }
            }
            queue.add(connection);
        }
        tryMatchMaking();
    }

    @Override
    public int getQueueCount() {
        return queue.size();
    }

}
