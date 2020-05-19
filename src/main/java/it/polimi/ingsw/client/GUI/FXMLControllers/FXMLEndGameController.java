package it.polimi.ingsw.client.GUI.FXMLControllers;

import it.polimi.ingsw.client.GUI.GUI;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class FXMLEndGameController {

    private GUI gui;

    private Stage stage;

    @FXML
    private Label winnerLabel;

    @FXML
    private Label exitLabel;


    public void initialize(){
        
        //initialize exitButton
        exitLabel.setOnMouseClicked(e -> stage.close() );

    }

    public void initController(Stage stage, String Winnernickname){
        this.stage = stage;

        winnerLabel.setText(Winnernickname);

    }




}
