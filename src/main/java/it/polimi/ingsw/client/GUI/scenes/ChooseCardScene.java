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

    String chosenCard;



    public ChooseCardScene(GUI gui, Stage stage, GivePossibleCardsEvent event) {

        this.stage = stage;

        GridPane chooseCardLayout = new GridPane();

        // margin around all grid
        chooseCardLayout.setPadding(new Insets(20, 20, 20, 20));
        // margin between row
        chooseCardLayout.setVgap(15);
        // margin between column
        chooseCardLayout.setHgap(12);

        // TODO: fix the placement of the buttoms
        for (int i = 0; i < event.cardsName.size(); i++) {


            String nameCard = event.cardsName.get(i);
            String effectDescription = event.cardsEffect.get(i);

            Label cardName = new Label(nameCard);
            Label cardEffect = new Label(effectDescription);


            Button cardButton = new Button(event.cardsName.get(i));

            cardButton.setOnAction(a -> {
              chosenCard = nameCard;
            });


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
