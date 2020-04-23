package it.polimi.ingsw.events.CTSEvents;

import it.polimi.ingsw.events.ClientToServerEvent;
import it.polimi.ingsw.events.manager.ClientToServerManager;

public class ChosenCardEvent extends ClientToServerEvent {


    public String playerNickname;


    public String card;


    // ======================================================================================


    public ChosenCardEvent(String playerNickname, String card) {
        this.playerNickname = playerNickname;
        this.card = card;
    }


    // ======================================================================================


    @Override
    public void accept(ClientToServerManager visitor) {
        visitor.manageEvent(this);
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
