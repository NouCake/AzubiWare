package de.united.azubiware.Games.SSP.logic;

import java.util.HashMap;

public class SSP {

    private HashMap<Integer, Integer> playerPoints;
    private HashMap<Integer, SSPFigure> playerFigure;

    private final SSPFigureFactory figureFactory;

    public SSP(){
        playerPoints = new HashMap<>();
        playerFigure = new HashMap<>();

        figureFactory = new SSPFigureFactory();
    }

    public int getRoundResult(){
        int result = 0; // => DRAW

        SSPFigure playerOne = playerFigure.getOrDefault(1, figureFactory.getFigure(SSPFigureType.SCISSORS));
        SSPFigure playerTwo = playerFigure.getOrDefault(1, figureFactory.getFigure(SSPFigureType.SCISSORS));

        if(playerOne != playerTwo){
            if(playerOne.getWin().contains(playerTwo)){
                result = 1;
            }
            if(playerTwo.getWin().contains(playerOne)) {
                result = 2;
            }
        }else{
            givePoint(1 | 2);
        }

        if(result != 0)
            givePoint(result);

        return 0;
    }

    public int getMatchResult(){
        int result = 0;

        int playerOnePoints = playerPoints.get(1);
        int playerTwoPoints = playerPoints.get(2);

        if(playerOnePoints != playerTwoPoints){
            if(playerOnePoints > playerTwoPoints)
                result = 1;
            if(playerTwoPoints > playerOnePoints)
                result = 2;
        }

        return result;
    }

    public void setPick(int player, SSPFigureType figureType) throws IllegalPickException {
        if(player != 1 && player != 2) throw new IllegalPickException("Bad Player");
        playerFigure.put(player, figureFactory.getFigure(figureType));
    }

    public SSPFigureType getPick(int player){
        return playerFigure.getOrDefault(player, figureFactory.getFigure(SSPFigureType.SCISSORS)).getType();
    }

    private void givePoint(int player){
        playerPoints.put(player, playerPoints.getOrDefault(player, 0) + 1);
    }

    public class IllegalPickException extends Exception {
        public IllegalPickException(String message){
            super(message);
        }
    }

}
