package it.polimi.ingsw.events.STCEvents;

import it.polimi.ingsw.events.ServerToClientEvent;
import it.polimi.ingsw.events.manager.ServerToClientManager;

import java.util.List;

public class SuccessfullyConnectedEvent implements ServerToClientEvent {


    private List<String> connectedPlayers;


    // ======================================================================================


    public SuccessfullyConnectedEvent(List<String> connectedPlayers) {
        this.connectedPlayers = connectedPlayers;
    }


    // ======================================================================================

    @Override
    public void accept(ServerToClientManager visitor) {
        visitor.manageEvent(this);
    }


    public List<String> getConnectedPlayers() {
        return connectedPlayers;
    }


}
