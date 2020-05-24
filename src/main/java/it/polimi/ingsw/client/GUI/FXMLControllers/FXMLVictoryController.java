package it.polimi.ingsw.client.GUI.FXMLControllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class FXMLVictoryController {

    private Stage stage;

    /**
     * bindings to VictoryScene.fxml
     */
    @FXML
    private Label winnerLabel;

    @FXML
    private Label exitLabel;


    //MARK : Initialization Methods =================================================================================


    public void initialize(){
        
        //initialize exitButton
        exitLabel.setOnMouseClicked(e -> {
            stage.close();
            System.exit(0);

    });

        exitLabel.setFont(Font.loadFont(getClass().getResource("/Font/DisneyHeroic.ttf").toExternalForm(),
                15
        ));

        winnerLabel.setFont(Font.loadFont(getClass().getResource("/Font/DisneyHeroic.ttf").toExternalForm(),
                15
        ));
    }


    public void initController(Stage stage, String winnerNickname){
        this.stage = stage;
        winnerLabel.setText(winnerNickname);

    }




}
