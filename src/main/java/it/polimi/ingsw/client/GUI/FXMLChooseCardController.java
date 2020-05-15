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
    private ImageView card3;



    @FXML
    private void initialize(){
        this.cardsList = new ArrayList<>();



        Image cardImage = new Image(getClass().getResourceAsStream("/Graphics/santorini-start.png"));
        card3.setImage(cardImage);

        card3.setVisible(true);

        cardsList.add(card3);

    }
}
