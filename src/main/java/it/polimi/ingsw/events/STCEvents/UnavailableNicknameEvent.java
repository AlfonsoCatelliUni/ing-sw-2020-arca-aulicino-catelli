package it.polimi.ingsw.events.STCEvents;

import it.polimi.ingsw.events.ServerToClientEvent;
import it.polimi.ingsw.events.manager.ServerToClientManager;

public class UnavailableNicknameEvent implements ServerToClientEvent {


    private Integer ID;


    // ======================================================================================


    public UnavailableNicknameEvent(Integer ID) {
        this.ID = ID;
    }


    // ======================================================================================


    @Override
    public void accept(ServerToClientManager visitor) {
        visitor.manageEvent(this);
    }


    // ======================================================================================


    public Integer getID() {
        return this.ID;
    }


}
