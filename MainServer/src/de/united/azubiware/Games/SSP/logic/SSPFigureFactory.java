package de.united.azubiware.Games.SSP.logic;

import java.util.Collections;
import java.util.HashMap;

public class SSPFigureFactory {

    private HashMap<SSPFigureType, SSPFigure> figures;
    private HashMap<Integer, SSPFigure> figureID;

    public SSPFigureFactory(){
        figures = new HashMap<>();
        figureID = new HashMap<>();
        initFigures();
    }

    private void initFigures(){
        SSPFigure scissors = new SSPFigure(SSPFigureType.SCISSORS, Collections.singletonList(SSPFigureType.PAPER), Collections.singletonList(SSPFigureType.ROCK));
        figures.put(SSPFigureType.SCISSORS, scissors);
        figureID.put(SSPFigureType.SCISSORS.ordinal(), scissors);

        SSPFigure rock = new SSPFigure(SSPFigureType.ROCK, Collections.singletonList(SSPFigureType.SCISSORS), Collections.singletonList(SSPFigureType.PAPER));
        figures.put(SSPFigureType.ROCK, rock);
        figureID.put(SSPFigureType.ROCK.ordinal(), rock);

        SSPFigure paper = new SSPFigure(SSPFigureType.PAPER, Collections.singletonList(SSPFigureType.ROCK), Collections.singletonList(SSPFigureType.SCISSORS));
        figures.put(SSPFigureType.PAPER, paper);
        figureID.put(SSPFigureType.PAPER.ordinal(), paper);
    }

    public SSPFigure getFigure(SSPFigureType type){
        return figures.get(type);
    }

    public SSPFigure getFigureByID(int ID){
        return figureID.getOrDefault(ID, getFigure(SSPFigureType.SCISSORS));
    }
}
