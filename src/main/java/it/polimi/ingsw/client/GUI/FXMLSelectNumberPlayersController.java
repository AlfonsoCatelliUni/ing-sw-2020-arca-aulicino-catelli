package it.polimi.ingsw.client.GUI;

import it.polimi.ingsw.events.CTSEvents.ChosenPlayerNumberEvent;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class FXMLSelectNumberPlayersController {


    private GUI gui;

    private Stage stage;

    @FXML
    private ImageView twoPlayers;

    @FXML
    private ImageView threePlayers;


    private void initialize() {
        initializeImages();
    }

    private void initializeImages() {

        twoPlayers.setOnMouseClicked( e -> {
            gui.getClientView().sendCTSEvent(new ChosenPlayerNumberEvent(gui.getNickname(), 2));
            gui.getLobbyStage().displayWait();
            stage.close();
//            Label text = new Label("Wait Until the lobby is full");
//            text.getStyleClass().add("labelWithBackground");
//            text.setStyle("-fx-font-size: 18px");
//            numberPlayersLayout.getChildren().removeAll(numberLabel, twoButton, threeButton);
//            numberPlayersLayout.getChildren().add(text);


        });

        threePlayers.setOnMouseClicked( e -> {
            gui.getClientView().sendCTSEvent(new ChosenPlayerNumberEvent(gui.getNickname(), 3));
            gui.getLobbyStage().displayWait();
            stage.close();



//            Label text = new Label("Wait Until the lobby is full");
//            numberPlayersLayout.getChildren().add(text);
//            numberPlayersLayout.getChildren().removeAll(numberLabel, twoButton, threeButton);

        });

    }

    public void setController(GUI gui) {
        this.gui = gui;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
