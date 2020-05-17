package it.polimi.ingsw.client.GUI.scenes;


import it.polimi.ingsw.client.GUI.FXMLIpController;
import it.polimi.ingsw.client.GUI.GUI;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;

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

        Image backgroundImage = new Image(gui.getClass().getResourceAsStream("/Graphics/santorini-start.png"));

        Background background = new Background(
                new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                        new BackgroundSize(scene.getWidth(), scene.getHeight(),
                                true, true, true, true)));


        startGameButton.setStyle("-fx-text-fill: #104abc;");
        startGameButton.setTranslateY(235);
        startGameButton.setPrefSize(220,27);
        startLayout.getChildren().addAll(startGameButton);
        startLayout.setBackground(background);


        startGameButton.setOnAction(event -> {

//            TheScene next = new IpPortScene(gui, this.stage);
//            Scene nextScene = next.getScene();
//            this.stage.setScene(nextScene);

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




    @Override
    public Scene getScene() {
        return this.scene;
    }


}
