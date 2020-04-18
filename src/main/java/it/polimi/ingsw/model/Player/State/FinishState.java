package it.polimi.ingsw.model.Player.State;

import it.polimi.ingsw.model.Actions.Action;
import it.polimi.ingsw.model.Actions.FinishAction;
import it.polimi.ingsw.model.Actions.ForceAction;
import it.polimi.ingsw.model.Board.Board;
import it.polimi.ingsw.model.Player.Effect.Effect;
import it.polimi.ingsw.model.Player.Pawn;

import java.util.ArrayList;
import java.util.List;

public class FinishState implements StateInterface {

    private final Effect effect;


    // ======================================================================================


    public FinishState(Effect effect) {
        this.effect = effect;
    }


    // ======================================================================================


    public List<Action> checkPossibleActions(Board gameBoard, Pawn designatedPawn) {
        List<Action> possibleActions = new ArrayList<>();
        possibleActions.add(new FinishAction());
        return possibleActions;
    }


}
