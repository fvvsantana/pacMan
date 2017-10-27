package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import model.grid.ObstacleCellModel;
import model.grid.GridModel;
import model.grid.EmptyCellModel;
import model.grid.PacDotCellModel;
import model.grid.PowerPelletCellModel;

public abstract class Maps{
    private static final int DEFAULT_ROWS = 20;
    private static final int DEFAULT_COLS = 20;

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
    
    public static GridModel mainMap() {
        return fileMap("src/maps/map.txt");
    }
    
    // le um mapa a partir do arquivo
    public static GridModel fileMap(String filePath) {
        try {
            // cria scanner para o arquivo recebido
            Scanner arquivo = new Scanner(new File(filePath));
            
            // le o numero de linhas e colunas
            int rows = arquivo.nextInt();
            int cols = arquivo.nextInt();
            
            // cria o grid e adiciona os elementos
            GridModel grid = new GridModel(rows, cols);
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    int type = arquivo.nextInt();
                    switch (type) {
                        case 1:
                            grid.addCell(new ObstacleCellModel(), i, j);
                            break;
                        case 2:
                            grid.addCell(new PacDotCellModel(), i, j);
                        case 3:
                            grid.addCell(new PowerPelletCellModel(), i, j);
                        default:
                            grid.addCell(new EmptyCellModel(), i, j);
                    }
                }
            }
            return grid;
        } catch (FileNotFoundException ex) {
            System.err.println("Erro ao abrir arquivo do mapa");
            System.exit(0);
            return null;
        }
    }

}