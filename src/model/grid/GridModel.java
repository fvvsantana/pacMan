package model.grid;

public class GridModel{
    private CellModel[][] grid;
    private final int rows;
    private final int cols;

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
}