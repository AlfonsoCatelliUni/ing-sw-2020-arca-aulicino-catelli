package it.polimi.ingsw.model.Player;

import it.polimi.ingsw.model.Actions.Consequence;
import it.polimi.ingsw.model.Board.Board;
import it.polimi.ingsw.model.Board.Cell;

import java.util.List;

public class PushPlayer extends PlayerDecorator {


    public PushPlayer(BasicPlayer player) {
        super(player);
    }


    // ======================================================================================


    /**
     * wherePawnCanMove returns also cells occupied by opponent pawns
     * @param gameBoard the game board where the pawn can move on
     * @param designatedPawn the pawn that's designated to move
     * @return the list of cells available to be move
     */
    @Override
    public List<Cell> wherePawnCanMove(Board gameBoard, Pawn designatedPawn) {

        List<Cell> availableCellsToMove = super.wherePawnCanMove(gameBoard, designatedPawn);

        List<Cell> neighboringCells = gameBoard.getNeighboring( designatedPawn.getPosition() );

        int diffRow = 0;
        int diffColumn = 0;

        for ( Cell c : neighboringCells ) {
            if (c.getBuilderHere() && c.getPawnInThisCell().getColor() != designatedPawn.getColor()) {

                diffRow = c.getRowPosition() - designatedPawn.getPosition().getRowPosition();
                diffColumn = c.getColumnPosition() - designatedPawn.getPosition().getColumnPosition();


                if( c.getRowPosition()+diffRow >= 0 && c.getRowPosition()+diffRow <= 4 &&
                        c.getColumnPosition()+diffColumn >= 0 && c.getColumnPosition()+diffColumn <= 4) {
                    if( gameBoard.getCell( c.getRowPosition()+diffRow, c.getColumnPosition()+diffColumn ).getIsFree() ) {
                        availableCellsToMove.add(c);
                    }
                }

            }


        }

        if( !super.player.getCanMoveUp() ) {
            availableCellsToMove.removeIf(c -> c.getHeight() - designatedPawn.getHeight() == 1);
        }

        return availableCellsToMove;

    }


    /**
     * this method move pawn on nextposition cell and if the cell is occupied by a opponent pawn, it pushes this pawn in the same direction if is unoccupied
     * @param gameBoard the board where we have to move the pawn
     * @param designatedPawn the pawn that's designated to move
     * @param nextPosition the cell where to move the pawn
     * @return int encoded by super.movepawn
     */
    @Override
    public Consequence movePawn(Board gameBoard, Pawn designatedPawn, Cell nextPosition) {

        Consequence resultAction;


        if( nextPosition.getBuilderHere() ) {

            int diffRow = nextPosition.getRowPosition() - designatedPawn.getPosition().getRowPosition();
            int diffColumn = nextPosition.getColumnPosition() - designatedPawn.getPosition().getColumnPosition();

            Pawn oppPawn = nextPosition.getPawnInThisCell();
            Cell oppCell = gameBoard.getCell( nextPosition.getRowPosition()+diffRow, nextPosition.getColumnPosition()+diffColumn );

            removePawn(gameBoard, nextPosition.getPawnInThisCell());

            resultAction = super.movePawn(gameBoard, designatedPawn, nextPosition);

            super.forcePawn(oppPawn, oppCell);

            placePawn(gameBoard, oppPawn, oppCell);

        }
        else {
            resultAction = super.movePawn(gameBoard, designatedPawn, nextPosition);
        }

        return resultAction;
    }



}
