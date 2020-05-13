package it.polimi.ingsw.client.GUI.scenes;


import it.polimi.ingsw.client.GUI.CloseStage;
import it.polimi.ingsw.client.GUI.Dialog;
import it.polimi.ingsw.client.GUI.GUI;
import it.polimi.ingsw.view.client.ClientView;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;


public class IpPortScene implements TheScene {

    private Scene scene;
    private Stage stage;

    private String inputIP;
    private int inputPort;


public IpPortScene(GUI gui, Stage stage){

    this.stage = stage;

    GridPane connectionLayout = new GridPane();

    connectionLayout.setPadding( new Insets(20, 20, 20, 20));
    connectionLayout.setVgap(15);
    connectionLayout.setHgap(12);

    Label iPLabel = new Label("Insert IP address:");
    iPLabel.getStyleClass().add("labelWithBackground");

    GridPane.setConstraints(iPLabel, 1,8);

    TextField iPInput = new TextField("127.0.0.1");

    GridPane.setConstraints(iPInput, 2,8);


    Label portLabel = new Label("Insert PORT:");
    portLabel.getStyleClass().add("labelWithBackground");


    GridPane.setConstraints(portLabel, 1,12);

    TextField portInput = new TextField("64209");

    GridPane.setConstraints(portInput, 2,12);



    Button exitButton = new Button("EXIT");
    exitButton.setOnAction(a-> CloseStage.display(stage));
    exitButton.setTextFill(Color.BLACK);
    GridPane.setConstraints(exitButton, 2, 19 );


    Button connectButton = new Button("Connect");
    connectButton.setTextFill(Color.BLACK);
    GridPane.setConstraints(connectButton, 2, 15);


    this.scene = new Scene(connectionLayout, 750, 500);

    connectionLayout.getChildren().addAll(iPLabel, iPInput, portLabel, portInput, connectButton, exitButton);


    connectButton.setOnAction(e -> {

        inputIP = iPInput.getText();

        String possiblePort = portInput.getText();

        if (!isValidIP(inputIP)) {
            Dialog.display("Enter a valid IP address");
            return;
        }

        if (!isValidPort(possiblePort)) {
            Dialog.display("Enter a valid port number");
            return;
        }

        inputPort = Integer.parseInt(possiblePort);

        new Thread(() -> startConnection(gui, inputIP, inputPort) ).start();

    } );



}

    private void startConnection(GUI gui, String ipAddress, int port) {

        Socket serverSocket = null;
        try {
            //Connects with the server through socket
            serverSocket = new Socket(ipAddress, port);
        }
        catch (IOException e) {
            System.err.println(e.getMessage());
        }

        //Creates a new RemoteViewSocket object which is used to keep the connection open and read all new messages
        assert serverSocket != null;
        ClientView clientSocket = new ClientView(serverSocket, gui);

        gui.setClientView(clientSocket);

        new Thread(clientSocket).start();
        System.out.println("Connection established!");

    }


    private boolean isValidIP(String ip) {
        // code taken from : https://stackoverflow.com/Questions/5667371/validate-ipv4-address-in-java
        String IP_PATTERN = "^((0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)\\.){3}(0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)$";

        return ip.matches(IP_PATTERN);
    }


    private boolean isValidPort(String port) {

        boolean isValid = false;

        try {
            int possiblePort = Integer.parseInt(port);

            if (possiblePort >= 49152 && possiblePort < 65536)
                isValid = true;

        } catch (NumberFormatException e) {
            isValid = false;
        }

        return isValid;
    }


    @Override
    public Scene getScene() {
        return this.scene;
    }


}
