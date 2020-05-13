package it.polimi.ingsw.client.GUI.scenes;

import com.sun.javafx.fxml.builder.JavaFXSceneBuilder;
import it.polimi.ingsw.client.GUI.GUI;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class StartScene implements TheScene {

    private Scene scene;

    private Stage stage;



    public StartScene(GUI gui, Stage stage){

        this.stage = stage;
        this.stage.setResizable(false);

        StackPane startLayout = new StackPane();
        startLayout.setAlignment(Pos.CENTER);

        Button startGameButton = new Button("Start game");


        this.scene = new Scene(startLayout, 750, 750);

        Image backgroundImage = new Image(gui.getClass().getResourceAsStream("/Graphics/santorini-copia.png"));

        Background background = new Background(
                new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                        new BackgroundSize(scene.getWidth(), scene.getHeight(),
                                true, true, true, true)));


        startGameButton.setStyle("-fx-text-fill: #104abc;");
        // per coprire il nome sotto santorini nelle dimensioni standard
        // startGameButton.setTranslateY(235);
        startGameButton.setPrefSize(220,27);
        startLayout.getChildren().addAll(startGameButton);
        startLayout.setBackground(background);


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
