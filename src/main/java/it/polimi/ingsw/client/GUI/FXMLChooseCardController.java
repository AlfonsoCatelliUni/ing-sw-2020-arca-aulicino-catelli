package it.polimi.ingsw.client.GUI;

import it.polimi.ingsw.view.client.ClientView;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.ArrayList;
import java.util.List;

public class FXMLChooseCardController {

    private ClientView clientView;

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
    private void initialize(){
        this.cardsList = new ArrayList<>();

        initializeCards();
    }

    private void initializeCards(){

        card1.setVisible(true);
        card2.setVisible(true);
        card3.setVisible(true);
        card4.setVisible(true);
        card0.setVisible(true);


      //  Image card = new Image(getClass().getResourceAsStream("/Graphics/Cards/ApolloCard.png"));
      //  card2.setImage(card);



        cardsList.add(card0);
        cardsList.add(card1);
        cardsList.add(card2);
        cardsList.add(card3);
        cardsList.add(card4);


    }

    @FXML
    public void initializeOneCard(Image cardImage){
        card2.setImage(cardImage);

    }
}
