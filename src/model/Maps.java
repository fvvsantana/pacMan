package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.Scanner;
import model.grid.DoorCellModel;
import model.grid.ObstacleCellModel;
import model.grid.GridModel;
import model.grid.EmptyCellModel;
import model.grid.PacDotCellModel;
import model.grid.PowerPelletCellModel;

public abstract class Maps{
    private static final int DEFAULT_ROWS = 20;
    private static final int DEFAULT_COLS = 20;

    // cria um mapa vazio com o numero de linhas e colunas recebidos
    public static GridModel emptyMap(int rows, int cols){
        GridModel grid = new GridModel(rows, cols);
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                grid.addCell(new EmptyCellModel(), i, j);
            }
        }
        return grid;
    }

    // cria um mapa vazio com o numero de linhas e colunas padrão
    public static GridModel emptyMap(){
        return emptyMap(DEFAULT_ROWS, DEFAULT_COLS);
    }
    
    // carrega o mapa principal a partir do arquivo
    public static GridModel mainMap() {
        return fileMap("src/maps/map.txt");
    }
    
    // le um mapa a partir do arquivo recebido
    public static GridModel fileMap(String filePath) {
        try {
            // cria scanner para o arquivo recebido
            Scanner arquivo = new Scanner(new File(filePath)).useLocale(Locale.US);
            
            // le o numero de linhas e colunas
            int rows = arquivo.nextInt();
            int cols = arquivo.nextInt();
            
            // cria o grid no tamanho adequado
            GridModel grid = new GridModel(rows, cols);
            
            // le a posicao do spawn
            grid.setSpawnRow(arquivo.nextInt());
            grid.setSpawnCol(arquivo.nextInt());
            
            // le a posicao da fruta
            grid.setFruitRow(arquivo.nextDouble());
            grid.setFruitCol(arquivo.nextDouble());
            
            // le a posicao inicial do pacman
            grid.setPacmanRow(arquivo.nextDouble());
            grid.setPacmanCol(arquivo.nextDouble());
            
            // adiciona os elementos no grid
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    int type = arquivo.nextInt();
                    switch (type) {
                        case 1:
                            grid.addCell(new ObstacleCellModel(), i, j);
                            break;
                        case 2:
                            grid.addCell(new PacDotCellModel(), i, j);
                            break;
                        case 3:
                            grid.addCell(new PowerPelletCellModel(), i, j);
                            break;
                        case 4:
                            grid.addCell(new DoorCellModel(), i, j);
                            break;
                        default:
                            grid.addCell(new EmptyCellModel(), i, j);
                            break;
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