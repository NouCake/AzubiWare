package de.united.azubiware.Games.SV;

public class Battleship {

    private static final int shipsLenOne = 4;
    private static final int shipsLenTwo = 3;
    private static final int shipsLenThree = 2;
    private static final int shipsLenFour = 1;

    private final int height = 10;
    private final int width = 10;

    private int nextPlayer = 1;

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

    /**
     * @param player the player who is shooting
     * @param x cellX
     * @param y cellY
     * @return returns true if a Ship is hit
     * @throws IllegalTurnException Throws when the wrong player is making the turn, the cell is already hit or the given cell is out of bounds.
     */
    public boolean doPlayerTurn(int player, int x, int y) throws IllegalTurnException{
        if(player != nextPlayer) throw new IllegalTurnException("Wrong Player Turn");

        if(player == 1){
            if(hitField(gridPlayer2, x, y)) {
                return true;
            } else {
                nextPlayer = 2;
                return false;
            }
        } else if(player == 2){
            if(hitField(gridPlayer1, x, y)) {
                return true;
            } else {
                nextPlayer = 1;
                return false;
            }
        }

        throw new IllegalTurnException("Invalid Player!");
    }

    private void draw() {
        System.out.println("Player1");
        gridPlayer1.draw();

        System.out.println("Player2");
        gridPlayer2.draw();
    }

    public int checkPlayerWin() {
        if(gridPlayer1.areAllShipsSunk()) return 2;
        if(gridPlayer2.areAllShipsSunk()) return 1;
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

    public int getNextPlayer() {
        return nextPlayer;
    }

    public int[][] getSetup(int player) {
        if(player == 1) return Grid.GetShipSetup(gridPlayer1);
        if(player == 2) return Grid.GetShipSetup(gridPlayer2);
        return null;
    }

    public static class IllegalTurnException extends Exception {
        public IllegalTurnException(String message){
            super(message);
        }
    }
}