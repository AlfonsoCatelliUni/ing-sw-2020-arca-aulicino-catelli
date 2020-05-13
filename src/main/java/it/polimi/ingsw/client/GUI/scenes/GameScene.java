package it.polimi.ingsw.client.GUI.scenes;

import it.polimi.ingsw.client.GUI.GUI;
import it.polimi.ingsw.client.GUI.PlayersInteraction;
import it.polimi.ingsw.events.STCEvents.GivePossibleCardsEvent;
import it.polimi.ingsw.events.STCEvents.GivePossibleCellsToMoveEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class GameScene implements TheScene {

    private GUI gui;

    private Scene scene;

    private Stage stage;

    public PlayersInteraction playerInteraction;


    public GameScene(GUI gui, Stage stage) {

        this.gui = gui;
        this.stage = stage;


        // load the style from FXML file in Resources
        Parent root = null;
        try {
            root = FXMLLoader.load( getClass().getResource("/FXML/new_game_layout.fxml") );
        } catch (IOException e) {
            e.printStackTrace();
        }



        assert root != null;
        this.scene = new Scene(root);

    }



    public void manageEvent(GivePossibleCardsEvent event) {

    }


    public void manageEvent(GivePossibleCellsToMoveEvent event) {

        List<Point> cellsAvailableToMove = event.cellsAvailableToMove;

        for ( Point p : cellsAvailableToMove ) {

        }


    }



    @Override
    public Scene getScene() {
        return this.scene;
    }



}
