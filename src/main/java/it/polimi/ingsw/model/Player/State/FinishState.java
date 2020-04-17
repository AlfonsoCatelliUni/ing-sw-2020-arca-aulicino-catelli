package it.polimi.ingsw.model.Player.State;

import it.polimi.ingsw.model.Actions.Action;
import it.polimi.ingsw.model.Actions.FinishAction;
import it.polimi.ingsw.model.Board.Board;
import it.polimi.ingsw.model.Player.Pawn;
import it.polimi.ingsw.model.Player.Player;

import java.util.ArrayList;
import java.util.List;

public class FinishState implements StateInterface {

    //TODO : cambiare in effect quando gli altri committeranno
    private Player player;


    private List<Action> actions;


    // ======================================================================================


    public FinishState(Player player) {
        this.player = player;

        this.actions = new ArrayList<>();
        this.actions.add(new FinishAction());
    }


    // ======================================================================================


    public List<Action> checkPossibleActions(Board gameBoard, Pawn designatedPawn) {
        return new ArrayList<>(actions);
    }

}
