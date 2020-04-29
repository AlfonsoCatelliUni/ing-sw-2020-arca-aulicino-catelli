package it.polimi.ingsw.model.Player.Effect;

import it.polimi.ingsw.model.Actions.Action;
import it.polimi.ingsw.model.Consequence.Consequence;
import it.polimi.ingsw.model.Board.Board;
import it.polimi.ingsw.model.Board.Building;
import it.polimi.ingsw.model.Board.Cell;
import it.polimi.ingsw.model.Player.Pawn;

import it.polimi.ingsw.model.Player.State.StateEffectInterface;


import java.util.List;

public interface Effect extends Cloneable {


    // ======================================================================================

    //<editor-fold desc="Getter Section">


    StateEffectInterface getState();


    Effect getEffect();


    //</editor-fold>

    // ======================================================================================

    //<editor-fold desc="Setter Section">


    void changeState(StateEffectInterface state);


    //</editor-fold>

    // ======================================================================================

    //<editor-fold desc="Possibilities Control Section">


    List<Action> getPossibleActions(Board gameBoard, Pawn designatedPawn);


    List<Cell> wherePawnCanMove(Board gameBoard, Pawn designatedPawn);


    List<Cell> wherePawnCanBuild(Board gameBoard, Pawn designatedPawn);


    List<Building> getPossibleBuildingOnCell( Board gameBoard, Cell designatedCell);


    List<Cell> getOpponentsNeighboring(Board gameBoard, Pawn designatedPawn);


    List<Cell> wherePawnCanDestroy(Board gameBoard, Pawn designatedPawn);

    //</editor-fold>

    // ======================================================================================

    //<editor-fold desc="Real Actions Section">


    Consequence move(Board gameBoard, Pawn designatedPawn, Cell nextPosition);


    Consequence build(Pawn designatedPawn, Cell designatedCell, int chosenLevel, List<Building> buildings);


    void force(Pawn designatedPawn, Cell nextPosition);


    void destroy(Cell designatedCell, List<Building> buildings);


    //</editor-fold>

    // ======================================================================================

    //<editor-fold desc="Pawn Placing Section">


    void removePawn(Board gameBoard, Pawn designatedPawn);


    void placePawn(Board gameBoard, Pawn designatedPawn, Cell designatedCell);


    //</editor-fold>

    // ======================================================================================

    Effect clone();

    Effect addEffect(Effect e);



}
