package it.polimi.ingsw.client.GUI;

import it.polimi.ingsw.view.client.ClientView;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.ArrayList;
import java.util.List;

public class FXMLChooseCardController {

    private ClientView clientView;

    private List<String> cardsUrl;

    List<ImageView> cardsList;

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
    private void initialize() {
        this.cardsList = new ArrayList<>();
        cardsUrl = new ArrayList<>();

        initializeCards();
    }


    private void initializeCards() {

        cardsList.add(card0);
        cardsList.add(card1);
        cardsList.add(card2);
        cardsList.add(card3);
        cardsList.add(card4);

        for( ImageView iw : cardsList  ) {
            iw.setVisible(true);
        }

    }


    // MARK : Game Event Managers ======================================================================================



    // MARK : Supportive Methods ======================================================================================


    public void setCardsURL(List<String> cardsUrl) {
        this.cardsUrl = cardsUrl;
    }


    public void setCards() {
        switch (cardsUrl.size()) {
            case 3:
                card0.setImage( new Image(getClass().getResourceAsStream(cardsUrl.get(0))) );
                card2.setImage( new Image(getClass().getResourceAsStream(cardsUrl.get(1))) );
                card4.setImage( new Image(getClass().getResourceAsStream(cardsUrl.get(2))) );
                break;

            case 2:
                card1.setImage( new Image(getClass().getResourceAsStream(cardsUrl.get(0))) );
                card3.setImage( new Image(getClass().getResourceAsStream(cardsUrl.get(1))) );
                break;

            case 1:
                card2.setImage( new Image(getClass().getResourceAsStream(cardsUrl.get(0))) );
                break;
        }
    }


}
