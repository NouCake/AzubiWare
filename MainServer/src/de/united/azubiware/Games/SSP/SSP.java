package de.united.azubiware.Games.SSP;

import de.united.azubiware.Games.TTT.TicTacToe;

import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class SSP {

    private HashMap<Integer, Integer> playerPoints;
    private HashMap<Integer, SSPFigure> playerFigure;

    public SSP(){
        playerPoints = new HashMap<>();
        playerFigure = new HashMap<>();
    }

    public int getRoundResult(){
        int result = 0; // => DRAW

        SSPFigure playerOne = playerFigure.get(1);
        SSPFigure playerTwo = playerFigure.get(2);

        if(playerOne != playerTwo){
            if(playerOne.win.contains(playerTwo)){
                result = 1;
            }
            if(playerTwo.win.contains(playerOne)) {
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

    public void setPick(int player, SSPFigure sspFigure) throws IllegalPickException {
        if(player != 1 && player != 2) throw new IllegalPickException("Bad Player");
        playerFigure.put(player, sspFigure);
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
