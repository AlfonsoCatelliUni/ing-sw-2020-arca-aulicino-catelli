package it.polimi.ingsw.client.GUI.FXMLControllers;

import it.polimi.ingsw.client.GUI.Dialog;
import it.polimi.ingsw.client.GUI.GUI;
import it.polimi.ingsw.events.CTSEvents.NewConnectionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.regex.Pattern;

public class FXMLLoginController {

    private Stage stage;

    private GUI gui;

    /**
     * id that the server gave to this player
     */
    private int id;

    /**
     * bindings to LoginScene.fxml
     */
    @FXML
    private TextField UsernameTextField;

    @FXML
    private Label InfoLabel;

    @FXML
    private Button LoginButton;

    @FXML
    private ImageView exitButtonPressed;

    @FXML
    private ImageView exitButton;

    @FXML
    private Label exitLabel;


    public void initialize() {
        initializeButton();
        initializeLabel();
        initializeTextField();
    }


    private void initializeTextField() {

        UsernameTextField.setFont( Font.loadFont(getClass().getResource("/Font/DisneyHeroic.ttf").toExternalForm(),
                15
        ));


    }


    private void initializeLabel() {

        InfoLabel.setFont( Font.loadFont(getClass().getResource("/Font/DisneyHeroic.ttf").toExternalForm(),
                12
        ));


    }


    private void initializeButton() {


        LoginButton.setFont( Font.loadFont(getClass().getResource("/Font/DisneyHeroic.ttf").toExternalForm(),
                        15
                ));



        //LoginButton.setDefaultButton(true);

        LoginButton.setOnMouseClicked(mouseEvent -> {
            LoginButton.setDisable(true);
            manageLogin(gui, UsernameTextField.getText(), id);
        });



        //just to catch the enter key
        UsernameTextField.setOnKeyPressed(keyEvent -> {

            if(keyEvent.getCode().equals(KeyCode.ENTER)) {

                LoginButton.setDisable(true);
                manageLogin(gui, UsernameTextField.getText(), id);

            }

        });

        exitLabel.setOnMouseClicked(mouseEvent -> {

            gui.getDialog().displayClose(stage);

        });

        exitLabel.setOnMousePressed(mouseEvent -> {

            exitButtonPressed.setVisible(true);
            exitButton.setVisible(false);

        });

        exitLabel.setOnMouseReleased(mouseEvent -> {

            exitButtonPressed.setVisible(false);
            exitButton.setVisible(true);

        });



    }


    public void initLoginController(GUI gui, Stage stage, int id ) {
        this.gui = gui;
        this.id = id;
        this.stage = stage;
    }


    private void manageLogin(GUI gui, String nickname, int ID ) {

        String nicknamePattern = "^[aA-zZ]\\w{5,29}$";

        if (nickname.isEmpty() || !(Pattern.matches(nicknamePattern, nickname) )) {
            gui.getDialog().display("Invalid nickname");
            LoginButton.setDisable(false);
            return;
        }

        gui.setNickname(nickname);

        gui.getClientView().sendCTSEvent(new NewConnectionEvent(ID, nickname));

    }


}
