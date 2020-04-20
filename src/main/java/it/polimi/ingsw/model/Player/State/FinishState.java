package it.polimi.ingsw.model.Player.State;

import it.polimi.ingsw.model.Actions.Action;
import it.polimi.ingsw.model.Actions.FinishAction;
import it.polimi.ingsw.model.Actions.ForceAction;
import it.polimi.ingsw.model.Board.Board;
import it.polimi.ingsw.model.Player.Effect.Effect;
import it.polimi.ingsw.model.Player.Pawn;

import java.util.ArrayList;
import java.util.List;

public class FinishState implements StateEffectInterface {


    private Effect effect;


    // ======================================================================================


    public FinishState(Effect effect) {
        this.effect = effect;
    }


    // ======================================================================================


    /**
     * returns a FinishAction, so the player can only end his turn
     * @param gameBoard is the board where tha game is played
     * @param designatedPawn is the chosen pawn for the current turn
     * @return a list of possible actions
     */
    public List<Action> checkPossibleActions(Board gameBoard, Pawn designatedPawn) {
        List<Action> possibleActions = new ArrayList<>();
        possibleActions.add(new FinishAction());
        return possibleActions;
    }


    public void setEffect(Effect effect) {
        this.effect = effect;
    }


}
