package it.polimi.ingsw.model.Player;

import it.polimi.ingsw.model.BoardPack.Board;
import it.polimi.ingsw.model.BoardPack.Building;
import it.polimi.ingsw.model.BoardPack.Cell;

import java.util.ArrayList;
import java.util.List;

public class BuildBeforePlayer extends PlayerDecorator {


    private Boolean hasBuiltBefore;


    // ======================================================================================


    public BuildBeforePlayer(BasicPlayer player) {
        super(player);
        hasBuiltBefore = false;
    }


    // ======================================================================================


    /**
     * This method is based on the status of the player in his turn, so if the player has already built, the method will return
     * as possible action only "move"
     * If this is the start of the player's turn, the method will return the possible action "build" (if possible) and move
     * After the player moved, will be returned only the action "build"
     * For the end of the turn, the method will return the action "finish"
     * @param gameBoard is the board where is played the game
     * @param designatedPawn is the pawn selected by the player for the current turn
     * @return a list of possible actions that the player can do in a specific moment in his turn
     */
    @Override
    public List<String> getPossibleAction(Board gameBoard, Pawn designatedPawn ) {

        List<String> availableActions = new ArrayList<>();
        List<Cell> availableCellsToMove;
        List<Cell> availableCellsToBuild;

        /* se sono entrambi 0 vuol dire che voglio costruire prima di muovere */
        if( player.getNumMove() == 0 && player.getNumBuild() == 1 && !hasBuiltBefore ) {

            availableCellsToMove = player.wherePawnCanMove(gameBoard, designatedPawn);
            availableCellsToBuild = player.wherePawnCanBuild(gameBoard, designatedPawn);

            if(availableCellsToBuild.size() == availableCellsToMove.size() && availableCellsToBuild.size() == 1) {
                for(Cell c : availableCellsToBuild) {
                    if(c.getHeight() < designatedPawn.getHeight()) {
                        availableActions.add("build");
                        break;
                    }
                }
            }
            else if(availableCellsToBuild.size() >= availableCellsToMove.size()) {
                for(Cell c : availableCellsToMove) {
                    if(c.getHeight() <= designatedPawn.getHeight()) {
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
            availableActions.add("finish");
        }


        return availableActions;
    }


    /**
     * The method returns the list of cells where the designated pawn can build
     * This is different from the basic wherePawnCanBuild because the player can build before moving,
     * but if he builds, he cannot move up when moving, so we have to delete some cells in particular cases
     * @param gameBoard is the board where is played the game
     * @param designatedPawn is the pawn selected by the player for the current turn
     * @return a list of cells where the pawn can build in the current turn
     */
    @Override
    public List<Cell> wherePawnCanBuild(Board gameBoard, Pawn designatedPawn) {

        List<Cell> availableCellsToBuild = gameBoard.getCellAvailableToBuild(designatedPawn);
        List<Cell> availableCellsToMove = super.player.wherePawnCanMove(gameBoard, designatedPawn);


        /* se voglio costruire prima di muovere */
        if( player.getNumMove() == 0 && player.getNumBuild() == 1 && !hasBuiltBefore ) {

            if(availableCellsToBuild.size() == availableCellsToMove.size() && availableCellsToBuild.size() == 1) {

                Cell possibleBuild = availableCellsToBuild.get(0);
                availableCellsToBuild.clear();

                if(possibleBuild.getHeight() < designatedPawn.getHeight()) {
                    availableCellsToBuild.add(possibleBuild);
                }

            }
            else if(availableCellsToBuild.size() >= availableCellsToMove.size()) {

                int sameLevelCells = 0;
                int lowerLevelCells = 0;
                Cell sameLevelCell = new Cell();

                for(Cell c : availableCellsToMove) {
                    if(c.getHeight() < designatedPawn.getHeight()) {
                        lowerLevelCells++;
                    }
                    else if(c.getHeight() == designatedPawn.getHeight()) {
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


    /**
     * This method returns a list of cells where the pawn can move
     * This is different from the basic method because if the player builds before moving,
     * he cannot move up in the same turn
     * @param gameBoard is the board where is played the game
     * @param designatedPawn is the pawn selected by the player for the current turn
     * @return a list of cells where the pawn can move in the current turn
     */
    @Override
    public List<Cell> wherePawnCanMove(Board gameBoard, Pawn designatedPawn) {

        List<Cell> availableCellsToMove = super.player.wherePawnCanMove(gameBoard, designatedPawn);

        if( hasBuiltBefore ) {

            availableCellsToMove.removeIf(c -> c.getHeight() > designatedPawn.getHeight());
        }

        if( !super.player.getCanMoveUp() ) {
            availableCellsToMove.removeIf(c -> c.getHeight() - designatedPawn.getHeight() == 1);
        }

        return availableCellsToMove;
    }


    /**
     * This method is different from the basic one because the player can build before the move,
     * so if it happens, we have to set some parameters in order to have a correct move after the first built
     * @param designatedPawn is the pawn selected by the player for the current turn
     * @param designatedCell is the cell where the pawn will build
     * @param chosenLevel is the level that the designatedCell will have after the built
     * @param buildings is a list of every type of building based on the level * not used here *
     */
    @Override
    public void pawnBuild(Pawn designatedPawn, Cell designatedCell, int chosenLevel, List<Building> buildings) {

        super.player.pawnBuild(designatedPawn, designatedCell, chosenLevel, buildings);

        if( player.getNumBuild() == 2 && !hasBuiltBefore ) {
            player.setNumBuild(1);
            hasBuiltBefore = true;
        }

    }


    // ======================================================================================


    /**
     * USED ONLY FOR TESTING
     * @param hasBuiltBefore sets if the player has built before moving
     */
    public void setHasBuiltBefore(Boolean hasBuiltBefore) {
        this.hasBuiltBefore = hasBuiltBefore;
    }


    /**
     * USED ONLY FOR TESTING
     * @return if the player has built before moving
     */
    public boolean getHasBuiltBefore() {
        return this.hasBuiltBefore;
    }


}
