package de.united.azubiware.Games.SV;

public class Grid {

    private enum CellType {
        WATER,
        SHIP
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

    private final int width;
    private final int height;
    private final Cell[][] cells;


    public Grid(int width, int height){
        this.width = width;
        this.height = height;
        cells = new Cell[width][height];
    }

    public boolean setHit(int x, int y){
        if(isCellOutOfBounds(x, y)) return false;
        boolean hit = getCell(x,y).isHit();
        getCell(x,y).setHit();
        return hit;
    }

    public boolean areAllShipsSunk(){
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
              if (getCell(i,j).isShip() && !getCell(i, j).isHit()) return false;
            }
        }
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



