package view.characters;

import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import utils.GhostState;

import utils.Position;
import utils.Updatable;

public abstract class GhostView implements Updatable {
    private final ImageView img;
    private final Image imageAlive1;
    private final Image imageAlive2;
    private final Image imageRunning1;
    private final Image imageRunning2;
    private final Image imageRunning3;
    private final Image imageEye;
    
    private GhostState viewState;
    
    // tamanhos da celula
    private final double cellWidth;
    private final double cellHeight;
    
    // multiplicador de tamanho para o fantasmas ser maior que uma celula
    private static final double SIZE_MULTIPLIER = 1.2;
    
    // contador para atualizar a imagem
    private int counter = 0;
    // numero de atualizações que deve esperar para trocar a imagem
    private static final int INTERVAL = 5;
    
    public GhostView (String imagePath1, String imagePath2, double cellWidth, double cellHeight){
        imageAlive1 = new Image (imagePath1);
        imageAlive2 = new Image (imagePath2);
        imageRunning1 = new Image ("/images/ghosthollow1.png");
        imageRunning2 = new Image ("/images/ghosthollow2.png");
        imageRunning3 = new Image ("/images/ghosthollow3.png");
        imageEye = new Image ("/images/ghosteye.png");
        img = new ImageView(imageAlive1);
        
        this.cellHeight = cellHeight;
        this.cellWidth = cellWidth;
        img.setFitWidth(cellWidth*SIZE_MULTIPLIER);
        img.setFitHeight(cellHeight*SIZE_MULTIPLIER);
        
        viewState = GhostState.NORMAL;
    }
    
    public GhostState getState() {
        return viewState;
    }

    public void setState(GhostState state) {
        this.viewState = state;
    }
    
    @Override
    public void update() {
        // alterna entre duas imagens de acordo com o estado atual
        Image image1, image2;
        switch (viewState) {
            case RUNNING:
                image1 = imageRunning2;
                image2 = imageRunning3;
                break;
            case RUNNING_END:
                image1 = imageRunning1;
                image2 = imageRunning2;
                break;
            case DEAD1:
            case DEAD2:
                image1 = imageEye;
                image2 = imageEye;
                break;
            default:
                image1 = imageAlive1;
                image2 = imageAlive2;
                break;
        }
        if (counter == INTERVAL) {
            img.setImage((image2));
        } else if (counter == INTERVAL * 2) {
            img.setImage(image1);
            counter = 0;
        }
        counter++;
    }
     
    public void setImage (Image image){
        img.setImage(image);
    }

    public ImageView getImg() {
        return img;
    }
    
    // consider the position inside a map cell
    public void setCellPosition(Position position){
        img.setX(position.getX() - cellWidth*(SIZE_MULTIPLIER-1)/2);
        img.setY(position.getY() - cellHeight*(SIZE_MULTIPLIER-1)/2);
    }
    
}
