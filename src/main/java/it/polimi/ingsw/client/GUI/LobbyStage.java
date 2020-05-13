package it.polimi.ingsw.client.GUI;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;

public class LobbyStage {

    private List<String> connectedPlayers;

    private Stage stage;

    private VBox vBox;




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

        Label title = new Label("The players in the LOBBY are");
        title.setStyle("-fx-font: 25px 'Stencil', 'Impact', monospace; -fx-text-fill: #7FC0F6");


        vBox = new VBox(15);
        vBox.setStyle("-fx-background-color: #5C8D3E");
        vBox.getChildren().add(title);

        for(String player : connectedPlayers){

            Label newLabel = new Label(player);
            newLabel.setStyle("-fx-font: 22px 'Stencil', 'Impact', monospace; -fx-text-fill: #C3F1FA");

            vBox.getChildren().add(newLabel);
        }

        vBox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vBox, 500, 500);
        stage.setScene(scene);
        stage.show();
    }

    public void displayWait(){
        Label text = new Label("Wait until the lobby is full");
        vBox.getChildren().add(text);
    }


     void close(List<String> playerNickname){

        vBox.getChildren().clear();

        Label text = new Label("THE LOBBY IS CLOSED, players are");
        text.setStyle("-fx-font: 25px 'Stencil', 'Impact', monospace; -fx-text-fill: #7FC0F6");

        vBox.getChildren().add(text);

         for(String nickname : playerNickname){

             Label newNickname = new Label(nickname);
             newNickname.setStyle("-fx-font: 22px 'Stencil', 'Impact', monospace; -fx-text-fill: #C3F1FA");

             vBox.getChildren().add(newNickname);


         }


        Button closeButtom = new Button("   CLOSE    ");

        vBox.getChildren().add(closeButtom);

        closeButtom.setOnAction(a -> {
            stage.close();
        });

    }


    void addPlayer(String newPlayer){

        Label newLabel = new Label(newPlayer);

        vBox.getChildren().add(newLabel);
    }


}
