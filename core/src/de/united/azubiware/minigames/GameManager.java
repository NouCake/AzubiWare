package de.united.azubiware.minigames;

import de.united.azubiware.Games.Pong.PongMatch;
import de.united.azubiware.Games.SSP.SSPMatch;
import de.united.azubiware.Games.TTT.TTTMatch;
import de.united.azubiware.Games.VG.VGMatch;
import de.united.azubiware.minigames.interfaces.IGame;
import de.united.azubiware.minigames.interfaces.IGameManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class GameManager implements IGameManager {

    private HashMap<Integer, IGame> miniGamesById;
    private HashMap<Integer, IGame> miniGamesByMatchType;

    private List<Integer> matchTypes = Arrays.asList(TTTMatch.MATCH_TYPE, VGMatch.MATCH_TYPE, PongMatch.MATCH_TYPE, SSPMatch.MATCH_TYPE);

    public GameManager(){
        miniGamesById = new HashMap<>();
        miniGamesByMatchType = new HashMap<>();

        initGames();
    }

    private void initGames(){
        TicTacToe ticTacToe = new TicTacToe();
        miniGamesById.put(0, ticTacToe);
        miniGamesByMatchType.put(ticTacToe.getMatchType(), ticTacToe);

        FourWins fourWins = new FourWins();
        miniGamesById.put(1, fourWins);
        miniGamesByMatchType.put(fourWins.getMatchType(), fourWins);

        SSP ssp = new SSP();
        miniGamesById.put(-1, ssp);
        miniGamesByMatchType.put(0, ssp);

        Pong pong = new Pong();
        miniGamesById.put(2, pong);
        miniGamesByMatchType.put(pong.getMatchType(), pong);
    }

    @Override
    public List<IGame> getGames() {
        return new ArrayList<>(miniGamesByMatchType.values());
    }

    @Override
    public IGame getGameById(int id) {
        return miniGamesById.getOrDefault(id, null);
    }

    @Override
    public IGame getGameByMatchType(int matchType) {
        return miniGamesByMatchType.getOrDefault(matchType, null);
    }

    @Override
    public boolean isValidMatchType(int matchType) {
        return matchTypes.contains(matchType);
    }
}
