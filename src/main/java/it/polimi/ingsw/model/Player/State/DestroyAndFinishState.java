package it.polimi.ingsw.model.Player.State;

import it.polimi.ingsw.model.Actions.Action;
import it.polimi.ingsw.model.Actions.DestroyAction;
import it.polimi.ingsw.model.Actions.FinishAction;
import it.polimi.ingsw.model.Board.Board;
import it.polimi.ingsw.model.Player.Effect.Effect;
import it.polimi.ingsw.model.Player.Pawn;

import java.util.ArrayList;
import java.util.List;

public class DestroyAndFinishState implements StateInterface {


    private final Effect effect;


    private final List<Action> actions;


    // ======================================================================================


    public DestroyAndFinishState(Effect effect) {
        this.effect = effect;

        this.actions = new ArrayList<>();
        this.actions.add(new DestroyAction());
        this.actions.add(new FinishAction());
    }


    // ======================================================================================


    public List<Action> checkPossibleActions(Board gameBoard, Pawn designatedPawn) {

        List<Action> possibleActions = new ArrayList<>(actions);

        if(effect.wherePawnCanDestroy(gameBoard, designatedPawn).size() == 0) {
            possibleActions.removeIf(a -> a.getClass().equals(DestroyAction.class));
        }

        return possibleActions;
    }


}
