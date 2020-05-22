package it.polimi.ingsw.client.GUI;

import it.polimi.ingsw.client.GUI.FXMLControllers.FXMLDialogController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class Dialog {

    FXMLDialogController dialogController;

    Stage dialogStage;

    public void display(String plainText) {


        if (dialogStage == null || !dialogStage.isShowing()) {

            dialogStage = new Stage();

            Parent root = null;
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/DialogModal.fxml"));
                root = fxmlLoader.load();

                dialogController = fxmlLoader.getController();
                dialogController.initController(dialogStage);
                dialogController.display(plainText);

            } catch (IOException e) {
                e.printStackTrace();
            }

            assert root != null;
            Scene scene = new Scene(root);

            dialogStage.setScene(scene);
            dialogStage.setResizable(false);
            dialogStage.initModality(Modality.APPLICATION_MODAL);

            dialogStage.show();
        }
        else {
            dialogController.addText(plainText);
        }
    }

    public void displayExit(String plainText){

        if (dialogStage == null || !dialogStage.isShowing() ) {

            dialogStage = new Stage();

            Parent root = null;
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/DialogModal.fxml"));
                root = fxmlLoader.load();

                dialogController = fxmlLoader.getController();
                dialogController.initController(dialogStage);
                dialogController.displayExit(plainText);

            } catch (IOException e) {
                e.printStackTrace();
            }

            assert root != null;
            Scene scene = new Scene(root);

            dialogStage.setScene(scene);
            dialogStage.setResizable(false);
            dialogStage.initModality(Modality.APPLICATION_MODAL);

            dialogStage.showAndWait();
        }
        else {
            dialogController.displayExit(plainText);
        }
    }

    public void displayClose(Stage stage){

        dialogStage = new Stage();

        Parent root = null;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader( getClass().getResource("/FXML/DialogModal.fxml") );
            root = fxmlLoader.load();

            dialogController = fxmlLoader.getController();
            dialogController.initController(dialogStage);
            dialogController.displayClose(stage);

        } catch (IOException e) {
            e.printStackTrace();
        }

        assert root != null;
        Scene scene = new Scene(root);

        dialogStage.setScene(scene);
        dialogStage.setResizable(false);
        dialogStage.initModality(Modality.APPLICATION_MODAL);

        dialogStage.showAndWait();

    }



}
