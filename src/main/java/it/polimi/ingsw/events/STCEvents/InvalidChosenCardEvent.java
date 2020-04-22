package it.polimi.ingsw.events.STCEvents;

import it.polimi.ingsw.events.ServerToClientEvent;
import it.polimi.ingsw.events.manager.ServerToClientManager;
import it.polimi.ingsw.model.Player.Card;

import java.util.List;

public class InvalidChosenCardEvent extends ServerToClientEvent {

    public String receiverNickname;


    public List<String> cards;


    // ======================================================================================


    public InvalidChosenCardEvent(String receiverNickname, List<String> cards) {
        this.receiverNickname = receiverNickname;
        this.cards = cards;
    }


    // ======================================================================================


    @Override
    public void accept(ServerToClientManager visitor) {
        visitor.manageEvent(this);
    }


    // ======================================================================================
}
