package it.polimi.ingsw.client.GUI.FXMLControllers;

import it.polimi.ingsw.client.GUI.FXMLControllers.FXMLIpController;
import it.polimi.ingsw.client.GUI.GUI;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class FXMLStartController {


    private GUI gui;

    private Stage stage;

    @FXML
    private Button startGameButton;


    public void initialize() {

        initializeButton();

    }

    private void initializeButton() {

        //startGameButton.setDefaultButton(true);

        startGameButton.setOnMouseClicked(mouseEvent -> {

            Parent root = null;
            FXMLIpController controller;
            try {
                FXMLLoader fxmlLoader = new FXMLLoader( getClass().getResource("/FXML/ipScene.fxml") );
                root = fxmlLoader.load();

                controller = fxmlLoader.getController();
                controller.setController(gui);

            } catch (IOException e) {
                e.printStackTrace();
            }

            assert root != null;
            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.setResizable(false);

            stage.show();

        });

    }

    public void initStartController(GUI gui, Stage stage) {
        this.gui = gui;
        this.stage = stage;
    }

}
