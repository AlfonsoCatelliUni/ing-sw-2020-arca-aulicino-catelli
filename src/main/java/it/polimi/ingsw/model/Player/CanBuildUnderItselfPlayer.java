package it.polimi.ingsw.model.Player;

import it.polimi.ingsw.model.Actions.Consequence;
import it.polimi.ingsw.model.Actions.NoConsequence;
import it.polimi.ingsw.model.Board.Board;
import it.polimi.ingsw.model.Board.Building;
import it.polimi.ingsw.model.Board.Cell;

import java.util.List;

public class CanBuildUnderItselfPlayer extends PlayerDecorator {


    public CanBuildUnderItselfPlayer(BasicPlayer player) {
        super(player);
    }


    // ======================================================================================


    /**
     * this method calculates all the possible cells where the designated pawn can build,
     * adding the cell where the pawn itself is located, because with this decorator
     * you can build under your own pawn
     * @param gameBoard the board
     * @param designatedPawn the pawn that have to build
     * @return the list of cells
     */
    @Override
    public List<Cell> wherePawnCanBuild(Board gameBoard, Pawn designatedPawn) {
        List<Cell> cellsAvailableToBuild = super.wherePawnCanBuild(gameBoard, designatedPawn);

        if(designatedPawn.getHeight() <= 2) {
            cellsAvailableToBuild.add(designatedPawn.getPosition());
        }

        return cellsAvailableToBuild;
    }


    /**
     * this method actually build the block of the chosen level in the designated cell
     * but if the player has decided to build a block under his own pawn then I have to
     * force the pawn up one block (the player does not win by forcing from 2nd to 3hd level)
     * @param designatedPawn the pawn that build the block
     * @param designatedCell the cell where the pawn build the block
     * @param chosenLevel the level of the new block
     * @param buildings the list of all blocks
     * @return the consequence of the build action
     */
    @Override
    public Consequence pawnBuild(Pawn designatedPawn, Cell designatedCell, int chosenLevel, List<Building> buildings) {

        super.pawnBuild(designatedPawn, designatedCell, chosenLevel, buildings);

        /* se costruisco sotto di me devo solamente cambiare
         * la mia altezza e poi mi forzo la mossa */
        if(designatedCell.equals(designatedPawn.getPosition())) {
            forcePawn(designatedPawn, designatedCell);
        }


        return new NoConsequence();
    }


}
