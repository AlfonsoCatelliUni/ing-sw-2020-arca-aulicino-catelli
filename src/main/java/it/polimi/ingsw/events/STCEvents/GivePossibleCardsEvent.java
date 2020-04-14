package it.polimi.ingsw.events.STCEvents;

import it.polimi.ingsw.events.ServerToClientEvent;
import it.polimi.ingsw.events.manager.ServerToClientManager;
import it.polimi.ingsw.model.Player.Card;

import java.util.List;

public class GivePossibleCardsEvent implements ServerToClientEvent {


    private String receiverNickname;


    private List<Card> cards;


    // ======================================================================================


    public GivePossibleCardsEvent(String receiverNickname, List<Card> cards) {
        this.receiverNickname = receiverNickname;
        this.cards = cards;
    }


    // ======================================================================================


    @Override
    public void accept(ServerToClientManager visitor) {
        visitor.manageEvent(this);
    }


    // ======================================================================================


    public String getReceiverNickname() {
        return this.receiverNickname;
    }


    public List<Card> getCards() {
        return this.cards;
    }


}
