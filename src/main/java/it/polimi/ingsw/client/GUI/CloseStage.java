package it.polimi.ingsw.client.GUI;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class CloseStage {

    public static void  display(Stage stage){

        Stage closeStage = new Stage();

        closeStage.initModality(Modality.APPLICATION_MODAL);

        stage.setTitle("Closing");
        stage.setMinHeight(300);
        stage.setMinWidth(300);

        Button yesButton = new Button("YES");
        yesButton.setMaxSize(50, 30);

        Button noButton = new Button("NO");
        noButton.setMaxSize(50, 30);


        Label text = new Label("Are you sure you want to exit?");
        text.setStyle("-fx-font-size: 20;-fx-background-color: orange");

        yesButton.setOnAction(e ->{
            closeStage.close();
            stage.close();
            System.exit(0);
        });

        noButton.setOnAction(e -> closeStage.close());

        VBox vBox = new VBox(20);
        vBox.setStyle("-fx-background-color: #ac0010");

        vBox.getChildren().addAll(text, yesButton, noButton);

        vBox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vBox, 400,400);

        closeStage.setScene(scene);
        closeStage.showAndWait();




    }
}
