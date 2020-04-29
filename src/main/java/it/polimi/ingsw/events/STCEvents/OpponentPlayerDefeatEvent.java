package it.polimi.ingsw.events.STCEvents;

import it.polimi.ingsw.events.ServerToClientEvent;
import it.polimi.ingsw.events.manager.ServerToClientManager;

public class OpponentPlayerDefeatEvent  extends ServerToClientEvent {

    public String playersInGame;

    public String looser;

    public OpponentPlayerDefeatEvent(String playersInGame, String looser) {
        this.playersInGame = playersInGame;
        this.looser = looser;
    }


    @Override
    public void accept(ServerToClientManager visitor) {
        visitor.manageEvent(this);
    }
}
