package it.polimi.ingsw.client.GUI;

import it.polimi.ingsw.client.Client;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class GUI extends Application implements Client {


    public GUI() {

    }


    // ======================================================================================


    @Override
    public void run() {
        launch();
    }

    @Override
    public void start(Stage stage) {
        var label = new Label("JavaFX + MVC ");
        var scene = new Scene(new StackPane(label), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }


}
