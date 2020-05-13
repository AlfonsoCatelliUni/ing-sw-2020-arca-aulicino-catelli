package it.polimi.ingsw.client.GUI;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PlayersInteraction {

    private List<Pane> cellsList;

    //si può ottimizzare
    public void choosePawnToUse(List<Point> points) {

        int listSize = points.size();

        for (int i = 0; i < cellsList.size() && listSize > 0; i++) {

            for (Point point : points) {

                if (point.x * 5 + point.y == i) {

                    listSize--;
                    cellsList.get(i).setOnMouseClicked(mouseEvent -> {
                        //invia messaggio con le coordinate scelte
                    });
                    break;
                }

            }

        }

    }

}
