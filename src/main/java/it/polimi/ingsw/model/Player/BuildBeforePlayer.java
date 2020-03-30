package it.polimi.ingsw.model.Player;

import it.polimi.ingsw.model.BoardPack.Board;
import it.polimi.ingsw.model.BoardPack.Building;
import it.polimi.ingsw.model.BoardPack.Cell;
import it.polimi.ingsw.model.Pawn;

import java.util.ArrayList;
import java.util.List;

public class BuildBeforePlayer extends PlayerDecorator {

    private Boolean hasBuiltBefore;


    public BuildBeforePlayer(BasicPlayer player) {
        super(player);
        hasBuiltBefore = false;
    }


    @Override
    public List<String> getPossibleAction(Board gameBoard, Pawn designatedPawn ) {

        List<String> availableActions = new ArrayList<>();
        List<Cell> availableCellsToMove = new ArrayList<>();
        List<Cell> availableCellsToBuild = new ArrayList<>();

        /* se sono entrambi 0 vuol dire che voglio costruire prima di muovere */
        if( player.getNumMove() == 0 && player.getNumBuild() == 1 && !hasBuiltBefore ) {

            availableCellsToMove = player.wherePawnCanMove(gameBoard, designatedPawn);
            availableCellsToBuild = player.wherePawnCanBuild(gameBoard, designatedPawn);

            if(availableCellsToBuild.size() == availableCellsToMove.size() && availableCellsToBuild.size() == 1) {
                for(Cell c : availableCellsToBuild) {
                    if(c.getHeight() < designatedPawn.getzPosition()) {
                        availableActions.add("build");
                        break;
                    }
                }
            }
            else if(availableCellsToBuild.size() >= availableCellsToMove.size()) {
                for(Cell c : availableCellsToMove) {
                    if(c.getHeight() <= designatedPawn.getzPosition()) {
                        availableActions.add("build");
                        break;
                    }
                }
            }


        }

        /* se non ha ancora mosso allora devo dargli la possibilità di farlo */
        if ( player.getNumMove() == 0 ) {
            availableActions.add("move");
        }
        /* se ho mosso ma non costruito allora devo dare la possibilita di costruire */
        if ( player.getNumBuild() == 0 ) {
            availableActions.add("build");
        }
        if( player.getNumMove() == 1 && player.getNumBuild() == 1 ) {
            hasBuiltBefore = false;
            availableActions.add("Finish");
        }


        return availableActions;
    }


    @Override
    public List<Cell> wherePawnCanBuild(Board gameBoard, Pawn designatedPawn) {

        List<Cell> availableCellsToBuild = gameBoard.getCellAvailableToBuild(designatedPawn);
        List<Cell> availableCellsToMove = player.wherePawnCanMove(gameBoard, designatedPawn);


        /* se voglio costruire prima di muovere */
        if( player.getNumMove() == 0 && player.getNumBuild() == 1 && !hasBuiltBefore ) {

            if(availableCellsToBuild.size() == availableCellsToMove.size() && availableCellsToBuild.size() == 1) {
                for(Cell c : availableCellsToBuild) {
                    if(c.getHeight() < designatedPawn.getzPosition()) {
                        availableCellsToBuild.clear();
                        availableCellsToBuild.add(c);
                        break;
                    }
                }
            }
            else if(availableCellsToBuild.size() >= availableCellsToMove.size()) {

                int sameLevelCells = 0;
                int lowerLevelCells = 0;
                Cell sameLevelCell = new Cell();

                for(Cell c : availableCellsToMove) {
                    if(c.getHeight() < designatedPawn.getzPosition()) {
                        lowerLevelCells++;
                    }
                    else if(c.getHeight() == designatedPawn.getzPosition()) {
                        sameLevelCells++;
                        sameLevelCell = c;

                    }
                }

                if(sameLevelCells == 1 && lowerLevelCells == 0) {
                    availableCellsToBuild.remove(sameLevelCell);
                }

            }
        }
        else if( player.getNumMove() == 1 ) {
            availableCellsToBuild = player.wherePawnCanBuild(gameBoard, designatedPawn);
        }


        return availableCellsToBuild;
    }


    @Override
    public List<Cell> wherePawnCanMove(Board gameBoard, Pawn designatedPawn) {

        List<Cell> availableCellsToMove = super.player.wherePawnCanMove(gameBoard, designatedPawn);

        if( hasBuiltBefore ) {

            availableCellsToMove.removeIf(c -> c.getHeight() > designatedPawn.getzPosition());
        }

        if( !super.player.getCanMoveUp() ) {
            availableCellsToMove.removeIf(c -> c.getHeight() - designatedPawn.getzPosition() == 1);
        }

        return availableCellsToMove;
    }


    @Override
    public void pawnBuild(Pawn designatedPawn, Cell designatedCell, int chosenLevel, List<Building> buildings) {

        super.player.pawnBuild(designatedPawn, designatedCell, chosenLevel, buildings);

        if( player.getNumBuild() == 2 && !hasBuiltBefore ) {
            player.setNumBuild(1);
            hasBuiltBefore = true;
        }

    }

    /**
     * USED ONLY FOR TESTING
     * @param hasBuiltBefore sets if the player has built before moving
     */
    public void setHasBuiltBefore(Boolean hasBuiltBefore) {
        this.hasBuiltBefore = hasBuiltBefore;
    }
}
