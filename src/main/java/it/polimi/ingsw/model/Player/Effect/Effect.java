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


    StateInterface getState();

    void changeState(StateInterface state);

    Consequence move(Board gameBoard, Pawn designatedPawn, Cell nextPosition);


    List<Cell> wherePawnCanMove(Board gameBoard, Pawn designatedPawn);


    void forcePawn(Pawn designatedPawn, Cell nextPosition);


    // ======================================================================================


    Consequence build(Pawn designatedPawn, Cell designatedCell, int chosenLevel, List<Building> buildings);


    List<Cell> wherePawnCanBuild(Board gameBoard, Pawn designatedPawn);


    List<Building> getPossibleBuildingOnCell( Board gameBoard, Cell designatedCell);


    // ======================================================================================


    void initPawn(Board gameBoard, Sex sex, Cell cell);


    List<Action> getPossibleActions(Board gameBoard, Pawn designatedPawn);


    List<Cell> getPawnsCoordinates(Board gameBoard);


    void removePawn(Board gameBoard, Pawn designatedPawn);


    void placePawn(Board gameBoard, Pawn designatedPawn, Cell designatedCell);


    // ======================================================================================






}
