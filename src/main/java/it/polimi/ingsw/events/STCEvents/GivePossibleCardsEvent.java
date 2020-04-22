package it.polimi.ingsw.events.STCEvents;

import it.polimi.ingsw.events.ServerToClientEvent;
import it.polimi.ingsw.events.manager.ServerToClientManager;
import it.polimi.ingsw.model.Player.Card;

import java.util.List;

public class GivePossibleCardsEvent extends ServerToClientEvent {


    public String receiverNickname;


    public List<Card> cards;


    public boolean isValid;


    // ======================================================================================


    public GivePossibleCardsEvent(String receiverNickname, List<Card> cards, boolean isValid) {
        this.receiverNickname = receiverNickname;
        this.cards = cards;
        this.isValid = isValid;
    }


    // ======================================================================================


    @Override
    public void accept(ServerToClientManager visitor) {
        visitor.manageEvent(this);
    }


    // ======================================================================================


//    public String getReceiverNickname() {
//        return this.receiverNickname;
//    }
//
//
//    public List<Card> getCards() {
//        return this.cards;
//    }


}
