package it.polimi.ingsw.client.GUI.scenes;

import it.polimi.ingsw.client.GUI.Dialog;
import it.polimi.ingsw.client.GUI.GUI;
import it.polimi.ingsw.events.CTSEvents.NewConnectionEvent;
import javafx.geometry.Insets;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.regex.Pattern;

public class LoginScene implements TheScene {

    private Scene scene;

    private Stage stage;



    public LoginScene(GUI gui, Stage stage, int ID){

        this.stage = stage;

        GridPane loginLayout = new GridPane();

        loginLayout.setPadding( new Insets(20, 20, 20, 20));
        loginLayout.setVgap(15);
        loginLayout.setHgap(12);

        Label usernameLabel = new Label("Username:");
        usernameLabel.getStyleClass().add("labelWithBackground");
        usernameLabel.setStyle("-fx-font-size: 18px; -fx-text-fill:white");

        GridPane.setConstraints( usernameLabel, 1,8);

        TextField nicknameInput = new TextField();
        nicknameInput.setPromptText("Username");

        GridPane.setConstraints(nicknameInput, 2,8);

        Button loginButton = new Button("Login");
        loginButton.setTextFill(Color.BLACK);
        GridPane.setConstraints(loginButton, 2, 15);

        loginLayout.getChildren().addAll(usernameLabel, nicknameInput, loginButton);

        scene = new Scene(loginLayout, 750, 500);


        Image backgroundImage = new Image(gui.getClass().getResourceAsStream("/Graphics/SceneBackground/loginBackground.png"));

        Background background = new Background(
                new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                        new BackgroundSize(scene.getWidth(), scene.getHeight(),
                                true, true, true, true)));

        loginLayout.setBackground(background);


        loginButton.setOnAction(e -> {
            manageLogin(gui, nicknameInput.getText(), ID);
        });



    }


    private void manageLogin(GUI gui, String nickname, int ID ) {

        String nicknamePattern = "^[aA-zZ]\\w{5,29}$";

        if (nickname.isEmpty() || !(Pattern.matches(nicknamePattern, nickname) )) {
            Dialog.display("Invalid nickname");
        }

        gui.setNickname(nickname);

        gui.getClientView().sendCTSEvent(new NewConnectionEvent(ID, nickname));


    }

    @Override
    public Scene getScene() {
        return this.scene;
    }
}
