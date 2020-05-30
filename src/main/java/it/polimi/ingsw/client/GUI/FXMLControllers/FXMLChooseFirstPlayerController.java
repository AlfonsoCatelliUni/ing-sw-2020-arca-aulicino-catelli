package it.polimi.ingsw.client.GUI.FXMLControllers;

import it.polimi.ingsw.events.CTSEvents.ChosenFirstPlayerEvent;
import it.polimi.ingsw.view.client.ClientView;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.List;

/**
 * controller to manage the scene of the Challenger to select the first player of the match
 */
public class FXMLChooseFirstPlayerController {

    /**
     * clientView to send the event causes by the click of the choice
     */
    private ClientView clientView;

    /**
     * nickname of the challenger that create the events of choice
     */
    private String nicknameChallenger;

    /**
     * list of player in this match to display so the challenger can choose one of them
     */
    private List<String> playersNicknames;

    /**
     * name of the card owned to the players
     */
    private List<String> cardsName;

    /**
     * description of the effect of the card
     */
    private List<String> effectsList;

    /**
     * list of imageview of the buttons to choose the first player
     */
    private List<ImageView> buttons;

    //graphic content
    private List<ImageView> buttonsPressed;

    /**
     * label of the nickname of the players in game
     */
    private List<Label> namesLabel;

    /**
     * label to display the effect of the card in scene
     */
    private List<Label> effectsLabel;

    /**
     * list of url to load the right image of the card and display in scene
     */
    private List<String> cardsUrl;

    /**
     * binding the ChooseFirstPlayerScene.fxml
     */

    @FXML
    private ImageView image0;

    @FXML
    private ImageView image0pressed;

    @FXML
    private ImageView image1;

    @FXML
    private ImageView image1pressed;

    @FXML
    private ImageView image2;

    @FXML
    private ImageView image2pressed;

    @FXML
    private Label titleLabel;

    @FXML
    private Label label0;

    @FXML
    private Label label1;

    @FXML
    private Label label2;

    @FXML
    private Label name0;

    @FXML
    private Label name1;

    @FXML
    private Label name2;

    @FXML
    private ImageView cardImage0;

    @FXML
    private ImageView cardImage1;

    @FXML
    private ImageView cardImage2;


    @FXML
    private void initialize(){

        playersNicknames = new ArrayList<>();

        cardsName = new ArrayList<>();

        effectsList = new ArrayList<>();

        buttons = new ArrayList<>();

        buttonsPressed = new ArrayList<>();

        namesLabel = new ArrayList<>();

        effectsLabel = new ArrayList<>();

        cardsUrl = new ArrayList<>();

        namesLabel.add(name0);
        namesLabel.add(name1);
        namesLabel.add(name2);

        effectsLabel.add(label0);
        effectsLabel.add(label1);
        effectsLabel.add(label2);

        cardImage0.setVisible(false);
        cardImage1.setVisible(false);
        cardImage2.setVisible(false);

        initializeButtons();

    }

    public void initializeButtons(){

        titleLabel.setFont( Font.loadFont(
                getClass().getResource("/Font/DisneyHeroic.ttf").toExternalForm(),
                18
        ));

        for (Label effect : effectsLabel){
            effect.setVisible(false);
            effect.setFont( Font.loadFont(
                    getClass().getResource("/Font/DisneyHeroic.ttf").toExternalForm(),
                    16
            ));
        }

        for (Label name : namesLabel){
            name.setVisible(false);
            name.setFont( Font.loadFont(
                    getClass().getResource("/Font/DisneyHeroic.ttf").toExternalForm(),
                    15
            ));
        }


        buttons.add(image0);
        buttons.add(image1);
        buttons.add(image2);

        buttonsPressed.add(image0pressed);
        buttonsPressed.add(image1pressed);
        buttonsPressed.add(image2pressed);

        for (ImageView image : buttons){
            image.setVisible(false);
            image.setDisable(true);
        }

        for (ImageView image : buttonsPressed){
            image.setVisible(false);
            image.setDisable(true);
        }




        //graphic content
        name0.setOnMousePressed(mouseEvent -> {

            image0pressed.setVisible(true);
            image0.setVisible(false);

        });

        name1.setOnMousePressed(mouseEvent -> {

            image1pressed.setVisible(true);
            image1.setVisible(false);

        });

        name2.setOnMousePressed(mouseEvent -> {

            image2pressed.setVisible(true);
            image2.setVisible(false);

        });

        name0.setOnMouseReleased(mouseEvent -> {

            image0pressed.setVisible(false);
            image0.setVisible(true);

        });

        name1.setOnMouseReleased(mouseEvent -> {

            image1pressed.setVisible(false);
            image1.setVisible(true);

        });

        name2.setOnMouseReleased(mouseEvent -> {

            image2pressed.setVisible(false);
            image2.setVisible(true);

        });









    }

    /**
     * set the controller to manage events
     * @param nicknameChallenger name of the challenger that see the scene
     * @param clientView clientView to send answers
     * @param playersNicknames list of nickname to choose the first that starts his turn
     * @param cardsName name of the card selected by each player
     * @param effectsList description of each card
     */
    public void setController( String nicknameChallenger, ClientView clientView, List<String> playersNicknames, List<String> cardsName, List<String> effectsList){

        String prefix = "/Graphics/Cards/";

        this.nicknameChallenger = nicknameChallenger;
        this.clientView = clientView;
        this.playersNicknames = playersNicknames;
        this.cardsName = cardsName;
        this.effectsList = effectsList;

        for (String cardName : cardsName){
            cardsUrl.add(prefix + cardName + ".png");
        }

        setButtons();


    }

    /**
     * set the buttons in order of number of players
     */
    public void setButtons(){

        switch (this.playersNicknames.size()){

            case 2:
                name0.setText(playersNicknames.get(0));
                name0.setVisible(true);
                label0.setText(cardsName.get(0) + "\n\n" + effectsList.get(0));
                cardImage0.setImage(new Image(getClass().getResourceAsStream(cardsUrl.get(0))));
                cardImage0.setVisible(true);
                label0.setVisible(true);
                image0.setVisible(true);

                name0.setOnMouseClicked(e -> {

                    disableButtons();

                    clientView.sendCTSEvent(new ChosenFirstPlayerEvent(nicknameChallenger, playersNicknames.get(0)));
                });


                name2.setText(playersNicknames.get(1));
                name2.setVisible(true);
                label2.setText(cardsName.get(1) + "\n\n" + effectsList.get(1));
                cardImage2.setImage(new Image(getClass().getResourceAsStream(cardsUrl.get(1))));
                cardImage2.setVisible(true);
                label2.setVisible(true);
                image2.setVisible(true);

                name2.setOnMouseClicked(e -> {

                    disableButtons();

                    clientView.sendCTSEvent(new ChosenFirstPlayerEvent(nicknameChallenger, playersNicknames.get(1)));

                });
                break;

            case 3:
                name0.setText(playersNicknames.get(0));
                name0.setVisible(true);
                label0.setText(cardsName.get(0) + "\n\n" + effectsList.get(0));
                cardImage0.setImage(new Image(getClass().getResourceAsStream(cardsUrl.get(0))));
                cardImage0.setVisible(true);
                label0.setVisible(true);
                image0.setVisible(true);

                name0.setOnMouseClicked(e -> {

                    disableButtons();

                    clientView.sendCTSEvent(new ChosenFirstPlayerEvent(nicknameChallenger, playersNicknames.get(0)));

                });


                name1.setText(playersNicknames.get(1));
                name1.setVisible(true);
                label1.setText(cardsName.get(1) + "\n\n" + effectsList.get(1));
                cardImage1.setImage(new Image(getClass().getResourceAsStream(cardsUrl.get(1))));
                cardImage1.setVisible(true);
                label1.setVisible(true);
                image1.setVisible(true);

                name1.setOnMouseClicked(e -> {

                    disableButtons();

                    clientView.sendCTSEvent(new ChosenFirstPlayerEvent(nicknameChallenger, playersNicknames.get(1)));

                });

                name2.setText(playersNicknames.get(2));
                name2.setVisible(true);
                label2.setText(cardsName.get(1) + "\n\n" + effectsList.get(1));
                cardImage2.setImage(new Image(getClass().getResourceAsStream(cardsUrl.get(2))));
                cardImage2.setVisible(true);
                label2.setVisible(true);
                image2.setVisible(true);

                name2.setOnMouseClicked(e -> {

                    disableButtons();

                    clientView.sendCTSEvent(new ChosenFirstPlayerEvent(nicknameChallenger, playersNicknames.get(2)));

                });
                break;




        }
    }

    /**
     * disable buttons to prevent errors
     */
    private void disableButtons(){

        name0.setOnMouseClicked(e->{});
        name1.setOnMouseClicked(e->{});
        name2.setOnMouseClicked(e->{});

        name0.setOnMousePressed(e->{});
        name1.setOnMousePressed(e->{});
        name2.setOnMousePressed(e->{});

        name0.setOnMouseReleased(e->{});
        name1.setOnMouseReleased(e->{});
        name2.setOnMouseReleased(e->{});

    }




}
