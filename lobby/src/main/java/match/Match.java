package match;

import connection.packet.Packet;

public abstract class Match {

    protected final MatchPlayer[] players;

    public Match(MatchPlayer[] players){
        this.players = players;
    }

    protected void send(MatchPlayer player, Packet packet){

    }

    protected void sendAll(Packet packet){

    }

    protected MatchPlayer getPlayerByIndex(int index){
        if(index < 0 || index >= players.length) System.err.println("Bad Index");
        return players[index];
    }

    protected void onMatchOver(MatchPlayer winner){

    }

    public abstract MatchPacketHandler getPacketHandler();

}
