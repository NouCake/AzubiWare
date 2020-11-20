package de.united.azubiware.Matches.VierGewinnt;

import java.util.Scanner;

public class VierGewinnt {

    private static final int height = 6;
    private static final int width = 7;
    Scanner scanner = new Scanner(System.in);

    private static final int[][] field = new int[width][height];
    private int lastPlayer = 0;

    public VierGewinnt() {
        draw();
    }

    public boolean checkPlayerWin(int x, int y, int winCondition) {

        // horizontal
        for (int i = -3; i <= 0; i++) {
            while (x + i < 0) {
                i++;
            }
            if (x + i + 4 > width) {
                break;
            }
            if (field[x + i][y] == winCondition && field[x + i + 1][y] == winCondition && field[x + i + 2][y] == winCondition && field[x + i + 3][y] == winCondition) {
                return true;
            }

        }
        //vertical
        for (int i = -3; i <= 0; i++) {
            while (y + i < 0) {
                i++;
            }
            if (y + i + 4 > height) {
                break;
            }
            if (field[x][y + i] == winCondition && field[x][y + i + 1] == winCondition && field[x][y + i + 2] == winCondition && field[x][y + i + 3] == winCondition) {
                return true;
            }

        }
        //diagonal from top left to bottom right
        for (int i = -3; i <= 0; i++) {
            while (y + i < 0 || x + i < 0) {
                i++;
            }
            if (y + i + 4 > height || x + i + 4 > width) {
                break;
            }
            if (field[x + i][y + i] == winCondition && field[x + i + 1][y + i + 1] == winCondition && field[x + i + 2][y + i + 2] == winCondition && field[x + i + 3][y + i + 3] == winCondition) {
                return true;
            }


        }
        //diagonal form top right to bottom left (doesn't work properly)
        for (int i = -3; i <= 0; i++) {
            while (y + i < 0 || x - i + 3 < 0) {
                i++;
            }
            if (y + i + 4 > height || x - i >= width || x - i -3 < 0) {
                break;
            }
            System.out.println("true");
            if (field[x - i][y + i] == winCondition && field[x - i - 1][y + i + 1] == winCondition && field[x - i - 2][y + i + 2] == winCondition && field[x - i - 3][y + i + 3] == winCondition) {
                return true;
            }
        }
        return false;
    }

    public void addChip(int input, int player) {
        if (field[input][0] != 0) {
            System.out.println("new chip cannot be added!");
        } else {
            for (int i = height - 1; i >= 0; i--) {
                if (field[input][i] == 0) {
                    if (player==1) {
                        field[input][i] = 1;
                        if (checkPlayerWin(input, i, 1))
                            System.out.println("Player 1 won!");


                    } else if (player == 2) {
                        field[input][i] = 2;
                        checkPlayerWin(input, i, 2);
                        if (checkPlayerWin(input, i, 2))
                            System.out.println("Player 2 won!");


                    }
                    break;
                }
            }
            draw();
        }
    }

    public boolean checkForDraw() {
        int full = 0;
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
               if (field[width][height] != 0) {
                   full++;
               }
            }
        }
        if (full == 42) {
            return true;
        }
        return  false;
    }


    public void draw() {
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                if (field[i][j] == 0) {
                    System.out.print("_");
                } else if (field[i][j] == 1) {
                    System.out.print("X");
                } else {
                    System.out.print("O");
                }
            }
            System.out.println(" ");
        }

    }

    public void setField(int player, int Xinput) throws IllegalTurnException{
        if(player != 1 && player != 2) throw new IllegalTurnException("Bad Player");
        if(Xinput < 0 || Xinput >= width) throw  new IllegalTurnException("Bad X");
        if (field[Xinput][0] != 0) throw new IllegalTurnException("column already full");
        if(lastPlayer == player) throw new IllegalTurnException("Player have to alternate");

        lastPlayer = player;
        addChip(Xinput, player);
        draw();
    }

    public class IllegalTurnException extends Exception {
        public IllegalTurnException(String message){
            super(message);
        }
    }

    public int getNextPlayer(){
        return 1 + ((lastPlayer + 1) % 2);
    }

    public static void main(String[] args) {
        VierGewinnt vierGewinnt = new VierGewinnt();
        vierGewinnt.draw();
        while (true) {
            vierGewinnt.addChip(vierGewinnt.scanner.nextInt(), 1);
            vierGewinnt.addChip(vierGewinnt.scanner.nextInt(), 2);
        }
    }
}