package de.united.azubiware.Games.SV;

public class Battleship {

    private static final int shipsLenOne = 4;
    private static final int shipsLenTwo = 3;
    private static final int shipsLenThree = 2;
    private static final int shipsLenFour = 1;

    private final int height = 10;
    private final int width = 10;

    private int lastPlayer = 0;

    private final Grid gridPlayer1;
    private final Grid gridPlayer2;

    public Battleship() {
        gridPlayer1 = new Grid(width, height);
        gridPlayer2 = new Grid(width, height);
        setShips(gridPlayer1);
        setShips(gridPlayer2);
        
        draw();
    }

    private boolean hitField(Grid grid, int x, int y) throws IllegalTurnException{
        if(grid.isCellOutOfBounds(x, y)) throw new IllegalTurnException("Cell is out of Bounds");
        if(grid.isHit(x, y)) throw new IllegalTurnException("This field is already Hit");

        return grid.setHit(x, y);
    }
    
    public boolean hitField(int player, int x, int y) throws IllegalTurnException{
        if(player == lastPlayer) throw new IllegalTurnException("Player cant hit twice");
        lastPlayer = player;
        if(player == 1) return hitField(gridPlayer1, x, y);
        if(player == 2) return hitField(gridPlayer2, x, y);
        throw new IllegalTurnException("Invalid Player!");
    }

    private void draw() {
        System.out.println("Player1");
        gridPlayer1.draw();

        System.out.println("Player2");
        gridPlayer2.draw();
    }

    public int checkPlayerWin() {
        if(gridPlayer1.areAllShipsSunk()) return 1;
        if(gridPlayer2.areAllShipsSunk()) return 2;
        return 0;
    }

    private void setShips(Grid player) {
        for (int i = 0; i < shipsLenOne; ) {
            if (createRandomShip(player, 1)) i++;
        }
        for (int i = 0; i < shipsLenTwo; ) {
            if (createRandomShip(player, 2)) i++;
        }
        for (int i = 0; i < shipsLenThree; ) {
            if (createRandomShip(player, 3)) i++;
        }
        for (int i = 0; i < shipsLenFour; ) {
            if (createRandomShip(player, 4)) i++;
        }
    }

    private boolean createRandomShip(Grid grid, int length) {
        int randomX = (int) (Math.random() * width);
        int randomY = (int) (Math.random() * height);
        boolean randomBoolean = Math.random() >= 0.5f;
        return grid.setShip(randomX, randomY, length, randomBoolean);
    }

    /*public static void main(String[] args) {
        System.out.println("pre");
        Grid grid = new Grid(10, 10);

        grid.setShip(0, 0, 3, true);
        grid.draw();

        Battleship bs = new Battleship();
        System.out.println("post");
    }*/

    public class IllegalTurnException extends Exception {
        public IllegalTurnException(String message){
            super(message);
        }
    }
}