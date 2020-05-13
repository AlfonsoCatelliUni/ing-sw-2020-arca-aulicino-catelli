package it.polimi.ingsw.client.GUI.scenes;

import it.polimi.ingsw.client.GUI.GUI;
import it.polimi.ingsw.events.CTSEvents.ChosenPlayerNumberEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class SelectNumberPlayersScene implements TheScene {


    private Scene scene;
    private Stage stage;



    public SelectNumberPlayersScene(GUI gui, Stage stage){

        this.stage = stage;

        VBox numberPlayersLayout = new VBox(50);
        numberPlayersLayout.setAlignment(Pos.CENTER);

        Label numberLabel = new Label("Choose the number of players:");
        numberLabel.getStyleClass().add("labelWithBackground");



        Button twoButton = new Button("  2  ");
        twoButton.setTextFill(Color.BLACK);



        Button threeButton = new Button("  3  ");
        threeButton.setTextFill(Color.BLACK);



        numberPlayersLayout.getChildren().addAll(numberLabel, twoButton, threeButton);

        this.scene = new Scene(numberPlayersLayout, 750,500);

        twoButton.setOnAction( e -> {
            gui.getClientView().sendCTSEvent(new ChosenPlayerNumberEvent(gui.getNickname(), 2));
            gui.getLobbyStage().displayWait();
            stage.close();
//            Label text = new Label("Wait Until the lobby is full");
//            text.getStyleClass().add("labelWithBackground");
//            text.setStyle("-fx-font-size: 18px");
//            numberPlayersLayout.getChildren().removeAll(numberLabel, twoButton, threeButton);
//            numberPlayersLayout.getChildren().add(text);


        });

        threeButton.setOnAction( e -> {
            gui.getClientView().sendCTSEvent(new ChosenPlayerNumberEvent(gui.getNickname(), 3));
            gui.getLobbyStage().displayWait();
            stage.close();



//            Label text = new Label("Wait Until the lobby is full");
//            numberPlayersLayout.getChildren().add(text);
//            numberPlayersLayout.getChildren().removeAll(numberLabel, twoButton, threeButton);

        });


    }



    @Override
    public Scene getScene() {
        return this.scene;
    }
}
