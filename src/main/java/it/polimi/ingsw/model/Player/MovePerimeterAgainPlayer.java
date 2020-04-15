package it.polimi.ingsw.model.Player;

import it.polimi.ingsw.model.Actions.Action;
import it.polimi.ingsw.model.Actions.MoveAction;
import it.polimi.ingsw.model.Board.Board;
import it.polimi.ingsw.model.Board.Cell;

import java.util.List;

/**
 * this decorator gives the possibility to move again each time the pawn moves onto perimeter space
 */
public class MovePerimeterAgainPlayer extends PlayerDecorator {


    public MovePerimeterAgainPlayer(BasicPlayer player) {
        super(player);
    }


    // ======================================================================================


    /**
     * This method is similar to basic, but the player can move again each time pawn moves onto perimeter space
     * is so, after the player moved, will be returned the action "build" and also "move"
     * For the end of the turn, the method will return the action "finish"
     * @param gameBoard is the board where is played the game
     * @param designatedPawn is the pawn selected by the player for the current turn
     * @return a list of possible actions that the player can do in a specific moment in his turn
     */
    @Override
    public List<Action> getPossibleActions(Board gameBoard, Pawn designatedPawn) {

        if (player.getNumMove() == 2 && player.getNumBuild() < 0) {
            player.setNumMove(1);
            player.setNumBuild(0);
        }


        List<Action> availableActions = player.getPossibleActions(gameBoard, designatedPawn);

        Cell pawnPosition = designatedPawn.getPosition();

        //numMove = 1 and build = 0, so if pawn is on perimeter space it means that player moves on perimeter space, so
        if (player.getNumBuild() == 0 && player.getNumMove() == 1 && pawnPosition.isPerimeter())
            availableActions.add(new MoveAction());

        return availableActions;
    }


}
