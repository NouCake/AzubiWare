package de.united.azubiware.Games.SSP.logic;

import java.util.List;

public class SSPFigure {

    private final SSPFigureType type;

    private final List<SSPFigureType> win;
    private final List<SSPFigureType> lose;

    public SSPFigure(SSPFigureType type, List<SSPFigureType> win, List<SSPFigureType> lose){
        this.type = type;

        this.win = win;
        this.lose = lose;
    }

    public SSPFigureType getType() {
        return type;
    }

    public List<SSPFigureType> getLose() {
        return lose;
    }

    public List<SSPFigureType> getWin() {
        return win;
    }

}
