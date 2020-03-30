package it.polimi.ingsw.model.Player;

import it.polimi.ingsw.model.BoardPack.Board;
import it.polimi.ingsw.model.BoardPack.Cell;
import it.polimi.ingsw.model.Pawn;

import java.util.ArrayList;
import java.util.List;

public class PushPlayer extends PlayerDecorator {


    public PushPlayer(BasicPlayer player) {
        super(player);
    }


    // ======================================================================================


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
            availableCellsToMove.removeIf(c -> c.getHeight() - designatedPawn.getzPosition() == 1);
        }

        return availableCellsToMove;

    }


    @Override
    public int movePawn(Board gameBoard, Pawn designatedPawn, Cell nextPosition) {

        int moveRetEncoded = 0;


        if( nextPosition.getBuilderHere() ) {

            int diffRow = nextPosition.getRowPosition() - designatedPawn.getPosition().getRowPosition();
            int diffColumn = nextPosition.getColumnPosition() - designatedPawn.getPosition().getColumnPosition();

            Pawn oppPawn = nextPosition.getPawnInThisCell();
            Cell oppCell = gameBoard.getCell( nextPosition.getRowPosition()+diffRow, nextPosition.getColumnPosition()+diffColumn );

            removePawn(gameBoard, nextPosition.getPawnInThisCell());

            moveRetEncoded = super.movePawn(gameBoard, designatedPawn, nextPosition);

            super.forcePawn(oppPawn, oppCell);

            placePawn(gameBoard, oppPawn, oppCell);

        }
        else {
            moveRetEncoded = super.movePawn(gameBoard, designatedPawn, nextPosition);
        }

        return moveRetEncoded;
    }



}
