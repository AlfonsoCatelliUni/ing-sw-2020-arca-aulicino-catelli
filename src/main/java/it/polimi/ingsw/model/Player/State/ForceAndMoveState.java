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


    // ======================================================================================


    public ForceAndMoveState(Effect effect) {
        this.effect = effect;
    }


    // ======================================================================================


    /**
     * checks if in the current turn the designatedPawn can do a ForceAction, a MoveAction
     * or even both
     * @param gameBoard is the board where the game is played
     * @param designatedPawn is the pawn chosen by the current player
     * @return a list of possible actions that can be done by the designatedPawn
     */
    public List<Action> checkPossibleActions(Board gameBoard, Pawn designatedPawn) {

        List<Action> possibleActions = new ArrayList<>();

        if(effect.wherePawnCanMove(gameBoard, designatedPawn).size() > 0) {
            possibleActions.add(new MoveAction());
        }
        if(effect.wherePawnCanForce(gameBoard, designatedPawn).size() > 0) {
            possibleActions.add(new ForceAction());
        }

        return possibleActions;
    }


}
