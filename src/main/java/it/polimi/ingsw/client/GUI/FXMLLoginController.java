package it.polimi.ingsw.client.GUI;

import it.polimi.ingsw.events.CTSEvents.NewConnectionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.regex.Pattern;

public class FXMLLoginController {

    private GUI gui;

    private int id;

    @FXML
    private TextField UsernameTextField;
    @FXML
    private Label InfoLabel;
    @FXML
    private Button LoginButton;


    public void initialize() {
        initializeButton();
        initializeLabel();
        initializeTextField();
    }


    private void initializeTextField() {

        /*
        UsernameTextField.setFont( Font.loadFont(getClass().getResource("/Font/DisneyHeroic.ttf").toExternalForm(),
                15
        ));

         */
    }


    private void initializeLabel() {
        /*
        InfoLabel.setFont( Font.loadFont(getClass().getResource("/Font/DisneyHeroic.ttf").toExternalForm(),
                12
        ));

         */
    }


    private void initializeButton() {

        /*
        LoginButton.setFont( Font.loadFont(getClass().getResource("/Font/DisneyHeroic.ttf").toExternalForm(),
                        15
                ));

         */

        LoginButton.setOnMouseClicked(mouseEvent -> {
            LoginButton.setDisable(true);
            manageLogin(gui, UsernameTextField.getText(), id);
        });

    }


    public void setController(GUI gui, Stage stage, int id ) {
        this.gui = gui;
        this.id = id;
    }


    private void manageLogin(GUI gui, String nickname, int ID ) {

        String nicknamePattern = "^[aA-zZ]\\w{5,29}$";

        if (nickname.isEmpty() || !(Pattern.matches(nicknamePattern, nickname) )) {
            Dialog.display("Invalid nickname");
            LoginButton.setDisable(false);
            return;
        }

        gui.setNickname(nickname);

        gui.getClientView().sendCTSEvent(new NewConnectionEvent(ID, nickname));
    }


}
