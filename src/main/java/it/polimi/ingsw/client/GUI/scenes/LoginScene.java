package it.polimi.ingsw.client.GUI.scenes;

import it.polimi.ingsw.client.GUI.GUI;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.regex.Pattern;

public class LoginScene implements TheScene {

    private Scene scene;
    private Stage stage;

    private String username;

    private final String nicknamePattern = "^[aA-zZ]\\w{5,29}$";


    public LoginScene(GUI gui, Stage stage){

        this.stage = stage;

        GridPane loginLayout = new GridPane();

        loginLayout.setPadding( new Insets(20, 20, 20, 20));
        loginLayout.setVgap(15);
        loginLayout.setHgap(12);

        Label usernameLabel = new Label("Username:");
        usernameLabel.getStyleClass().add("labelWithBackground");
        usernameLabel.setStyle("-fx-font-size: 18px");

        GridPane.setConstraints( usernameLabel, 1,8);

        TextField nicknameInput = new TextField();

        GridPane.setConstraints(nicknameInput, 2,8);

        Button loginButton = new Button("Login");
        loginButton.setTextFill(Color.BLACK);
        GridPane.setConstraints(loginButton, 2, 15);

        loginLayout.getChildren().addAll(usernameLabel, nicknameInput, loginButton);

        scene = new Scene(loginLayout, 750, 500);

        loginButton.setOnAction(e -> {
            manageLogin(gui, nicknameInput.getText());
        });


    }


    private void manageLogin(GUI gui, String nickname ) {

        String nicknamePattern = "^[aA-zZ]\\w{5,29}$";

        if (username.isEmpty() || (Pattern.matches(nicknamePattern, nickname) )) {
            //TODO: invalid nickname
        }

        gui.setNickname(nickname);

    }

    @Override
    public Scene getScene() {
        return this.scene;
    }
}
