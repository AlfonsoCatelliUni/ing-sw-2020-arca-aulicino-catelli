package it.polimi.ingsw.model.Player.State;

import it.polimi.ingsw.model.Actions.Action;
import it.polimi.ingsw.model.Actions.MoveAction;
import it.polimi.ingsw.model.Board.Board;
import it.polimi.ingsw.model.Player.Pawn;
import it.polimi.ingsw.model.Player.Player;

import java.util.ArrayList;
import java.util.List;

public class MoveState implements StateInterface {


    private final Player player;


    private final List<Action> actions;


    // ======================================================================================


    public MoveState(Player player) {
        this.player = player;

        this.actions = new ArrayList<>();
        this.actions.add(new MoveAction());
    }


    // ======================================================================================


    public List<Action> checkPossibleActions(Board gameBoard, Pawn designatedPawn) {

        List<Action> possibleActions = new ArrayList<>(actions);

        //TODO : cambiare in effect quando committeranno gli altri
        if(player.wherePawnCanMove(gameBoard, designatedPawn).size() == 0) {
            possibleActions.removeIf(a -> a.getClass().equals(MoveAction.class));
        }

        return possibleActions;
    }


}
