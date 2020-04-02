package it.polimi.ingsw.model.Player;

import it.polimi.ingsw.model.Actions.Action;
import it.polimi.ingsw.model.Actions.BuildAction;
import it.polimi.ingsw.model.Actions.MoveAction;
import it.polimi.ingsw.model.Actions.MoveConsequence;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Sex;

import it.polimi.ingsw.model.BoardPack.*;

import java.awt.*;
import java.util.List;
import java.util.Map;


public interface Player {


    // ======================================================================================


    int getNumMove();


    int getNumBuild();


    String getName();


    Color getColor();


    Card getGodCard();


    Pawn[] getPawns();


    Pawn getPawnInCoordinates(int row, int column);


    Boolean getCanMoveUp();



    // ======================================================================================


    void setCanMoveUp(Boolean canMoveUp);


    void setName(String name);


    void setColor(Color color);


    void setCard(Card card);


    void setNameGod(String nameGod);


    void setNumMove(int numMove);


    void setNumBuild(int numBuild);


    // ======================================================================================


    MoveConsequence movePawn(Board gameBoard, Pawn designatedPawn, Cell nextPosition);


    List<Cell> wherePawnCanMove(Board gameBoard, Pawn designatedPawn);


    void forcePawn(Pawn designatedPawn, Cell nextPosition);


    // ======================================================================================


    void pawnBuild(Pawn designatedPawn, Cell designatedCell, int chosenLevel, List<Building> buildings);


    List<Cell> wherePawnCanBuild(Board gameBoard, Pawn designatedPawn);


    List<Building> getPossibleBuildingOnCell( Board gameBoard, Cell designatedCell, List<Building> buildings );


    // ======================================================================================


    void initPawn(Board gameBoard, Sex sex, Cell cell);


    List<Action> getPossibleActions(Board gameBoard, Pawn designatedPawn);


    List<Cell> getPawnsCoordinates(Board gameBoard);


    void removePawn(Board gameBoard, Pawn designatedPawn);


    void placePawn(Board gameBoard, Pawn designatedPawn, Cell designatedCell);


    void resetNumMove();


    void resetNumBuild();


    // ======================================================================================






}
