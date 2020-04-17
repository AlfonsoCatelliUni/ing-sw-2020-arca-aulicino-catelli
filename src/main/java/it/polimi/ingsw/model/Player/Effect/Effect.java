package it.polimi.ingsw.model.Player.Effect;

import it.polimi.ingsw.model.Actions.Action;
import it.polimi.ingsw.model.Actions.Consequence;
import it.polimi.ingsw.model.Board.Board;
import it.polimi.ingsw.model.Board.Building;
import it.polimi.ingsw.model.Board.Cell;
import it.polimi.ingsw.model.Player.Pawn;

import it.polimi.ingsw.model.Player.State.StateInterface;
import it.polimi.ingsw.model.Sex;


import java.util.List;

public interface Effect {


    // ======================================================================================
    // MARK : Getter Section


    StateInterface getState();


    List<Cell> getPawnsCoordinates(Board gameBoard);


    // ======================================================================================
    // MARK : Setter Section


    void changeState(StateInterface state);


    // ======================================================================================
    // MARK : Possibilities Control Section


    List<Action> getPossibleActions(Board gameBoard, Pawn designatedPawn);


    List<Cell> wherePawnCanMove(Board gameBoard, Pawn designatedPawn);


    List<Cell> wherePawnCanBuild(Board gameBoard, Pawn designatedPawn);


    List<Building> getPossibleBuildingOnCell( Board gameBoard, Cell designatedCell);


    List<Cell> wherePawnCanForce(Board gameBoard, Pawn designatedPawn);


    List<Cell> wherePawnCanDestroy(Board gameBoard, Pawn designatedPawn);


    // ======================================================================================
    // MARK : Real Actions Section


    Consequence move(Board gameBoard, Pawn designatedPawn, Cell nextPosition);


    Consequence build(Pawn designatedPawn, Cell designatedCell, int chosenLevel, List<Building> buildings);


    void force(Pawn designatedPawn, Cell nextPosition);


    // ======================================================================================
    // MARK : Pawn Placing Section


    void initPawn(Board gameBoard, Sex sex, Cell cell);


    void removePawn(Board gameBoard, Pawn designatedPawn);


    void placePawn(Board gameBoard, Pawn designatedPawn, Cell designatedCell);


    // ======================================================================================



}
