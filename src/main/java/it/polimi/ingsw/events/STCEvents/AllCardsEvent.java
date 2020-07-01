package it.polimi.ingsw.events.STCEvents;

import it.polimi.ingsw.events.ServerToClientEvent;
import it.polimi.ingsw.events.manager.ServerToClientManager;

import java.util.List;

public class AllCardsEvent extends ServerToClientEvent {


    public String receiverNickname;

    public List<String> cardsName;

    public List<String> cardsEffect;

    public boolean isValid;

    public int numberOfPlayers;


    // ======================================================================================


    public AllCardsEvent(String receiverNickname, List<String> cardsName, List<String> cardsEffect, int numberOfPlayers, boolean isValid) {
        this.receiverNickname = receiverNickname;
        this.cardsName = cardsName;
        this.cardsEffect = cardsEffect;
        this.isValid = isValid;
        this.numberOfPlayers = numberOfPlayers;
    }


    // ======================================================================================


    @Override
    public void accept(ServerToClientManager visitor) {
        visitor.manageEvent(this);
    }


    //USED IN TESTING
    @Override
    public String toString() {
        return "AllPossibleCards{" +  ",\n" +
                "receiverNickname='" + receiverNickname + '\'' +  ",\n" +
                "cardsName=" + cardsName +  ",\n" +
                "cardsEffect=" + cardsEffect +  ",\n" +
                "isValid=" + isValid +  ",\n" +
                '}';
    }


}
