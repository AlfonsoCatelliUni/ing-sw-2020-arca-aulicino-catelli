package it.polimi.ingsw.model.Player.State;

import it.polimi.ingsw.model.Actions.Action;
import it.polimi.ingsw.model.Actions.FinishAction;
import it.polimi.ingsw.model.Board.Board;
import it.polimi.ingsw.model.Player.Effect.Effect;
import it.polimi.ingsw.model.Player.Pawn;

import java.util.ArrayList;
import java.util.List;

public class FinishState implements StateInterface {

    private final Effect effect;


    private final List<Action> actions;


    // ======================================================================================


    public FinishState(Effect effect) {
        this.effect = effect;

        this.actions = new ArrayList<>();
        this.actions.add(new FinishAction());
    }


    // ======================================================================================


    public List<Action> checkPossibleActions(Board gameBoard, Pawn designatedPawn) {
        return new ArrayList<>(actions);
    }


}
