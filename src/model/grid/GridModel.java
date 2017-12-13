package model.grid;

import java.io.Serializable;

public class GridModel implements Serializable {
    private final CellModel[][] grid;
    private final int rows;
    private final int cols;

    private int spawnRow;
    private int spawnCol;
    
    private double pacmanRow;
    private double pacmanCol;
    
    // conta o numero de coisas comiveis restante (o pacman deve comer todas para ganhar)
    private int eatables;
    
    //set the dimensions and the position of the grid
    public GridModel(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        grid = new CellModel[rows][cols];
    }

    //add cell to the grid, set the cell's position and the cell's size
    public void addCell(CellModel cell, int row, int col){
        grid[row][col] = cell;
    }

    public void removeCell(int row, int col){
        grid[row][col] = null;
    }

    public CellModel getCell(int row, int col){
        return grid[row][col];
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public int getSpawnRow() {
        return spawnRow;
    }

    public void setSpawnRow(int spawnRow) {
        this.spawnRow = spawnRow;
    }

    public int getSpawnCol() {
        return spawnCol;
    }

    public void setSpawnCol(int spawnCol) {
        this.spawnCol = spawnCol;
    }

    public double getPacmanRow() {
        return pacmanRow;
    }

    public void setPacmanRow(double pacmanRow) {
        this.pacmanRow = pacmanRow;
    }

    public double getPacmanCol() {
        return pacmanCol;
    }

    public void setPacmanCol(double pacmanCol) {
        this.pacmanCol = pacmanCol;
    }

    public int getEatables() {
        return eatables;
    }

    public void setEatables(int eatables) {
        this.eatables = eatables;
    }
}