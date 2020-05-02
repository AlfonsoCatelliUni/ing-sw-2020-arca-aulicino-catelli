package it.polimi.ingsw.events.STCEvents;

import it.polimi.ingsw.events.ServerToClientEvent;
import it.polimi.ingsw.events.manager.ServerToClientManager;

public class OpponentPlayerDefeatEvent  extends ServerToClientEvent {

    public String playersInGame;

    public String loser;


    // ======================================================================================


    public OpponentPlayerDefeatEvent(String playersInGame, String loser) {
        this.playersInGame = playersInGame;
        this.loser = loser;
    }


    // ======================================================================================


    @Override
    public void accept(ServerToClientManager visitor) {
        visitor.manageEvent(this);
    }


}
