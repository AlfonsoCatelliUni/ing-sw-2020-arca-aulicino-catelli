package it.polimi.ingsw.client.GUI.FXMLControllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;


public class FXMLDialogController {


    Stage dialogStage;


    @FXML
    private Label plainTextLabel;

    @FXML
    private ImageView okButton;

    @FXML
    private Label okLabel;

    @FXML
    private ImageView yesButton;

    @FXML
    private ImageView noButton;

    @FXML
    private Label noLabel;

    @FXML
    private Label yesLabel;

    @FXML
    private ImageView noButtonPressed;

    @FXML
    private ImageView yesButtonPressed;

    @FXML
    private ImageView okButtonPressed;



    @FXML
    private void initialize(){

        okLabel.setVisible(false);
        okButton.setVisible(false);

        yesButton.setVisible(false);
        yesLabel.setVisible(false);

        noButton.setVisible(false);
        noLabel.setVisible(false);

        noButtonPressed.setVisible(false);
        yesButtonPressed.setVisible(false);
        okButtonPressed.setVisible(false);

    }

    public void initController(Stage stage){
        dialogStage = stage;

    }

    public void display(String plainText){

        okButton.setVisible(true);
        okLabel.setVisible(true);

        plainTextLabel.setText(plainText);

        okLabel.setOnMouseClicked(e->{
            dialogStage.close();
        });

        okLabel.setOnMousePressed(mouseEvent -> {

            okButtonPressed.setVisible(true);
            okButton.setVisible(false);

        });

        okLabel.setOnMouseReleased(mouseEvent -> {

            okButton.setVisible(true);
            okButtonPressed.setVisible(false);

        });
    }

    public void displayExit(String plainText){

        String previousText = plainTextLabel.getText();

        if (previousText != null){
            plainTextLabel.setText(previousText + "\n" + plainText);
        }
        else {
            plainTextLabel.setText(plainText);
        }

        okButton.setVisible(true);
        okLabel.setVisible(true);



        okLabel.setOnMouseClicked(e->{
            dialogStage.close();
            System.exit(0);
        });

        okLabel.setOnMousePressed(mouseEvent -> {

            okButtonPressed.setVisible(true);
            okButton.setVisible(false);

        });

        okLabel.setOnMouseReleased(mouseEvent -> {

            okButton.setVisible(true);
            okButtonPressed.setVisible(false);

        });

    }

    public void displayClose(Stage stage){

        yesLabel.setVisible(true);
        yesButton.setVisible(true);

        noLabel.setVisible(true);
        noButton.setVisible(true);

        plainTextLabel.setText("Are you sure you want to exit?");

        yesLabel.setOnMouseClicked(e ->{
            dialogStage.close();
            stage.close();
            System.exit(0);
        });

        yesLabel.setOnMousePressed(mouseEvent -> {

            yesButtonPressed.setVisible(true);
            yesButton.setVisible(false);

        });

        yesLabel.setOnMouseReleased(mouseEvent -> {

            yesButton.setVisible(true);
            yesButtonPressed.setVisible(false);

        });

        noLabel.setOnMouseClicked(e ->{
            dialogStage.close();
        });

        noLabel.setOnMousePressed(mouseEvent -> {

            noButtonPressed.setVisible(true);
            noButton.setVisible(false);

        });

        noLabel.setOnMouseReleased(mouseEvent -> {

            noButton.setVisible(true);
            noButtonPressed.setVisible(false);

        });
    }

    public void addText(String plainText){
        String previousText = plainTextLabel.getText();

        plainTextLabel.setText(previousText + "\n" + plainText);

    }











}
