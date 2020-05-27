package it.polimi.ingsw.events.STCEvents;

import it.polimi.ingsw.events.ServerToClientEvent;
import it.polimi.ingsw.events.manager.ServerToClientManager;

import java.util.List;

public class GiveFirstPlayerChoiceEvent extends ServerToClientEvent {

    public List<String> playersNickname;

    public List<String> playersCard;

    public List<String> playersCardEffect;

    public Boolean isValid;


    public GiveFirstPlayerChoiceEvent(List<String>playersNicknames, List<String> playersCard, List<String> playersCardEffect, Boolean isValid) {
        this.playersNickname = playersNicknames;
        this.playersCard = playersCard;
        this.playersCardEffect = playersCardEffect;
        this.isValid = isValid;
    }


    @Override
    public void accept(ServerToClientManager visitor) {
        visitor.manageEvent(this);
    }

}
