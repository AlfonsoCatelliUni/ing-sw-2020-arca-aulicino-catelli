package it.polimi.ingsw.client.GUI.FXMLControllers;

import it.polimi.ingsw.client.GUI.GUI;
import it.polimi.ingsw.events.STCEvents.DisconnectionClientEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class FXMLLoseGameController {


    @FXML
    private Label loserEndGameLabel;

    @FXML
    private Label winnerLabel;

    @FXML
    private Button spectateButton;

    @FXML
    private Button leaveButton;

    /**
     * the gui where the stage is in
     */
    private GUI gui;

    /**
     * the stage of losing scene
     */
    private Stage stage;

    /**
     * the stage of the game scene, in order to close or spectate that
     */
    private Stage gameStage;


    //MARK : Initialization Methods =================================================================================


    public void initialize() { }


    public void initializeButtons() {

        spectateButton.setOnMouseClicked(mouseEvent -> {
            stage.close();
        });

        leaveButton.setOnMouseClicked(mouseEvent -> {
            gui.manageEvent(new DisconnectionClientEvent());
        });

        spectateButton.setFont(Font.loadFont(getClass().getResource("/Font/DisneyHeroic.ttf").toExternalForm(),
                10
        ));

        leaveButton.setFont(Font.loadFont(getClass().getResource("/Font/DisneyHeroic.ttf").toExternalForm(),
                10
        ));

        spectateButton.setVisible(false);
        leaveButton.setVisible(false);

        loserEndGameLabel.setFont(Font.loadFont(getClass().getResource("/Font/DisneyHeroic.ttf").toExternalForm(),
                15
        ));

        winnerLabel.setFont(Font.loadFont(getClass().getResource("/Font/DisneyHeroic.ttf").toExternalForm(),
                15
        ));

        loserEndGameLabel.setVisible(false);
        winnerLabel.setVisible(false);
    }

    /**
     * set the attributes in order to managed the events
     * @param gui the gui where sta stage is
     * @param stage the stage showed during event
     * @param gameStage the gameStage of the match
     */
    public void initLoseController(GUI gui, Stage stage, Stage gameStage) {
        this.gui = gui;
        this.stage = stage;
        this.gameStage = gameStage;

        initializeButtons();

    }

    public void initLoseEndGameController(GUI gui, Stage gameStage){

        this.gui = gui;
        this.gameStage = gameStage;

        initializeButtons();

    }


    //MARK : Supportive Methods =================================================================================


    /**
     * show the text of the scene and set visible the elements
     */
    public void showLosingByNoAction() {
        loserEndGameLabel.setText("You Have Lost the Match!");

        loserEndGameLabel.setVisible(true);
        spectateButton.setVisible(true);
        leaveButton.setVisible(true);
    }


    public void showLosingEndGame(String winnerNickname) {

        winnerLabel.setText(winnerNickname + " is the winner");
        winnerLabel.setVisible(true);
        winnerLabel.setFont(Font.loadFont(
                getClass().getResource("/Font/DisneyHeroic.ttf").toExternalForm(),
                15
        ));

        loserEndGameLabel.setText("You Have Lost the Match!");
        loserEndGameLabel.setVisible(true);
        leaveButton.setVisible(true);

    }




}
