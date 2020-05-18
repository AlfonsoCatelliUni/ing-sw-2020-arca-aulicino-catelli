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


    public void initialize() {
        initializeImages();
    }


    private void initializeImages() {

        twoPlayers.setOnMouseClicked( e -> {
            gui.getClientView().sendCTSEvent(new ChosenPlayerNumberEvent(gui.getNickname(), 2));
            gui.getLobbyStage().displayWait();
            stage.close();
        });

        threePlayers.setOnMouseClicked( e -> {
            gui.getClientView().sendCTSEvent(new ChosenPlayerNumberEvent(gui.getNickname(), 3));
            gui.getLobbyStage().displayWait();
            stage.close();
        });

        twoPlayers.setOnMouseEntered(e -> {

            twoPlayers.setOpacity(0.7);

        });

        threePlayers.setOnMouseEntered(e -> {
            threePlayers.setOpacity(0.7);
        });

        twoPlayers.setOnMouseExited(e -> {

            twoPlayers.setOpacity(1);

        });

        threePlayers.setOnMouseExited(e -> {
            threePlayers.setOpacity(1);
        });

    }


    public void setController(GUI gui) {
        this.gui = gui;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
