package it.polimi.ingsw.client.GUI.scenes;

import it.polimi.ingsw.client.GUI.FXMLControllers.FXMLGameController;
import it.polimi.ingsw.client.GUI.GUI;
import it.polimi.ingsw.events.STCEvents.AskWhichPawnsUseEvent;
import it.polimi.ingsw.events.STCEvents.AskInitPawnsEvent;
import it.polimi.ingsw.events.STCEvents.GivePossibleCardsEvent;
import it.polimi.ingsw.events.STCEvents.GivePossibleCellsToMoveEvent;
import it.polimi.ingsw.view.client.ClientView;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class GameScene implements TheScene {

    private GUI gui;

    private Scene scene;

    private Stage stage;

    private FXMLGameController controller;

    private GridPane messagePane;


    public GameScene(GUI gui, Stage stage, String info) {

        this.gui = gui;
        this.stage = stage;
        this.controller = new FXMLGameController();


        Parent root = null;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader( getClass().getResource("/FXML/GameScene.fxml") );
            root = fxmlLoader.load();
            controller = fxmlLoader.getController();
            controller.initGameController(gui, info);


        } catch (IOException e) {
            e.printStackTrace();
        }

        assert root != null;
        this.scene = new Scene(root);

        ChangeListener<Number> stageSizeListener = (observable, oldValue, newValue) -> {

            System.out.println("Height: " + stage.getHeight() + " Width: " + stage.getWidth());
            //System.out.println("Height: " + fxmlGameController.getControlBoxSectionH() + " Width: " + fxmlGameController.getControlBoxSectionW());
        };

        stage.widthProperty().addListener(stageSizeListener);
        stage.heightProperty().addListener(stageSizeListener);


//        AnchorPane anchorPane = (AnchorPane) root.getChildrenUnmodifiable().get(2);
//        HBox hbox = (HBox) anchorPane.getChildren().get(0);
//        AnchorPane anchorPane1 = (AnchorPane) hbox.getChildren().get(3);
//        messagePane = (GridPane) anchorPane1.getChildren().get(0);
    }


    // MARK : Game Event Managers ======================================================================================


    public void manageEvent(GivePossibleCardsEvent event) {

    }


    public void manageEvent(AskInitPawnsEvent event) {

        controller.manageEvent(event);

    }


    public void manageEvent(GivePossibleCellsToMoveEvent event) {

        controller.manageEvent(event, gui.getRowUsedPawn(), gui.getColumnUsedPawn());

    }

    public void manageEvent(AskWhichPawnsUseEvent event, ClientView clientView) {

        Text label = new Text("Choose the pawn that you want to use");


        messagePane.add(label, 0,0);

        controller.choosePawnToUse(event.nickname, event.info, clientView);

    }


    // MARK : Supportive Methods ======================================================================================


    @Override
    public Scene getScene() {
        return this.scene;
    }



}
