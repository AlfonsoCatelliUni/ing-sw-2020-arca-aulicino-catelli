package it.polimi.ingsw.client.GUI.FXMLControllers;

import it.polimi.ingsw.events.CTSEvents.ChosenCardsChallengerEvent;
import it.polimi.ingsw.view.client.ClientView;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

/**
 * this controller manages the event of the challenger to choose the number of cards in order to the number of players in game,
 * cards are selected between the 14 gods of the game
 */
public class FXMLAllCardsController {

    /**
     * clientView to send the event causes by the click of the choice
     */
    private ClientView clientView;

    /**
     * nickname of the challenger that create the events of choice
     */
    private String nicknameChallenger;

    /**
     * url to load the image of the 14 cards
     */
    private List<String> cardsUrl;

    /**
     * description of the cards
     */
    private List<String> effectsList;

    /**
     * name of the cards
     */
    private List<String> cardsName;

    /**
     * list of label to manage easier graphic in scene
     */
    private List<Label> labelList;

    /**
     * list of imageView to load the image card and set properties in the right position
     */
    private List<ImageView> cardsList;

    /**
     * number of players uses to check the number of cards selected
     */
    private int numberOfPlayers;

    /**
     * list to store the choice of the challenger and finally sent the answer
     */
    private List<String> selectedCards;


    /**
     * binding the allCardSceneScene.fxml
     */

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private Label effect0;

    @FXML
    private Label effect1;

    @FXML
    private Label effect2;

    @FXML
    private Label effect3;

    @FXML
    private Label effect4;

    @FXML
    private Label effect5;

    @FXML
    private Label effect6;

    @FXML
    private Label effect7;

    @FXML
    private Label effect8;

    @FXML
    private Label effect9;

    @FXML
    private Label effect10;

    @FXML
    private Label effect11;

    @FXML
    private Label effect12;

    @FXML
    private Label effect13;

    @FXML
    private Label effect14;



    @FXML
    private ImageView card0;

    @FXML
    private ImageView card1;

    @FXML
    private ImageView card2;

    @FXML
    private ImageView card3;

    @FXML
    private ImageView card4;

    @FXML
    private ImageView card5;

    @FXML
    private ImageView card6;

    @FXML
    private ImageView card7;

    @FXML
    private ImageView card8;

    @FXML
    private ImageView card9;

    @FXML
    private ImageView card10;

    @FXML
    private ImageView card11;

    @FXML
    private ImageView card12;

    @FXML
    private ImageView card13;

    @FXML
    private ImageView card14;

    @FXML
    private void initialize() {

        this.cardsList = new ArrayList<>();
        this.labelList = new ArrayList<>();
        cardsUrl = new ArrayList<>();
        selectedCards = new ArrayList<>();

        initializeCards();
        initializeLabels();

    }

    public void setController( String nickname, ClientView clientView, List<String> cardsName, List<String> effectsList, int numberOfPlayers) {

        String prefix = "/Graphics/Cards/";

        this.clientView = clientView;
        this.cardsName = cardsName;
        this.effectsList = effectsList;
        this.nicknameChallenger = nickname;
        this.numberOfPlayers = numberOfPlayers;

        this.cardsUrl = new ArrayList<>();

        for (String cardName : cardsName) {
            cardsUrl.add(prefix + cardName + ".png");
        }
    }

    /**
     * add the card to the list to handle the initialization easier
     */
    private void initializeCards() {

        cardsList.add(card0);
        cardsList.add(card1);
        cardsList.add(card2);
        cardsList.add(card3);
        cardsList.add(card4);
        cardsList.add(card5);
        cardsList.add(card6);
        cardsList.add(card7);
        cardsList.add(card8);
        cardsList.add(card9);
        cardsList.add(card10);
        cardsList.add(card11);
        cardsList.add(card12);
        cardsList.add(card13);

        for( ImageView iw : cardsList  ) {
            iw.setVisible(true);
        }

    }

    /**
     * add the labels of effect card to the list to handle the initialization easier
     */
    private void initializeLabels() {


        labelList.add(effect0);
        labelList.add(effect1);
        labelList.add(effect2);
        labelList.add(effect3);
        labelList.add(effect4);
        labelList.add(effect5);
        labelList.add(effect6);
        labelList.add(effect7);
        labelList.add(effect8);
        labelList.add(effect9);
        labelList.add(effect10);
        labelList.add(effect11);
        labelList.add(effect12);
        labelList.add(effect13);

        for (Label label : labelList) {
            label.setVisible(true);

            label.setStyle("-fx-text-fill: black");

            label.setFont(Font.loadFont(
                    getClass().getResource("/Font/DisneyHeroic.ttf").toExternalForm(),
                    12

            ));


        }

    }

    public void setCards() {

        card0.setImage( new Image(getClass().getResourceAsStream(cardsUrl.get(0))) );
        card1.setImage( new Image(getClass().getResourceAsStream(cardsUrl.get(1))) );
        card2.setImage( new Image(getClass().getResourceAsStream(cardsUrl.get(2))) );
        card3.setImage( new Image(getClass().getResourceAsStream(cardsUrl.get(3))) );
        card4.setImage( new Image(getClass().getResourceAsStream(cardsUrl.get(4))) );
        card5.setImage( new Image(getClass().getResourceAsStream(cardsUrl.get(5))) );
        card6.setImage( new Image(getClass().getResourceAsStream(cardsUrl.get(6))) );
        card7.setImage( new Image(getClass().getResourceAsStream(cardsUrl.get(7))) );
        card8.setImage( new Image(getClass().getResourceAsStream(cardsUrl.get(8))) );
        card9.setImage( new Image(getClass().getResourceAsStream(cardsUrl.get(9))) );
        card10.setImage( new Image(getClass().getResourceAsStream(cardsUrl.get(10))) );
        card11.setImage( new Image(getClass().getResourceAsStream(cardsUrl.get(11))) );
        card12.setImage( new Image(getClass().getResourceAsStream(cardsUrl.get(12))) );
        card13.setImage( new Image(getClass().getResourceAsStream(cardsUrl.get(13))) );

        for (ImageView card : cardsList){
            card.setOnMouseClicked(this::storeCard);
        }


        card0.setUserData(cardsName.get(0));
        card1.setUserData(cardsName.get(1));
        card2.setUserData(cardsName.get(2));
        card3.setUserData(cardsName.get(3));
        card4.setUserData(cardsName.get(4));
        card5.setUserData(cardsName.get(5));
        card6.setUserData(cardsName.get(6));
        card7.setUserData(cardsName.get(7));
        card8.setUserData(cardsName.get(8));
        card9.setUserData(cardsName.get(9));
        card10.setUserData(cardsName.get(10));
        card11.setUserData(cardsName.get(11));
        card12.setUserData(cardsName.get(12));
        card13.setUserData(cardsName.get(13));


        effect0.setText( effectsList.get(0) );
        effect1.setText( effectsList.get(1) );
        effect2.setText( effectsList.get(2) );
        effect3.setText( effectsList.get(3) );
        effect4.setText( effectsList.get(4) );
        effect5.setText( effectsList.get(5) );
        effect6.setText( effectsList.get(6) );
        effect7.setText( effectsList.get(7) );
        effect8.setText( effectsList.get(8) );
        effect9.setText( effectsList.get(9) );
        effect10.setText( effectsList.get(10) );
        effect11.setText( effectsList.get(11) );
        effect12.setText( effectsList.get(12) );
        effect13.setText( effectsList.get(13) );



        //graphic
        for (ImageView image : cardsList){

            image.setOnMouseEntered(e -> {
                image.setOpacity(0.7);
            });

            image.setOnMouseExited(e -> {
                image.setOpacity(1);
            });
        }

        for (int i = 0 ; i < cardsList.size() ; i++){
            int finalI = i;
            labelList.get(i).setOnMouseEntered(e -> {
                cardsList.get(finalI).setOpacity(0.7);
            });

            labelList.get(i).setOnMouseExited(e -> {
                cardsList.get(finalI).setOpacity(1);
            });
        }



    }

    private void disableCardsClick() {

        for( ImageView image : cardsList ) {
            image.setOnMouseClicked( mouseEvent -> {});
            image.setOnMouseEntered( mouseEvent -> {});
            image.setOnMouseExited( mouseEvent -> {});
        }

        for (Label label : labelList){
            label.setOnMouseEntered(e -> {});
            label.setOnMouseExited(e -> {});
        }


    }

    /**
     * when a card is selected, disable the event in order to prevent errors like select the same card twice
     * @param card imageView of the card selected
     */
    private void disableClickedCard(ImageView card){
        card.setOnMouseClicked( mouseEvent -> {});
        card.setOnMouseEntered(e -> {});
        card.setOnMouseExited(e -> {});
        card.setOpacity(0.7);
    }

    /**
     * save the selected card and if its the last card, send the answer to server
     * @param event click event on the card selected
     */
    public void storeCard(MouseEvent event){

        ImageView selectedCard = (ImageView) event.getSource();
        String card = (String) selectedCard.getUserData();

        disableClickedCard(selectedCard);

        selectedCards.add(card);


        if (selectedCards.size() == numberOfPlayers){

            disableCardsClick();

            showWait();

            clientView.sendCTSEvent(new ChosenCardsChallengerEvent(nicknameChallenger, selectedCards));



        }

    }

    /**
     * when challenger selected all cards, display a text until it's his turn again
     */
    private void showWait(){

        scrollPane.setVvalue(0);

        for (ImageView image : cardsList){
            image.setDisable(true);
            image.setVisible(false);

        }

        for (Label label : labelList){
            label.setVisible(false);
        }

        labelList.get(1).setText("Wait Until your turn");
        labelList.get(1).setVisible(true);

    }






}
