package it.polimi.ingsw.events.CTSEvents;

import it.polimi.ingsw.events.ClientToServerEvent;
import it.polimi.ingsw.events.manager.ClientToServerManager;
import it.polimi.ingsw.model.Player.Card;

public class ChosenCardEvent extends ClientToServerEvent {


    public String playerNickname;


    public Card card;


    // ======================================================================================


    public ChosenCardEvent(String playerNickname, Card card) {
        this.playerNickname = playerNickname;
        this.card = card;
    }


    // ======================================================================================


    @Override
    public void accept(ClientToServerManager visitor) {
        visitor.receiveEvent(this);
    }


    // ======================================================================================


//    public String getCard() {
//        return this.card;
//    }
//
//
//    public String getPlayerNickname() {
//        return this.playerNickname;
//    }


}
