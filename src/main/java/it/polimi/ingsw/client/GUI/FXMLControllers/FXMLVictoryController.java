package it.polimi.ingsw.client.GUI.FXMLControllers;

import it.polimi.ingsw.client.GUI.GUI;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class FXMLVictoryController {

    private GUI gui;

    private Stage stage;

    @FXML
    private Label winnerLabel;

    @FXML
    private Label exitLabel;


    //MARK : Initialization Methods =================================================================================


    public void initialize(){
        
        //initialize exitButton
        exitLabel.setOnMouseClicked(e -> stage.close() );

    }


    public void initController(Stage stage, String winnerNickname){
        this.stage = stage;
        winnerLabel.setText(winnerNickname);

    }




}
