package view.grid;

import utils.Position;

public class GridView{
    Position position;
    private double width;
    private double height;

    private int rows;
    private int cols;
	
    private CellView[][] grid;
    private double cellWidth;
    private double cellHeight;

    //set the dimensions and the position of the grid
    public GridView(int rows, int cols, double width, double height, Position position) {
        this.position = position;
        this.rows = rows;
        this.cols = cols;
        this.width = width;
        this.height = height;

        grid = new CellView[rows][cols];
        cellWidth = width/cols;
        cellHeight = height/rows;
    }

    //add cell to the grid, set the cell's position and the cell's size
    public void addCell(CellView cell, int row, int col){
        if(cell != null){
            grid[row][col] = cell;
            cell.setPosition(new Position(position.getX() + col * cellWidth, position.getY() + row * cellHeight));
            cell.setSize(cellWidth, cellHeight);
        }
    }

    public void removeCell(int row, int col){
        grid[row][col] = null;
    }

    public CellView getCell(int row, int col){
        return grid[row][col];
    }

    public double getCellWidth() {
        return cellWidth;
    }

    public double getCellHeight() {
        return cellHeight;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    //get the position where the cell is placed relative to its container
    public Position getCellPosition(int row, int col){
        return new Position(position.getX() + col * cellWidth, position.getY() + row * cellHeight);
    }
    
    public Position getCellPosition(double row, double col){
        return new Position(position.getX() + col * cellWidth, position.getY() + row * cellHeight);
    }

}