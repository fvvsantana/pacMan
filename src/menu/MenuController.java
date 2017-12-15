package menu;

import menu.view.MenuView;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import menu.itens.MenuContinueModel;
import menu.itens.MenuItemModel;
import menu.itens.MenuMapModel;
import pacman.Controller;


public class MenuController {
    // numero de fases
    public static final int STAGE_NUMBER = 3;
    
    private int option = 0;
    private boolean selected = false;
    public static boolean running = false;
    public static boolean save = false;

    private MenuView menuView;
    private Controller controller;
    private ArrayList<MenuItemModel> menuList;
    
    public void run(Stage primaryStage){
        
        menuView = new MenuView(primaryStage);
        createMenuList();
        addItemsView();
        menuView.printMenu(0);
        addMenuController(menuView.getScene());
        
        new AnimationTimer(){
            long lastTime = 0;
            
            @Override
            public void handle(long now){
                if (!running) {
                    if (controller != null){
                        controller = null;
                        createMenuList();
                        addItemsView();
                        menuView.updateStage();
                    }
                    
                    menuView.printMenu(option);
                }
                if (!running && selected) {
                    int currentStage =  menuList.get(option).getStageNumber();
                    if (currentStage == 0)
                        read();
                    else
                        controller = new Controller(currentStage);
                    
                    controller.run(primaryStage);
                    running = true;
                    selected = false;
                }
                if (save){
                    save();
                    save = false;
                }
            }
        }.start();
        
        menuView.show();
    }
    
    public void createMenuList(){
        menuList = new ArrayList<>();
        if (verifySaveFile()){
            menuList.add(new MenuContinueModel());
        }
        
        for (int i = 1; i <= STAGE_NUMBER; i++){
            menuList.add(new MenuMapModel(i));
        }
    }
    public void addItemsView(){
        menuView.clearMenu();
        for (MenuItemModel item: menuList){
            if (item instanceof MenuContinueModel){
                menuView.addMenuContinue();
                continue;
            }
            menuView.addMenuMap(item.getStageNumber());
        }
    }
    
    private void addMenuController(Scene scene){
        scene.setOnKeyPressed((KeyEvent event) -> {
            switch(event.getCode()){                    
                case RIGHT:
                    if (option < menuList.size() - 1){
                        option++;
                    }
                    break;
                    
                case LEFT:
                    if (option > 0)
                        option--;
                    break;
                case ENTER:
                    selected = true;
            }
        });
    }
    
    
    
    
    // salva o controller no arquivo utilizando serializacao
    public void save() {
        try {
            FileOutputStream fileOutput = new FileOutputStream("pacman.ser");
            ObjectOutputStream objectOutput = new ObjectOutputStream(fileOutput);
            objectOutput.writeObject(controller);
            objectOutput.close();
            fileOutput.close();
            System.out.println("Jogo salvo com sucesso!");
        } catch (IOException ex) {
            System.out.println("Erro ao salvar jogo:");
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // le o controller a partir do arquivo
    public void read() {
        try {
            FileInputStream fileInput = new FileInputStream("pacman.ser");
            ObjectInputStream objectInput = new ObjectInputStream(fileInput);
            controller = (Controller) objectInput.readObject();
            fileInput.close();
            objectInput.close();
            System.out.println("Jogo carregado com sucesso!");
        } catch (FileNotFoundException ex) {
            System.out.println("Nenhum jogo salvo foi encontrado, iniciando um novo jogo.");
            controller = new Controller(1);
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println("Erro ao ler arquivo do jogo!");
            controller = new Controller(1);
            System.out.println("Criando novo jogo.");
        }
    }
    
    public boolean verifySaveFile(){
        try {
            FileInputStream fileInput = new FileInputStream("pacman.ser");
            fileInput.close();
            return true;
        } catch (IOException ex){
            return false;
        }
    }
}
