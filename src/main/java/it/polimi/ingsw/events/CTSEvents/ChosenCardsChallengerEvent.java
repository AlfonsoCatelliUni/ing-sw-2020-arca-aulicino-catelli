package it.polimi.ingsw.events.CTSEvents;

import it.polimi.ingsw.events.ClientToServerEvent;
import it.polimi.ingsw.events.manager.ClientToServerManager;

import java.util.List;

public class ChosenCardsChallengerEvent extends ClientToServerEvent {

    public String challengerNickname;

    public List<String> cardsName;


    // ======================================================================================


    public ChosenCardsChallengerEvent(String challengerNickname, List<String> cardsName) {
        this.challengerNickname = challengerNickname;
        this.cardsName = cardsName;
    }


    // ======================================================================================


    @Override
    public void accept(ClientToServerManager visitor) {
        visitor.manageEvent(this);
    }


}
