package de.united.azubiware.Games.SSP;

import java.util.Arrays;
import java.util.List;

public enum SSPFigure {

    SCISSORS(Arrays.asList(SSPFigure.PAPER), Arrays.asList(SSPFigure.ROCK)),
    ROCK(Arrays.asList(SSPFigure.SCISSORS), Arrays.asList(SSPFigure.PAPER)),
    PAPER(Arrays.asList(SSPFigure.ROCK), Arrays.asList(SSPFigure.SCISSORS));

    List<SSPFigure> win;
    List<SSPFigure> lose;

    SSPFigure(List<SSPFigure> win, List<SSPFigure> lose){
        this.win = win;
        this.lose = lose;
    }

    public List<SSPFigure> getLose() {
        return lose;
    }

    public List<SSPFigure> getWin() {
        return win;
    }
}
