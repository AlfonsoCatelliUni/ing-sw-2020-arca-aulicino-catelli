package it.polimi.ingsw.model.Player;

import it.polimi.ingsw.model.Actions.Action;
import it.polimi.ingsw.model.Actions.DestroyAction;
import it.polimi.ingsw.model.Board.Board;
import it.polimi.ingsw.model.Board.Building;
import it.polimi.ingsw.model.Board.Cell;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DestroyBlockPlayer extends PlayerDecorator {


    public DestroyBlockPlayer(BasicPlayer player) {
        super(player);
    }


    // ======================================================================================


    /**
     * this overrides the super method until the player has to finish the turn
     * With the finish action, the player could also have a destroy action to be used
     * @param gameBoard is the board where the game is played
     * @param designatedPawn is the designated pawn for the actions
     * @return a list of possible action for the designated pawn
     */
    @Override
    public List<Action> getPossibleActions(Board gameBoard, Pawn designatedPawn) {
        List<Action> availableAction = super.getPossibleActions(gameBoard, designatedPawn);

        if (player.getNumMove() == 1 && player.getNumBuild() == 1) {

            Pawn notMovedPawn = Arrays.stream(getPawns()).filter(pawn -> !pawn.getHasMoved()).findAny().get();
            List<Cell> wherePawnCanDestroy = wherePawnCanDestroy(gameBoard, notMovedPawn);

            if(wherePawnCanDestroy.size() > 0) {
                availableAction.add(new DestroyAction());
            }

        }

        return availableAction;

    }


    /**
     * this method controls where the not-moved pawn can destroy
     * @param gameBoard is the board where the game is played
     * @param designatedPawn is the not-movedPawn
     * @return the list of cells where the pawn can destroy a block
     */
    @Override
    public List<Cell> wherePawnCanDestroy(Board gameBoard, Pawn designatedPawn) {
        return wherePawnCanBuild(gameBoard, designatedPawn).stream().filter(cell -> cell.getHeight()>0).collect(Collectors.toList());

    }


    /**
     * this method destroys the roof of the designatedCell.
     * This method will not destroy a dome because the wherePawnCanDestroy do not return cells with a dome in them
     * Same for the cells with height == 0
     * The block is only removed, so it is reusable
     * @param buildings is the list of possible buildings
     * @param designatedCell is the cell where the block will be destroyed
     */
    public void destroyBlock(List<Building> buildings, Cell designatedCell) {

        for(Building building : buildings) {

            if(building.getLevel()+1 == designatedCell.getHeight()) {
                designatedCell.destroyRoof(building);
                break;
            }
        }
    }


}
