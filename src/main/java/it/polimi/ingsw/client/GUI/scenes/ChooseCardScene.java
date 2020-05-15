package it.polimi.ingsw.client.GUI.scenes;

import it.polimi.ingsw.client.GUI.FXMLChooseCardController;
import it.polimi.ingsw.client.GUI.GUI;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ChooseCardScene implements TheScene {

    private Scene scene;

    private Stage stage;

    private FXMLChooseCardController controller;



    public ChooseCardScene(GUI gui, Stage stage) {

        this.stage = stage;

        this.controller = new FXMLChooseCardController();




        Parent root = null;
        try {
            root = FXMLLoader.load( getClass().getResource("/FXML/ChooseCardScene.fxml") );
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert root != null;
        this.scene = new Scene(root);

    }





    @Override
    public Scene getScene() {
        return this.scene;
    }
}
