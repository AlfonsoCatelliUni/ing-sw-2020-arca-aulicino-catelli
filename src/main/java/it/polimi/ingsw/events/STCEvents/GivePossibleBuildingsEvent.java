package it.polimi.ingsw.events.STCEvents;

import it.polimi.ingsw.events.ServerToClientEvent;
import it.polimi.ingsw.events.manager.ServerToClientManager;

import java.util.List;

public class GivePossibleBuildingsEvent extends ServerToClientEvent {

    public String receiverNickname;

    public List<Integer> buildings;

    public boolean isValid;


    public GivePossibleBuildingsEvent(String receiverNickname, List<Integer> buildings, boolean isValid) {

        this.receiverNickname = receiverNickname;
        this.buildings = buildings;
        this.isValid = isValid;

    }

    @Override
    public void accept(ServerToClientManager visitor) {
        visitor.manageEvent(this);
    }

    //USED IN TESTING
    @Override
    public String toString() {
        return "GivePossibleBuildingsEvent{" + "\n" +
                "receiverNickname='" + receiverNickname + '\'' + ",\n" +
                "buildings=" + buildings + ",\n" +
                "isValid=" + isValid + "\n" +
                '}';
    }
}
