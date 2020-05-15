package it.polimi.ingsw.client.GUI.scenes;

import it.polimi.ingsw.client.FormattedSimpleCell;
import it.polimi.ingsw.client.GUI.FXMLGameController;
import it.polimi.ingsw.client.GUI.GUI;
import it.polimi.ingsw.events.STCEvents.AskInitPawnsEvent;
import it.polimi.ingsw.events.STCEvents.GivePossibleCardsEvent;
import it.polimi.ingsw.events.STCEvents.GivePossibleCellsToMoveEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameScene implements TheScene {

    private GUI gui;

    private Scene scene;

    private Stage stage;

    public FXMLGameController fxmlGameController;


    public GameScene(GUI gui, Stage stage) {

        this.gui = gui;
        this.stage = stage;
        this.fxmlGameController = new FXMLGameController();


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


    // MARK : Game Event Managers ======================================================================================


    public void manageEvent(GivePossibleCardsEvent event) {

    }


    public void manageEvent(AskInitPawnsEvent event) {

        fxmlGameController.manageEvent(event);

    }


    public void manageEvent(GivePossibleCellsToMoveEvent event) {

        fxmlGameController.manageEvent(event, gui.getRowUsedPawn(), gui.getColumnUsedPawn());

    }


    // MARK : Supportive Methods ======================================================================================


    @Override
    public Scene getScene() {
        return this.scene;
    }



}
