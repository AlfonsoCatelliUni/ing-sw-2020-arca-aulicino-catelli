package it.polimi.ingsw.events.CTSEvents;

import it.polimi.ingsw.events.ClientToServerEvent;
import it.polimi.ingsw.events.manager.ClientToServerManager;
import it.polimi.ingsw.events.manager.ServerToClientManager;

//TODO : magari cambiare implementazione di CTSevent
public class VictoryEvent implements ClientToServerEvent {


    private String winnerNickname;


    // ======================================================================================


    public VictoryEvent(String winnerNickname) {
        this.winnerNickname = winnerNickname;
    }


    // ======================================================================================


    @Override
    public void accept(ClientToServerManager visitor) {
        visitor.manageEvent(this);
    }


    public String getWinnerNickname() {
        return this.winnerNickname;
    }


}
