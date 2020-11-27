package de.united.azubiware.Games.SV;

public class Battleship {

    private final int shipsLenOne = 4;
    private final int shipsLenTwo = 3;
    private final int shipsLenThree = 2;
    private final int shipsLenFour = 1;

    private final int height = 10;
    private final int width = 10;
    private int shipCount = 10;

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
    
    public boolean hitField(int player, int x, int y){
        if(player == 1) return gridPlayer1.setHit(x, y);
        if(player == 2) return gridPlayer2.setHit(x, y);
        
        return false;
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

    

}