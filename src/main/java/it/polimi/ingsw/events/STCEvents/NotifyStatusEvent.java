package it.polimi.ingsw.events.STCEvents;

import it.polimi.ingsw.events.ServerToClientEvent;
import it.polimi.ingsw.events.manager.ServerToClientManager;

public class NotifyStatusEvent extends ServerToClientEvent {


    private String status;


    // ======================================================================================


    public NotifyStatusEvent(String status){
        this.status = status;
    }


    // ======================================================================================


    @Override
    public void accept(ServerToClientManager visitor) {
        visitor.manageEvent(this);
    }


    public String getStatus(){
        return this.status;
    }



}
