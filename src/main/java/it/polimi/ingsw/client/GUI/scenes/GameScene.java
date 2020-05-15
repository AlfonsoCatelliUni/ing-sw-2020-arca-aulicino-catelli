package it.polimi.ingsw.client.GUI.scenes;

import it.polimi.ingsw.client.FormattedSimpleCell;
import it.polimi.ingsw.client.GUI.FXMLGameController;
import it.polimi.ingsw.client.GUI.GUI;
import it.polimi.ingsw.events.STCEvents.AskWhichPawnsUseEvent;
import it.polimi.ingsw.events.STCEvents.AskInitPawnsEvent;
import it.polimi.ingsw.events.STCEvents.GivePossibleCardsEvent;
import it.polimi.ingsw.events.STCEvents.GivePossibleCellsToMoveEvent;
import it.polimi.ingsw.view.client.ClientView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameScene implements TheScene {

    private GUI gui;

    private Scene scene;

    private Stage stage;

    private FXMLGameController fxmlGameController;

    private GridPane messagePane;


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

        AnchorPane anchorPane = (AnchorPane) root.getChildrenUnmodifiable().get(2);
        HBox hbox = (HBox) anchorPane.getChildren().get(0);
        AnchorPane anchorPane1 = (AnchorPane) hbox.getChildren().get(3);
        messagePane = (GridPane) anchorPane1.getChildren().get(0);
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

    public void manageEvent(AskWhichPawnsUseEvent event, ClientView clientView) {

        Text label = new Text("Choose the pawn that you want to use");

        messagePane.add(label, 0,0);

        fxmlGameController.choosePawnToUse(event.nickname, event.info, clientView);

    }


    // MARK : Supportive Methods ======================================================================================


    @Override
    public Scene getScene() {
        return this.scene;
    }



}
