package it.polimi.ingsw.events.STCEvents;

import it.polimi.ingsw.events.ServerToClientEvent;
import it.polimi.ingsw.events.manager.ServerToClientManager;
import it.polimi.ingsw.model.Color;

import java.util.List;
import java.util.ListResourceBundle;

public class StartGameEvent extends ServerToClientEvent {

    public List<Color> playersColor;

    public List<String> playersNickname;

    public String playersCard;


    public StartGameEvent(List<Color> playersColor, List<String> playersNickname, String card) {
        this.playersColor = playersColor;
        this.playersNickname = playersNickname;
        this.playersCard = card;
    }

    @Override
    public void accept(ServerToClientManager visitor) {
        visitor.manageEvent(this);
    }
}
