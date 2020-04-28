package it.polimi.ingsw.events.STCEvents;

import it.polimi.ingsw.events.ServerToClientEvent;
import it.polimi.ingsw.events.manager.ServerToClientManager;
import it.polimi.ingsw.model.Player.Card;

import java.util.List;

public class GivePossibleCardsEvent extends ServerToClientEvent {


    public String receiverNickname;

    public List<String> cardsName;

    public List<String> cardsEffect;

    public boolean isValid;


    // ======================================================================================


    public GivePossibleCardsEvent(String receiverNickname, List<String> cardsName, List<String> cardsEffect, boolean isValid) {
        this.receiverNickname = receiverNickname;
        this.cardsName = cardsName;
        this.cardsEffect = cardsEffect;
        this.isValid = isValid;
    }


    // ======================================================================================


    @Override
    public void accept(ServerToClientManager visitor) {
        visitor.manageEvent(this);
    }


    @Override
    public String toString() {
        return "GivePossibleCardsEvent{" +  ",\n" +
                "receiverNickname='" + receiverNickname + '\'' +  ",\n" +
                "cardsName=" + cardsName +  ",\n" +
                "cardsEffect=" + cardsEffect +  ",\n" +
                "isValid=" + isValid +  ",\n" +
                '}';
    }
}
