package model;

import model.grid.ObstacleCellModel;
import model.grid.GridModel;
import model.grid.EmptyCellModel;

public abstract class Maps{
    private static final int DEFAULT_ROWS = 25;
    private static final int DEFAULT_COLS = 25;

    public static GridModel emptyMap(int rows, int cols){
        GridModel grid = new GridModel(rows, cols);
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                grid.addCell(new EmptyCellModel(), i, j);
            }
        }
        return grid;
    }

    //overloading
    public static GridModel emptyMap(){
        return emptyMap(DEFAULT_ROWS, DEFAULT_COLS);
    }

    public static GridModel fullMap(int rows, int cols){
        GridModel grid = new GridModel(rows, cols);
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                grid.addCell(new ObstacleCellModel(), i, j);
            }
        }
        return grid;
    }

    //overloading
    public static GridModel fullMap(){
        return fullMap(DEFAULT_ROWS, DEFAULT_COLS);
    }

}