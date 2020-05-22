package it.polimi.ingsw.client.GUI.FXMLControllers;


import it.polimi.ingsw.client.GUI.Dialog;
import it.polimi.ingsw.client.GUI.GUI;
import it.polimi.ingsw.view.client.ClientView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class FXMLIpController {

    private GUI gui;

    private Stage stage;

    @FXML
    private TextField IPTextField;
    @FXML
    private TextField PortTextField;
    @FXML
    private Button ConnectButton;

    @FXML
    private ImageView exitButtonPressed;

    @FXML
    private ImageView exitButton;

    @FXML
    private Label exitLabel;


    // MARK : Initialize Methods ======================================================================================


    public void initialize() {
        initializeButton();
        initializeTextField();
    }


    private void initializeButton() {

        /*
        ConnectButton.setFont(Font.loadFont(getClass().getResource("/Font/DisneyHeroic.ttf").toExternalForm(),
                15
        ));

         */

        //ConnectButton.setDefaultButton(true);

        exitButton.setVisible(true);
        exitButtonPressed.setVisible(false);

        ConnectButton.setOnMouseClicked(mouseEvent -> {

            ConnectButton.setDisable(true);

            String ip = IPTextField.getText();
            String port = PortTextField.getText();

            if (!isValidIP(ip)) {
                gui.getDialog().display("Enter a valid IP address");
                ConnectButton.setDisable(false);
                return;
            }

            if (!isValidPort(port)) {
                gui.getDialog().display("Enter a valid port number");
                ConnectButton.setDisable(false);
                return;
            }

            int portNumber = Integer.parseInt(port);

            new Thread( () -> startConnection(gui, ip, portNumber) ).start();

        });


        //Just to catch the enter key
        IPTextField.setOnKeyPressed(keyEvent -> {

            if (keyEvent.getCode().equals(KeyCode.ENTER)) {

                String ip = IPTextField.getText();
                String port = PortTextField.getText();

                if (!isValidIP(ip)) {
                    gui.getDialog().display("Enter a valid IP address");
                    ConnectButton.setDisable(false);
                    return;
                }

                if (!isValidPort(port)) {
                    gui.getDialog().display("Enter a valid port number");
                    ConnectButton.setDisable(false);
                    return;
                }

                int portNumber = Integer.parseInt(port);

                new Thread( () -> startConnection(gui, ip, portNumber) ).start();


            }

        });


        PortTextField.setOnKeyPressed(keyEvent -> {

            if (keyEvent.getCode().equals(KeyCode.ENTER)) {

                String ip = IPTextField.getText();
                String port = PortTextField.getText();

                if (!isValidIP(ip)) {
                    gui.getDialog().display("Enter a valid IP address");
                    ConnectButton.setDisable(false);
                    return;
                }

                if (!isValidPort(port)) {
                    gui.getDialog().display("Enter a valid port number");
                    ConnectButton.setDisable(false);
                    return;
                }

                int portNumber = Integer.parseInt(port);

                new Thread( () -> startConnection(gui, ip, portNumber) ).start();


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


    private void initializeTextField() {

        IPTextField.setText("127.0.0.1");
        PortTextField.setText("64209");

        /*
        IPTextField.setFont(Font.loadFont(getClass().getResource("/Font/DisneyHeroic.ttf").toExternalForm(),
                12
        ));

        PortTextField.setFont(Font.loadFont(getClass().getResource("/Font/DisneyHeroic.ttf").toExternalForm(),
                12
        ));

         */
    }


    public void setController( GUI gui, Stage stage ) {

        this.stage = stage;
        this.gui = gui;
    }


    // MARK : Main Methods ======================================================================================


    private void startConnection(GUI gui, String ipAddress, int port) {

        Socket serverSocket = null;
        try {
            //Connects with the server through socket
            serverSocket = new Socket();
            serverSocket.connect( new InetSocketAddress(ipAddress, port), 5000);
        }
        catch (IOException e ) {
            ConnectButton.setDisable(false);
            return;
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

        } catch (NumberFormatException ignored) {
        }

        return isValid;
    }


}
