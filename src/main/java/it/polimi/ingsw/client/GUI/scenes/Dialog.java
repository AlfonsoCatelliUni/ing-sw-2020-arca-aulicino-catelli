package it.polimi.ingsw.client.GUI.scenes;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Dialog {

    public static void display (String plainText){

        Stage stage = new Stage();

        //creates a modal window that blocks events from being delivered to any other application window.
        stage.initModality(Modality.APPLICATION_MODAL);

        stage.setMinWidth(400);
        stage.setMinHeight(300);

        Label label = new Label(plainText);

        label.setStyle("-fx-text-fill: #E94B2B");

        VBox vBox = new VBox(10);

        Button button = new Button("OK");
        button.setStyle("-fx-background-color: #E94B2B; -fx-text-fill: linear-gradient(from 25% 25% to 100% 100%, #300900, #7F1600);");
        button.setMaxSize(40, 30);

        button.setOnAction(e -> stage.close());

        vBox.setStyle("-fx-background-color: #200500");

        vBox.getChildren().addAll(label, button);

        vBox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vBox);
        stage.setScene(scene);
        //block the temporary current processing, show and wait to be closed
        stage.showAndWait();


    }

}
