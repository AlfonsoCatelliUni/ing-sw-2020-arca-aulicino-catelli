package it.polimi.ingsw.client.GUI.scenes;

import it.polimi.ingsw.client.GUI.FXMLChooseCardController;
import it.polimi.ingsw.client.GUI.GUI;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.IOException;
import java.util.List;

public class ChooseCardScene implements TheScene {

    private GUI gui;

    private Scene scene;

    private Stage stage;

    private FXMLChooseCardController controller;



    public ChooseCardScene(GUI gui, Stage stage, List<String> cardsName, List<String> effectsCard) {

        this.gui = gui;
        this.stage = stage;
        this.stage.setResizable(false);


        Parent root = null;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader( getClass().getResource("/FXML/ChooseCardScene.fxml") );
            root = fxmlLoader.load();
            controller = fxmlLoader.getController();

            controller.setController(stage, gui.getNickname(), gui.getClientView(), cardsName, effectsCard);
            controller.setCards();

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
