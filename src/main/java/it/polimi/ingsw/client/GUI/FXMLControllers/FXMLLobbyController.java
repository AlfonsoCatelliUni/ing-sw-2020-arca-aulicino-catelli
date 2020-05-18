package it.polimi.ingsw.client.GUI.FXMLControllers;

import it.polimi.ingsw.events.STCEvents.ClosedWaitingRoomEvent;
import it.polimi.ingsw.events.STCEvents.RoomNotFilled;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class FXMLLobbyController {

    private Stage stage;

    private List<String> connectedPlayers;

    private TimerTask timerTask;


    private List<Label> labelList;

    private List<ImageView> imageViewList;

    @FXML
    private ImageView secondImage;
    @FXML
    private ImageView thirdImage;

    @FXML
    private Label firstNicknameLabel;
    @FXML
    private Label secondNicknameLabel;
    @FXML
    private Label thirdNicknameLabel;
    @FXML
    private Label titleLabel;

    @FXML
    private Button CloseButton;



    public void initialize() {
        labelList = new ArrayList<>();
        imageViewList = new ArrayList<>();

        CloseButton.setDisable(true);
        CloseButton.setVisible(false);

        initializeBoats();
        initializeLabels();
    }


    private void initializeBoats() {

        imageViewList.add(secondImage);
        imageViewList.add(thirdImage);

        for ( ImageView image : imageViewList ) {
            image.setVisible(false);
        }

    }


    private void initializeLabels() {

        labelList.add(firstNicknameLabel);
        labelList.add(secondNicknameLabel);
        labelList.add(thirdNicknameLabel);

        for (Label label : labelList) {

            label.setText("");
            label.setVisible(false);
            /*
            label.setFont( Font.loadFont(getClass().getResource("/Font/DisneyHeroic.ttf").toExternalForm(),
                    15
            ));

             */
        }

    }


    public void initController(Stage stage, List<String> connectedPlayers) {
        this.stage = stage;
        fillNicknames(connectedPlayers);
    }


    public void close(List<String> playerNickname) {

        titleLabel.setText("The Lobby is Closed! Wait until is your turn...");

        fillNicknames(playerNickname);
    }


    private void fillNicknames(List<String> connectedPlayers) {

        this.connectedPlayers = connectedPlayers;

        firstNicknameLabel.setText(connectedPlayers.get(0));
        firstNicknameLabel.setVisible(true);

        for(int i = 1; i < connectedPlayers.size(); i++ ) {
            labelList.get(i).setText(connectedPlayers.get(i));
            labelList.get(i).setVisible(true);
            imageViewList.get(i-1).setVisible(true);
        }

    }



}
