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
    private Label winnerEndGameLabel;

    @FXML
    private Label playAgainLabel;

    @FXML
    private Label payPalDonationsLabel;

    @FXML
    private Label loserNoActionLabel;

    @FXML
    private Button spectateButton;

    @FXML
    private Button leaveButton;

    @FXML
    private Label loserNameLabel;


    private GUI gui;

    private Stage stage;

    private Stage gameStage;


    public void initialize() {
        initializeButtons();
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

    }


    public void initLoseController(GUI gui, Stage stage, Stage gameStage) {

        this.gui = gui;
        this.stage = stage;
        this.gameStage = gameStage;

    }

}
