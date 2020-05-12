package it.polimi.ingsw.client.GUI.scenes;

import it.polimi.ingsw.client.GUI.GUI;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GameScene implements TheScene {

    private Scene scene;

    private Stage stage;


    public GameScene(GUI gui, Stage stage){
    }


    @Override
    public Scene getScene() {
        return this.scene;
    }


}
