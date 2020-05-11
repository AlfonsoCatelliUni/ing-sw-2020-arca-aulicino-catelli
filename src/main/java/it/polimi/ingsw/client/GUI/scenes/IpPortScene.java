package it.polimi.ingsw.client.GUI.scenes;

import com.sun.javafx.scene.control.IntegerField;
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

public class IpPortScene implements TheScene {

    private Scene scene;
    private Stage stage;

    private String inputIP;


public IpPortScene(GUI gui, Stage stage){

    this.stage = stage;

    GridPane connectionLayout = new GridPane();

    connectionLayout.setPadding( new Insets(20, 20, 20, 20));
    connectionLayout.setVgap(15);
    connectionLayout.setHgap(12);

    Label iPLabel = new Label("Insert IP address:");
    iPLabel.getStyleClass().add("labelWithBackground");

    GridPane.setConstraints(iPLabel, 1,8);

    TextField iPInput = new TextField();

    GridPane.setConstraints(iPInput, 2,8);


    Label portLabel = new Label("Insert PORT:");
    portLabel.getStyleClass().add("labelWithBackground");


    GridPane.setConstraints(portLabel, 1,12);

    TextField portInput = new TextField();

    GridPane.setConstraints(portInput, 2,12);



    Button connectButton = new Button("Connect");
    connectButton.setTextFill(Color.BLACK);
    GridPane.setConstraints(connectButton, 2, 15);


    this.scene = new Scene(connectionLayout, 750, 500);

    connectionLayout.getChildren().addAll(iPLabel, iPInput, portLabel, portInput, connectButton );


    connectButton.setOnAction(e -> {
        inputIP = iPInput.getText();

        if (!isValidIP(inputIP)) {
            // TODO: show to chose another valid ip
        }
    } );



}


    private boolean isValidIP(String ip) {
        // code taken from : https://stackoverflow.com/Questions/5667371/validate-ipv4-address-in-java
        String IP_PATTERN = "^((0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)\\.){3}(0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)$";

        return ip.matches(IP_PATTERN);
    }

    @Override
    public Scene getScene() {
        return this.scene;
    }
}
