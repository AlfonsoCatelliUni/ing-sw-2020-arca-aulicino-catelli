package it.polimi.ingsw.events.STCEvents;

import it.polimi.ingsw.events.ServerToClientEvent;
import it.polimi.ingsw.events.manager.ServerToClientManager;
import it.polimi.ingsw.model.Player.Card;

import java.util.List;

public class GivePossibleCardsEvent extends ServerToClientEvent {


    public String receiverNickname;

    public String info;

    public boolean isValid;


    // ======================================================================================


    public GivePossibleCardsEvent(String receiverNickname, String info, boolean isValid) {
        this.receiverNickname = receiverNickname;
        this.info = info;
        this.isValid = isValid;
    }


    // ======================================================================================


    @Override
    public void accept(ServerToClientManager visitor) {
        visitor.manageEvent(this);
    }


}
