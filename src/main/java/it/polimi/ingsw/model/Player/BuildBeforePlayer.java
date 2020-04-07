package it.polimi.ingsw.model.Player;

import it.polimi.ingsw.model.Actions.*;
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
    public List<Action> getPossibleActions(Board gameBoard, Pawn designatedPawn ) {

        List<Action> availableActions = new ArrayList<>();
        List<Cell> availableCellsToBuild;

        /* se sono entrambi 0 vuol dire che voglio costruire prima di muovere */
        if( player.getNumMove() == 0 && player.getNumBuild() == 1 && !hasBuiltBefore ) {

            availableCellsToBuild = player.wherePawnCanBuild(gameBoard, designatedPawn);

            if(availableCellsToBuild.size() > 0) {
                availableActions.add(new BuildAction());
            }

        }

        availableActions.addAll(super.player.getPossibleActions(gameBoard, designatedPawn));


        return availableActions;
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
    public Consequence pawnBuild(Pawn designatedPawn, Cell designatedCell, int chosenLevel, List<Building> buildings) {

        Consequence buildConsequence = super.player.pawnBuild(designatedPawn, designatedCell, chosenLevel, buildings);

        if( player.getNumBuild() == 2 && !hasBuiltBefore ) {
            player.setNumBuild(1);
            hasBuiltBefore = true;
        }

        return buildConsequence;
    }


    // ======================================================================================


    @Override
    public void resetPlayerStatus() {

        super.resetPlayerStatus();

        this.hasBuiltBefore = false;
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
