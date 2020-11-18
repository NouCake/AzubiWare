public class TicTacToe {

    private static int height = 3;
    private static int width = 3;

    private static int[][] field = new int[height][width];
    private static boolean player = false;


    public static void draw() {
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                System.out.print(field[j][i]);
            }
            System.out.println(" ");
        }
    }

    public static void checkPlayerWin() {
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

    }


    public static void main(String[] args) {

        field[0][0] = 1;    field[0][1] = 1;    field[0][2] = 2;
        field[1][0] = 0;    field[1][1] = 1;    field[1][2] = 2;
        field[2][0] = 1;    field[2][1] = 0;    field[2][2] = 2;

        draw();
        checkPlayerWin();

    }
}