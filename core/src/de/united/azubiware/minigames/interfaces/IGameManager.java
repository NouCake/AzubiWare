package de.united.azubiware.minigames.interfaces;

import java.util.List;

public interface IGameManager {


    List<IGame> getGames();

    IGame getGameById(int id);

    IGame getGameByMatchType(int matchType);

    boolean isValidMatchType(int matchType);

}
