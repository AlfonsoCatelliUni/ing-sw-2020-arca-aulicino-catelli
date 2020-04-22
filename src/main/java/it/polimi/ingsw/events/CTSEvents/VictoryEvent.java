package it.polimi.ingsw.events.CTSEvents;

import it.polimi.ingsw.events.ClientToServerEvent;
import it.polimi.ingsw.events.manager.ClientToServerManager;


public class VictoryEvent extends ClientToServerEvent {


    public String winnerNickname;


    // ======================================================================================


    public VictoryEvent(String winnerNickname) {
        this.winnerNickname = winnerNickname;
    }


    // ======================================================================================


    @Override
    public void accept(ClientToServerManager visitor) {
        visitor.manageEvent(this);
    }


//    public String getWinnerNickname() {
//        return this.winnerNickname;
//    }


}
