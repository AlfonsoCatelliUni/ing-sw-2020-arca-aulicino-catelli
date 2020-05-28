package it.polimi.ingsw.client.GUI;

import it.polimi.ingsw.TheLogger;
import it.polimi.ingsw.client.GUI.FXMLControllers.FXMLDialogController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;

/**
 * Dialog manage the screen dialog of the graphics, it's used in GUI
 */
public class Dialog {

    /**
     * controller to handle FXML dialog scenes
     */
    FXMLDialogController dialogController;

    /**
     * the stage of the dialog to pop up during game
     */
    Stage dialogStage;

    /**
     * display a dialog to show simple plain text, if there is yet a dialog opened, the method fill the new text in the stage opened
     * @param plainText the text to show in dialog
     */
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
                TheLogger.LOGGER.log(Level.SEVERE, "Error while loading FXML controller in Dialog"  );
                e.printStackTrace();
            }

            assert root != null;
            Scene scene = new Scene(root);

            dialogStage.setScene(scene);
            dialogStage.setResizable(false);
            dialogStage.initModality(Modality.NONE);

            dialogStage.show();
        }
        else {
            dialogController.addText(plainText);
        }
    }

    /**
     * this method display a text and disconnect client when the button 'OK' is pressed
     * @param plainText the text showed in dialog
     */
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
                TheLogger.LOGGER.log(Level.SEVERE, "Error while loading FXML controller in Dialog"  );
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

    /**
     * this method displays a particular dialog, the exit dialog before disconnection
     * @param stage it's the current stage when the dialog is opened in order to close the stage and disconnect client
     */
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
            TheLogger.LOGGER.log(Level.SEVERE, "Error while loading FXML controller in Dialog"  );
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
