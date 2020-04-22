package it.polimi.ingsw.events.STCEvents;

import it.polimi.ingsw.events.ServerToClientEvent;
import it.polimi.ingsw.events.manager.ServerToClientManager;
import it.polimi.ingsw.model.Board.Building;

import java.util.List;

public class GivePossibleBuildingsEvent extends ServerToClientEvent {

    public String receiverNickname;

    public List<Building> buildings;

    public boolean isValid;


    public GivePossibleBuildingsEvent(String receiverNickname,List<Building> buildings, boolean isValid) {

        this.receiverNickname = receiverNickname;
        this.buildings = buildings;
        this.isValid = isValid;

    }

    @Override
    public void accept(ServerToClientManager visitor) {
        visitor.manageEvent(this);
    }
}
