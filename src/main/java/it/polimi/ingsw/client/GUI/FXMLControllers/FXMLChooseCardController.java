package it.polimi.ingsw.client.GUI.FXMLControllers;

import it.polimi.ingsw.events.CTSEvents.ChosenCardEvent;
import it.polimi.ingsw.view.client.ClientView;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FXMLChooseCardController {


    private ClientView clientView;

    private Stage stage;

    /**
     * the nickname of the player
     */
    private String nickname;

    /**
     * list of urls for the images of the chosen cards for the game
     */
    private List<String> cardsUrl;

    /**
     * list of names of the chosen cards for the game
     */
    private List<String> cardsName;

    /**
     * list of effects of the chosen cards for the game
     */
    private List<String> effectsList;

    /**
     * list of the images of the chosen cards for the game
     */
    private List<ImageView> cardsList;


    /**
     * bindings with ChooseCardScene.fxml
     */
    @FXML
    private ImageView card0;
    @FXML
    private ImageView card1;
    @FXML
    private ImageView card2;


    private List<Label> labelList;

    @FXML
    private Label effect0;
    @FXML
    private Label effect1;
    @FXML
    private Label effect2;


    // MARK : Initialize Methods ======================================================================================


    @FXML
    private void initialize() {
        this.cardsList = new ArrayList<>();
        this.labelList = new ArrayList<>();
        cardsUrl = new ArrayList<>();

        initializeCards();
        initializeLabels();

    }


    /**
     * this method initialize all imageView for the cards
     */
    private void initializeCards() {

        cardsList.add(card0);
        cardsList.add(card1);
        cardsList.add(card2);

        for( ImageView iw : cardsList  ) {
            iw.setVisible(true);
        }

    }


    /**
     * this method initialize all Label for the gods effects
     */
    private void initializeLabels() {

        labelList.add(effect0);
        labelList.add(effect1);
        labelList.add(effect2);

        for( Label label : labelList  ) {
            label.setVisible(true);


            label.setFont( Font.loadFont(
                    getClass().getResource("/Font/DisneyHeroic.ttf").toExternalForm(),
                    15
            ));


        }

    }


    // MARK : Game Event Managers ======================================================================================


    public void setController(Stage stage, String nickname, ClientView clientView, List<String> cardsName, List<String> effectsList) {
        String prefix = "/Graphics/Cards/";

        this.clientView = clientView;
        this.cardsName = cardsName;
        this.effectsList = effectsList;
        this.nickname = nickname;
        this.stage = stage;

        this.cardsUrl = new ArrayList<>();

        for (String cardName : cardsName){
            cardsUrl.add(prefix + cardName + ".png");
        }


    }


    public void setCards() {
        switch (cardsUrl.size()) {
            case 3:
                card0.setImage( new Image(getClass().getResourceAsStream(cardsUrl.get(0))) );
                card1.setImage( new Image(getClass().getResourceAsStream(cardsUrl.get(1))) );
                card2.setImage( new Image(getClass().getResourceAsStream(cardsUrl.get(2))) );

                card0.setUserData(cardsName.get(0));
                card1.setUserData(cardsName.get(1));
                card2.setUserData(cardsName.get(2));

                effect0.setText( effectsList.get(0) );
                effect1.setText( effectsList.get(1) );
                effect2.setText( effectsList.get(2) );

                card0.setOnMouseClicked(this::sendCard);
                card1.setOnMouseClicked(this::sendCard);
                card2.setOnMouseClicked(this::sendCard);

                //graphic effect
                card0.setOnMouseEntered(e -> {
                    card0.setOpacity(0.7);
                });
                card0.setOnMouseExited(e -> {
                    card0.setOpacity(1);
                });
                card1.setOnMouseEntered(e -> {
                    card1.setOpacity(0.7);
                });
                card1.setOnMouseExited(e -> {
                    card1.setOpacity(1);
                });
                card2.setOnMouseEntered(e -> {
                    card2.setOpacity(0.7);
                });
                card2.setOnMouseExited(e -> {
                    card2.setOpacity(1);
                });
                break;

            case 2:
                card0.setImage( new Image(getClass().getResourceAsStream(cardsUrl.get(0))) );
                card2.setImage( new Image(getClass().getResourceAsStream(cardsUrl.get(1))) );

                card0.setUserData(cardsName.get(0));
                card1.setUserData("");
                card2.setUserData(cardsName.get(1));

                effect0.setText( effectsList.get(0) );
                effect1.setText("");
                effect2.setText( effectsList.get(1) );

                card0.setOnMouseClicked(this::sendCard);
                card2.setOnMouseClicked(this::sendCard);

                //graphic effect
                card0.setOnMouseEntered(e -> {
                    card0.setOpacity(0.7);
                });
                card0.setOnMouseExited(e -> {
                    card0.setOpacity(1);
                });
                card2.setOnMouseEntered(e -> {
                    card2.setOpacity(0.7);
                });
                card2.setOnMouseExited(e -> {
                    card2.setOpacity(1);
                });

                //graphic effect
                card1.setOnMouseEntered(e -> {
                    card1.setOpacity(0.7);
                });
                card1.setOnMouseExited(e -> {
                    card1.setOpacity(1);
                });
                break;

            case 1:
                card1.setImage( new Image(getClass().getResourceAsStream(cardsUrl.get(0))) );

                card0.setUserData("");
                card1.setUserData(cardsName.get(0));
                card2.setUserData("");

                effect0.setText("");
                effect1.setText( effectsList.get(0) );
                effect2.setText("");

                card1.setOnMouseClicked(this::sendCard);
                break;
        }
    }


    private void sendCard(MouseEvent event)  {

        disableCardsClick();



        ImageView selectedCard = (ImageView) event.getSource();
        String card = (String) selectedCard.getUserData();

        System.out.println("Selected Card : " + card);
        clientView.sendCTSEvent(new ChosenCardEvent(nickname, card));

        showWait();


    }


    /**
     * this method is used to disable the function when an image is clicked
     */
    private void disableCardsClick() {
        for( ImageView image : cardsList ) {
            image.setOnMouseClicked( mouseEvent -> {});
        }
    }

    private void showWait(){

        for (ImageView image : cardsList ){
            image.setVisible(false);
        }

        labelList.get(0).setVisible(false);
        labelList.get(1).setText("Wait until the Game starts");
        labelList.get(1).setAlignment(Pos.BOTTOM_CENTER);
        labelList.get(2).setVisible(false);
    }


}
