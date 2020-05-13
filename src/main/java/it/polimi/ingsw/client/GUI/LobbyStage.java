package it.polimi.ingsw.client.GUI;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class LobbyStage {

    private List<String> connectedPlayers;

    private Stage stage;

    private VBox vBox;

    private Label label;


    public LobbyStage(List<String> connectedPlayers) {
        this.connectedPlayers = connectedPlayers;
        this.stage = null;
        this.vBox = null;
    }


     void display(){
        stage = new Stage();

        stage.initModality(Modality.NONE);


        stage.setMinWidth(350);
        stage.setMinHeight(200);

        label = new Label("The players in the LOBBY are");
        label.setStyle("-fx-font: 25px 'Stencil', 'Impact', monospace; -fx-text-fill: #7FC0F6");


        vBox = new VBox(15);
        vBox.setStyle("-fx-background-color: #5C8D3E");
        vBox.getChildren().add(label);

        for(String player : connectedPlayers){

            Label newLabel = new Label(player);
            newLabel.setStyle("-fx-font: 22px 'Stencil', 'Impact', monospace; -fx-text-fill: #C3F1FA");

            vBox.getChildren().add(newLabel);
        }

        vBox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vBox);
        stage.setScene(scene);
        stage.show();
    }


     void close(){

        this.label = new Label("THE LOBBY IS CLOSED");

        Button okButtom = new Button("   OK    ");

        okButtom.setOnAction( a -> {
            stage.close();
        });

    }


    void addPlayer(String newPlayer){

        Label newLabel = new Label(newPlayer);

        vBox.getChildren().add(newLabel);
    }


}