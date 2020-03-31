package it.polimi.ingsw.model.Player;

import it.polimi.ingsw.model.BoardPack.Board;
import it.polimi.ingsw.model.BoardPack.Cell;
import it.polimi.ingsw.model.Pawn;

import java.util.ArrayList;
import java.util.List;

public class DoubleMovePlayer extends PlayerDecorator {


    public DoubleMovePlayer(BasicPlayer player) {
        super(player);
    }


    // ======================================================================================


    @Override
    public List<Cell> wherePawnCanMove(Board gameBoard, Pawn designatedPawn) {

        List<Cell> availableCellsToMove = super.wherePawnCanMove(gameBoard, designatedPawn);
        List<Cell> availableCells = new ArrayList<>();


        /* for each available cell in where I can move with one movement I
        * have to check if I can do another movement */
        for (Cell c : availableCellsToMove ) {

            if (!availableCells.contains(c)) {
                availableCells.add(c);
            }

            List<Cell> secondNeighboringCells = gameBoard.getNeighboring(c);
            for (Cell cc : secondNeighboringCells ) {
                if ( cc.getHeight() - c.getHeight() <= 1 && cc.getIsFree() && !availableCells.contains(cc)){
                    availableCells.add(cc);
                }
            }

        }

        if( !super.player.getCanMoveUp() ) {
            availableCells.removeIf(c -> c.getHeight() - designatedPawn.getzPosition() == 1);
        }

        return availableCells;
    }


}
