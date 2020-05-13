package it.polimi.ingsw.client.GUI;

import it.polimi.ingsw.events.CTSEvents.ChosenPawnToUseEvent;
import it.polimi.ingsw.view.client.ClientView;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PlayersInteraction {

    private String playerNickname;

    private List<Pane> cellsList;

    public PlayersInteraction() {
    }


    public void choosePawnToUse(List<Point> points, ClientView clientView) {

        // TODO : tell the player that he has to choose the pawn

        int listSize = points.size();

        for (int i = 0; i < cellsList.size() && listSize > 0; i++) {

            for (Point point : points) {

                if (point.x * 5 + point.y == i) {

                    listSize--;

                    //not sure about this chief
                    cellsList.get(i).getChildren().clear();

                    Button button = new Button();
                    cellsList.get(i).getChildren().add(button);

                    button.setOnAction(actionEvent -> {
                        clientView.sendCTSEvent(new ChosenPawnToUseEvent(playerNickname, point.x, point.y));
                    });

                    points.remove(point);
                    break;
                }

            }

        }

    }

}
