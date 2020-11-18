package de.united.azubiware.Matches.TTT;

public class TicTacToe {

    private int height = 3;
    private int width = 3;

    private int[][] field = new int[height][width];
    private boolean player = false;

    private int lastPlayer = 0;

    public void draw() {
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                System.out.print(field[j][i]);
            }
            System.out.println(" ");
        }
    }

    public int checkPlayerWin() {
        int win = 0;

        for (int winCondition = 1; winCondition <= 2; winCondition++) {

            // vertical
            for (int i = 0; i < height; i++) {
                if (field[0][i] == winCondition && field[1][i] == winCondition && field[2][i] == winCondition) {
                    win = winCondition;
                }
            }
            // horizontal
            for (int i = 0; i < width; i++) {
                if (field[i][0] == winCondition && field[i][1] == winCondition && field[i][2] == winCondition) {
                    win = winCondition;
                }
            }
            // diagonal
            if (field[0][0] == winCondition && field[1][1] == winCondition && field[2][2] == winCondition) {
                win = winCondition;
            }
            if (field[0][2] == winCondition && field[1][1] == winCondition && field[2][0] == winCondition) {
                win = winCondition;
            }

        }
        if (win > 0) {
            System.out.println("Player "+ win +" won!");
        }

        return win;
    }

    public void setField(int player, int x, int y){
        if(player != 1 && player != 2) throw new RuntimeException("Bad Player");
        if(x < 0 || x >= 3) throw  new RuntimeException("Bad X");
        if(y < 0 || y >= 3) throw  new RuntimeException("Bad Y");
        if(field[y][x] != 0) throw new RuntimeException("Field already taken!");
        if(lastPlayer == player) throw new RuntimeException("Player have to alternate");

        field[y][x] = player;
    }

    public TicTacToe() {
        draw();
        checkPlayerWin();
    }
}