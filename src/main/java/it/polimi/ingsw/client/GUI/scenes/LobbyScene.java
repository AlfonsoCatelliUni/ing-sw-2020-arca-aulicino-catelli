package it.polimi.ingsw.client.GUI.scenes;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class LobbyScene implements TheScene {

    private Scene scene;

    private Stage stage;

    public LobbyScene(List<String> connectedPlayers){

        stage = new Stage();

        stage.setMinWidth(300);
        stage.setMinHeight(300);

        Label label = new Label("LOBBY");
        label.setStyle("-fx-font: 25px 'Stencil', 'Impact', monospace; -fx-text-fill: #E94B2B");

        VBox vBox = new VBox(15);
        vBox.setStyle("-fx-background-color: #200500");
        vBox.getChildren().add(label);

        for(String player : connectedPlayers){

            Label newLabel = new Label(player);
            label.setStyle("-fx-font: 18px 'Stencil', 'Impact', monospace; -fx-text-fill: #E94B2B");

            vBox.getChildren().add(newLabel);
        }

        vBox.setAlignment(Pos.CENTER);

        this.scene = new Scene(vBox, 750, 750);
        stage.setScene(scene);




    }



    @Override
    public Scene getScene() {
        return this.scene;
    }
}
