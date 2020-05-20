package it.polimi.ingsw.client.GUI.FXMLControllers;

import it.polimi.ingsw.client.GUI.GUI;
import it.polimi.ingsw.events.STCEvents.DisconnectionClientEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class FXMLLoseGameController {


    @FXML
    private Label loserEndGameLabel;

    @FXML
    private Button spectateButton;
    @FXML
    private Button leaveButton;


    private GUI gui;

    private Stage stage;

    private Stage gameStage;


    //MARK : Initialization Methods =================================================================================


    public void initialize() {
        //initializeButtons();
    }


    public void initializeButtons() {

        spectateButton = new Button();
        leaveButton = new Button();

        spectateButton.setOnMouseClicked(mouseEvent -> {
            stage.close();
        });

        leaveButton.setOnMouseClicked(mouseEvent -> {
            gui.manageEvent(new DisconnectionClientEvent());
            stage.close();
            gameStage.close();
        });

        spectateButton.setVisible(false);
        leaveButton.setVisible(false);
        loserEndGameLabel.setVisible(false);
    }


    public void initLoseController(GUI gui, Stage stage, Stage gameStage) {
        this.gui = gui;
        this.stage = stage;
        this.gameStage = gameStage;

        initializeButtons();

    }


    //MARK : Supportive Methods =================================================================================


    public void showLosingByNoAction() {
        loserEndGameLabel.setText("You Have Lost the Match!");
        spectateButton.setVisible(true);
        leaveButton.setVisible(true);
    }


    public void showLosingEndGame(String winnerNickname) {
        //winnerEndGameLabel.setText(winnerNickname + "is the winner btw, NOOB!");
        loserEndGameLabel.setVisible(true);
    }




}
