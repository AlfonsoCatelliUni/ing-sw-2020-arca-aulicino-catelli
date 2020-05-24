package it.polimi.ingsw.client.GUI.FXMLControllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
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

        plainTextLabel.setFont( Font.loadFont(getClass().getResource("/Font/DisneyHeroic.ttf").toExternalForm(),
                15
        ));

    }


    public void initController(Stage stage){

        dialogStage = stage;

    }

    /**
     * this method fill the text in the scene and shows all the elements needed to show the stage correctly
     * @param plainText the text to show
     */
    public void display(String plainText){

        okButton.setVisible(true);
        okLabel.setVisible(true);

        plainTextLabel.setText(plainText);

        setAttributes(okLabel, okButtonPressed, okButton);
    }

    /**
     * this method fill the text in the scene and shows all the elements needed to show the stage correctly like the button to close connection
     * @param plainText the text to show
     */
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

    /**
     * this method fill the text in the scene and shows all the elements needed to show the stage correctly like the button to close the  currentstage
     * @param stage the current stage to close
     */
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

        setAttributes(noLabel, noButtonPressed, noButton);
    }

    /**
     * this method fill the previous text with new text received during game
     * @param plainText the text to add in dialog stage
     */
    public void addText(String plainText){
        String previousText = plainTextLabel.getText();

        plainTextLabel.setText(previousText + "\n" + plainText);

    }

    /**
     * this method set the correct attributes of the Images, Buttons and Label to show the right dialog stage
     * @param label the label to click and close the stage
     * @param buttonPressed the image to show when click
     * @param button the image to show when button is not clicked
     */
    private void setAttributes(Label label, ImageView buttonPressed, ImageView button) {

        label.setOnMouseClicked(e->{
            dialogStage.close();
        });

        label.setOnMousePressed(mouseEvent -> {

            buttonPressed.setVisible(true);
            button.setVisible(false);

        });

        label.setOnMouseReleased(mouseEvent -> {

            button.setVisible(true);
            buttonPressed.setVisible(false);

        });
    }











}
