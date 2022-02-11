package match;

import com.google.gson.Gson;
import connection.Connection;
import connection.packet.Packet;

import java.util.Arrays;

public abstract class Match {

    private final static Gson gson = new Gson();
    protected final MatchPlayer[] players;

    private final MatchConnectionAdapter adapter;
    private MatchListener listener;

    public Match(MatchConnectionAdapter adapter, MatchPlayer[] players){
        this.players = players;
        this.adapter = adapter;

        adapter.setListener(new MatchPacketHandlerAdapter(getPacketHandler()));
    }

    public void onPlayerDisconnect(MatchPlayer player){
        var p = Arrays.stream(players).filter(pl -> pl != player).findAny();
        onMatchOver(p.orElse(null));
    }

    protected void send(MatchPlayer player, Packet packet){
        adapter.send(player, gson.toJson(packet));
    }

    protected void sendAll(Packet packet){
        for(MatchPlayer player : players) send(player, packet);
    }

    protected MatchPlayer getPlayerByIndex(int index){
        if(index < 0 || index >= players.length) System.err.println("Bad Index");
        return players[index];
    }

    protected void onMatchOver(MatchPlayer winner){
        if(listener != null) listener.onMatchOver(this);
    }

    public MatchPacketHandler getPacketHandler(){
        if(this instanceof MatchPacketHandler) {
            return (MatchPacketHandler) this;
        }
        return null;
    }

    public void setListener(MatchListener listener) {
        this.listener = listener;
    }

}
