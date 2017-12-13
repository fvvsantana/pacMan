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
            
            // le a posicao inicial do pacman
            grid.setPacmanRow(arquivo.nextDouble());
            grid.setPacmanCol(arquivo.nextDouble());
            
            // eatables conta o numero de coisas comiveis
            int eatables = 0;
            
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
                            eatables++;
                            break;
                        case 3:
                            grid.addCell(new PowerPelletCellModel(), i, j);
                            eatables++;
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
            grid.setEatables(eatables);
            return grid;
        } catch (FileNotFoundException ex) {
            System.err.println("\nErro ao abrir arquivo do mapa");
            System.exit(0);
            return null;
        }
    }

}