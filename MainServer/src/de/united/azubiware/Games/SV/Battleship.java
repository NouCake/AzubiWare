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
        gridPlayer1 = new Grid();
        gridPlayer2 = new Grid();
        setShips(gridPlayer1);
        setShips(gridPlayer2);
    }

    private void draw() {
        System.out.println("Player1");
        gridPlayer1.draw();

        System.out.println("Player2");
        gridPlayer2.draw();
    }

    public void checkPlayerWin() {

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

    private class Cell{
        private CellType type;
        private boolean hit = false;

        public boolean isShip() {
            return type == CellType.SHIP;
        }

        public boolean isWater(){
            return type == CellType.WATER;
        }

        public boolean isHit(){
            return hit;
        }

        public void setHit(){
            hit = true;
        }

        public void setType(CellType type){
            this.type = type;
        }
    }

    private class Grid {
        private final Cell[][] cells = new Cell[width][height];

        public boolean areAllShipsSunk(){
            //TODO
            return true;
        }

        public void draw() {
            for (int x = 0; x < width; x++) {
                String line = "";
                for (int y = 0; y < height; y++) {
                    line += getCell(x, y).isShip() ? "_" : "S";
                }
                System.out.println(line);
            }
        }

        private Cell getNextCell(int x, int y, int stride, boolean horizontal) {
            if (horizontal) {
                return cells[x + stride][y];
            } else {
                return cells[x][y + stride];
            }
        }

        private Cell getCell(int x, int y){
            return cells[x][y];
        }

        private boolean isNextCellOutOfBounds(int x, int y, int stride, boolean hor) {
            if (hor) {
                return isCellOutOfBounds(x + stride, y);
            } else {
                return isCellOutOfBounds(x, y + stride);
            }
        }

        public boolean setShip(int x, int y, int length, boolean horizontal) {
            if  (isCellOutOfBounds(x, y)) return false;
            if (getCell(x, y).isShip()) return false;

            for (int i = 0; i < length; i++) {
                if (isNextCellOutOfBounds(x, y, i, horizontal)) return false;
                if (getNextCell(x, y, i, horizontal).isShip()) return false;
                if (!isNextCellSurroundedByWater(x, y, i, horizontal)) return false;
            }

            for (int i = 0; i < length; i++) {
                getNextCell(x, y, i, horizontal).setType(CellType.SHIP);
            }

            return true;
        }

        private boolean isNextCellSurroundedByWater(int x, int y, int stride, boolean hor) {
            if (hor) {
                return isSurroundedByWater(x + stride, y);
            } else {
                return isSurroundedByWater(x, y + stride);
            }
        }

        private boolean isCellOutOfBounds(int x, int y) {
            return x >= 0 && x < width && y >= 0 && y < height;
        }

        public boolean isSurroundedByWater(int x, int y) {

            if (!isCellOutOfBounds(x + 1, y) && getCell(x + 1, y).isShip()) return false;
            if (!isCellOutOfBounds(x - 1, y) && getCell(x - 1, y).isShip()) return false;
            if (!isCellOutOfBounds(x, y + 1) && getCell(x, y + 1).isShip()) return false;
            if (!isCellOutOfBounds(x, y - 1) && getCell(x, y - 1).isShip()) return false;

            return true;
        }

    }

    private enum CellType {
        WATER,
        SHIP
    }

}