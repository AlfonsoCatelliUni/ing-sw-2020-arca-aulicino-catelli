package it.polimi.ingsw.client.GUI.scenes;

import it.polimi.ingsw.client.GUI.GUI;
import it.polimi.ingsw.events.STCEvents.GivePossibleCardsEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.List;

public class ChooseCardScene implements TheScene {

    private Scene scene;

    private Stage stage;



    public ChooseCardScene(GUI gui, Stage stage, GivePossibleCardsEvent event) {

        this.stage = stage;

        GridPane chooseCardLayout = new GridPane();

        // margin around all grid
        chooseCardLayout.setPadding(new Insets(20, 20, 20, 20));
        // margin between row
        chooseCardLayout.setVgap(15);
        // margin between column
        chooseCardLayout.setHgap(12);

        for (int i = 0; i < event.cardsName.size(); i++) {


            Label cardName = new Label(event.cardsName.get(i));
            Label cardEffect = new Label(event.cardsEffect.get(i));


            Button cardButton = new Button(event.cardsName.get(i));


            GridPane.setConstraints(cardName, 1, i + 1);
            GridPane.setConstraints(cardEffect, 2, i + 1);
            GridPane.setConstraints(cardButton, i + 1, 6);


            chooseCardLayout.getChildren().addAll(cardName, cardEffect, cardButton);
        }

        scene = new Scene(chooseCardLayout, 750, 500);

    }




    @Override
    public Scene getScene() {
        return this.scene;
    }
}
