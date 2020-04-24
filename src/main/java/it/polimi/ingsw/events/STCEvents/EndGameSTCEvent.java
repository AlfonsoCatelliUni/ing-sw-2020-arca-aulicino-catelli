package it.polimi.ingsw.events.STCEvents;

import it.polimi.ingsw.events.ServerToClientEvent;
import it.polimi.ingsw.events.manager.ServerToClientManager;

public class EndGameSTCEvent extends ServerToClientEvent {


    String winner;


    public EndGameSTCEvent(String winner) {
        this.winner = winner;
    }

    @Override
    public void accept(ServerToClientManager visitor) {
        visitor.manageEvent(this);
    }
}
