package it.polimi.ingsw.client.GUI.scenes;

import com.sun.javafx.fxml.builder.JavaFXSceneBuilder;
import it.polimi.ingsw.client.GUI.GUI;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class StartScene implements TheScene {

    private Scene scene;

    private Stage stage;



    public StartScene(GUI gui, Stage stage){

        this.stage = stage;

        VBox startLayout = new VBox(50);
        startLayout.setAlignment(Pos.CENTER);

        Label welcomeLabel = new Label("Welcome to Santorini");

        welcomeLabel.setStyle("-fx-font-size: 25px");

        Button startGameButton = new Button("Start game");


        this.scene = new Scene(startLayout, 750, 500);

        startLayout.getChildren().addAll(welcomeLabel, startGameButton);


        startGameButton.setOnAction(e -> {

            TheScene next = new IpPortScene(gui, this.stage);
            Scene nextScene = next.getScene();
            this.stage.setScene(nextScene);

        });
    }







    @Override
    public Scene getScene() {
        return this.scene;
    }
}
