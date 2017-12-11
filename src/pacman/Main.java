package pacman;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    Controller controller;

    @Override
    public void init() throws Exception{
        try {
            read();            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception{
        try {
            controller.run(primaryStage);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() throws Exception {
        save();
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
            controller = new Controller();
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println("Erro ao ler arquivo do jogo:");
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            controller = new Controller();
            System.out.println("Criando novo jogo.");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
