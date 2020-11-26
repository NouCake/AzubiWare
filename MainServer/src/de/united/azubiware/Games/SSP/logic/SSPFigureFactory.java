package de.united.azubiware.Games.SSP.logic;

import java.util.Collections;
import java.util.HashMap;

public class SSPFigureFactory {

    private HashMap<SSPFigureType, SSPFigure> figures;

    public SSPFigureFactory(){
        figures = new HashMap<>();
        initFigures();
    }

    private void initFigures(){
        SSPFigure scissors = new SSPFigure(SSPFigureType.SCISSORS, Collections.singletonList(SSPFigureType.PAPER), Collections.singletonList(SSPFigureType.ROCK));
        figures.put(SSPFigureType.SCISSORS, scissors);

        SSPFigure rock = new SSPFigure(SSPFigureType.ROCK, Collections.singletonList(SSPFigureType.SCISSORS), Collections.singletonList(SSPFigureType.PAPER));
        figures.put(SSPFigureType.ROCK, rock);

        SSPFigure paper = new SSPFigure(SSPFigureType.PAPER, Collections.singletonList(SSPFigureType.ROCK), Collections.singletonList(SSPFigureType.SCISSORS));
        figures.put(SSPFigureType.PAPER, scissors);
    }

    public SSPFigure getFigure(SSPFigureType type){
        return figures.get(type);
    }
}
