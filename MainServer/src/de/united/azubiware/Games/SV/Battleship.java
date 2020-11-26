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

    private final Grid grid;

    public Battleship() {
        grid = new Grid();
        draw();
    }

    private void draw() {
    }

    public void checkPlayerWin() {

    }

    private void setShips() {
        for (int i = 0; i < shipsLenOne; ) {
            if (createRandomShip(1)) i++;
        }
        for (int i = 0; i < shipsLenTwo; ) {
            if (createRandomShip(2)) i++;
        }
        for (int i = 0; i < shipsLenThree; ) {
            if (createRandomShip(3)) i++;
        }
        for (int i = 0; i < shipsLenFour; ) {
            if (createRandomShip(4)) i++;
        }
    }

    private boolean createRandomShip(int length) {
        int randomX = (int) (Math.random() * width);
        int randomY = (int) (Math.random() * height);
        boolean randomBoolean = Math.random() >= 0.5f;
        return grid.setShip(randomX, randomY, length, randomBoolean);
    }

    private class Grid {
        private final CellType[][] cells = new CellType[width][height];

        private boolean isShip(int x, int y) {
            return cells[x][y] == CellType.SHIP;
        }

        private boolean isWater(int x, int y){
            return !isShip(x, y);
        }

        private CellType getNextCell(int x, int y, int stride, boolean horizontal){
            if(horizontal && !isCellOutOfBounds(x+stride, y)) {
                return cells[x+stride][y];
            }  else if(!isCellOutOfBounds(x, y+stride)){
                return cells[x][y+stride];
            }
            return CellType.WATER;
        }

        private boolean isNextCellShip(int x, int y, int stride, boolean hor){
            return getNextCell(x, y, stride, hor) == CellType.SHIP;
        }

        public boolean setShip(int x, int y, int length, boolean horizontal) {
            if (isShip(x, y)) return false;

            for (int i = 0; i < length; i++) {
                if(isNextCellShip(x, y, i, horizontal)) return false;
                //TODO: return if a cell is not surrounded by water
            }

            //TODO: Set cells to Type Ship

            return true;
        }

        private boolean isCellOutOfBounds(int x, int y){
            //TODO
            return false;
        }

        public boolean isSurroundedByWater(int x, int y) {
            //TODO: check if Array out of bounds

            if(isShip(x+1, y+1)
            if (cells[x + 1][y + 1] == CellType.WATER ) {

            }

            return false;
        }

    }

    private enum CellType {
        WATER,
        SHIP
    }

}