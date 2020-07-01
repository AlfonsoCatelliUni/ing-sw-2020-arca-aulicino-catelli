package it.polimi.ingsw.events.STCEvents;

import it.polimi.ingsw.events.ServerToClientEvent;
import it.polimi.ingsw.events.manager.ServerToClientManager;

import java.util.List;

public class SuccessfullyConnectedEvent extends ServerToClientEvent {


    public List<String> connectedPlayers;

    public String nickname;
    // ======================================================================================


    public SuccessfullyConnectedEvent(List<String> connectedPlayers, String nickname) {
        this.connectedPlayers = connectedPlayers;
        this.nickname = nickname;
    }


    // ======================================================================================

    @Override
    public void accept(ServerToClientManager visitor) {
        visitor.manageEvent(this);
    }


    //USED IN TESTING
    @Override
    public String toString() {
        return "SuccessfullyConnectedEvent{" + "\n" +
                "connectedPlayers=" + connectedPlayers + ",\n" +
                "nickname='" + nickname + '\'' + "\n" +
                '}';
    }
}
