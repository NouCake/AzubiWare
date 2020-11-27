package de.united.azubiware.Games.SV;

import java.util.LinkedList;
import java.util.List;

public class Grid {

    public static int[][] GetShipSetup(Grid grid){
        List<int[]> shipCells = new LinkedList<>();
        for (int x = 0; x < grid.width; x++) {
            for (int y = 0; y < grid.height; y++) {
                if(grid.getCell(x, y).isShip()){
                    shipCells.add(new int[]{x, y});
                }
            }
        }
        return shipCells.toArray(new int[0][]);
    }

    private enum CellType {
        WATER,
        SHIP
    }

    private class Cell {
        private CellType type;
        private boolean hit = false;

        public boolean isShip() {
            return type == CellType.SHIP;
        }

        public boolean isWater() {
            return type == CellType.WATER;
        }

        public boolean isHit() {
            return hit;
        }

        public void setHit() {
            hit = true;
        }

        public void setType(CellType type) {
            this.type = type;
        }
    }

    private final int width;
    private final int height;
    private final Cell[][] cells;

    public Grid(int width, int height) {
        this.width = width;
        this.height = height;
        cells = new Cell[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                cells[x][y] = new Cell();
                cells[x][y].setType(CellType.WATER);
            }
        }
    }

    public boolean setHit(int x, int y) {
        if (isCellOutOfBounds(x, y)) return false;
        boolean hit = getCell(x, y).isShip();
        getCell(x, y).setHit();
        return hit;
    }
    
    public boolean isHit( int x, int y) {
        return getCell(x, y).isHit();
    }

    public boolean areAllShipsSunk() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (getCell(i, j).isShip() && !getCell(i, j).isHit()) return false;
            }
        }
        return true;
    }

    public void draw() {
        for (int y = 0; y < height; y++) {
            String line = "";
            for (int x = 0; x < width; x++) {
                line += getCell(x, y).isShip() ? "S" : "_";
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

    private Cell getCell(int x, int y) {
        return cells[x][y];
    }

    public boolean isNextCellOutOfBounds(int x, int y, int stride, boolean hor) {
        if (hor) {
            return isCellOutOfBounds(x + stride, y);
        } else {
            return isCellOutOfBounds(x, y + stride);
        }
    }

    public boolean setShip(int x, int y, int length, boolean horizontal) {
        if (isCellOutOfBounds(x, y)) return false;
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

    public boolean isCellOutOfBounds(int x, int y) {
        return x < 0 || x >= width || y < 0 || y >= height;
    }

    public boolean isSurroundedByWater(int x, int y) {

        if (!isCellOutOfBounds(x + 1, y) && getCell(x + 1, y).isShip()) return false;
        if (!isCellOutOfBounds(x - 1, y) && getCell(x - 1, y).isShip()) return false;
        if (!isCellOutOfBounds(x, y + 1) && getCell(x, y + 1).isShip()) return false;
        if (!isCellOutOfBounds(x, y - 1) && getCell(x, y - 1).isShip()) return false;

        return true;
    }

}



