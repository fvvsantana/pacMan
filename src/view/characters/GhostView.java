package view.characters;

import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

import utils.Position;
import utils.Updatable;

public abstract class GhostView implements Updatable {
    private final ImageView img;
    private final Image image1;
    private final Image image2;
    private final Image image3;
    private final Image image4;
    private final Image image5;
    private boolean running;
    private boolean runningAway;
    
    private int temporaryCounter = 0;
    // contador para atualizar a imagem
    private int counter = 0;
    // numero de atualizações que deve esperar para trocar a imagem
    private static final int INTERVAL = 5;
    private static int TEMPORARYINTERVAL;
    
    public GhostView (String imagePath1, String imagePath2, double width, double height){
        image1 = new Image (imagePath1);
        image2 = new Image (imagePath2);
        image3 = new Image ("/images/ghosthollow1.png");
        image4 = new Image ("/images/ghosthollow2.png");
        image5 = new Image ("/images/ghosthollow3.png");
        img = new ImageView(imagePath1);
        img.setFitWidth(width);
        img.setFitHeight(height);
        
        running = false;
        runningAway = false;
    }
    
    public void setRunning(boolean running){
        this.running = running;
    }
    
    public boolean getRunning (){
        return running;
    }
    public void setRunningAway(boolean runningAway){
        this.runningAway = runningAway;
    }
    
    public boolean getRunningAway (){
        return runningAway;
    }
    
    
    
    @Override
    public void update() {
        if (!runningAway && !running){
            
            if (counter == INTERVAL) {
                img.setImage((image2));
            } else if (counter == INTERVAL*2) {
                img.setImage(image1);
                counter = 0;
            }
            temporaryCounter = 0;
            
        }else if (runningAway && !running){
            if (temporaryCounter <= 360){
                if (counter == INTERVAL) {
                    img.setImage(image4);
                } else if (counter == INTERVAL*2) {
                    img.setImage(image5);
                    counter = 0;
                }
            }else if ((temporaryCounter > 360) && (temporaryCounter <= 720)) {
                if (counter == INTERVAL) {
                    img.setImage(image3);
                } else if (counter == INTERVAL*2) {
                    img.setImage(image5);
                    counter = 0;
                }
            }else if (temporaryCounter > 720){
                runningAway = false;
                running = false;
                temporaryCounter = 0;
            }
        }else if(!runningAway && running)  img.setImage(image3);
        
        temporaryCounter++;
        
        counter++;
    }
     
    public void setImage (Image image){
        img.setImage(image);
    }

    public ImageView getImg() {
        return img;
    }
    
    public void setPosition(Position position){
        img.setX(position.getX());
        img.setY(position.getY());
    }
    
}
