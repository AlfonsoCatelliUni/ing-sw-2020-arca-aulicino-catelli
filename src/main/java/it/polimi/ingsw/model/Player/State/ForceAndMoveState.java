package it.polimi.ingsw.model.Player.State;

import it.polimi.ingsw.model.Actions.Action;
import it.polimi.ingsw.model.Actions.ForceAction;
import it.polimi.ingsw.model.Actions.MoveAction;
import it.polimi.ingsw.model.Board.Board;
import it.polimi.ingsw.model.Player.Effect.Effect;
import it.polimi.ingsw.model.Player.Pawn;

import java.util.ArrayList;
import java.util.List;

public class ForceAndMoveState implements StateInterface {


    private final Effect effect;


    private final List<Action> actions;


    // ======================================================================================


    public ForceAndMoveState(Effect effect) {
        this.effect = effect;

        this.actions = new ArrayList<>();
        this.actions.add(new ForceAction());
        this.actions.add(new MoveAction());
    }


    // ======================================================================================


    public List<Action> checkPossibleActions(Board gameBoard, Pawn designatedPawn) {

        List<Action> possibleActions = new ArrayList<>(actions);

        if(effect.wherePawnCanMove(gameBoard, designatedPawn).size() == 0) {
            possibleActions.removeIf(a -> a.getClass().equals(MoveAction.class));
        }
        if(effect.wherePawnCanForce(gameBoard, designatedPawn).size() == 0) {
            possibleActions.removeIf(a -> a.getClass().equals(ForceAction.class));
        }

        return possibleActions;
    }


}
