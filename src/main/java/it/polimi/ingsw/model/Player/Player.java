package it.polimi.ingsw.model.Player;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Pawn;
import it.polimi.ingsw.model.Sex;

import it.polimi.ingsw.model.BoardPack.*;

import java.util.ArrayList;
import java.util.List;


public interface Player {


    // ======================================================================================


    int getNumMove();


    int getNumBuild();


    String getName();


    Color getColor();


    Card getGodCard();


    Pawn[] getPawns();


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


    int movePawn(Board gameBoard, Pawn designatedPawn, Cell nextPosition);


    List<Cell> wherePawnCanMove(Board gameBoard, Pawn designatedPawn);


    void forcePawn(Pawn designatedPawn, Cell nextPosition);


    // ======================================================================================


    void pawnBuild(Pawn designatedPawn, Cell designatedCell, int chosenLevel, List<Building> buildings);


    List<Cell> wherePawnCanBuild(Board gameBoard, Pawn designatedPawn);


    List<Building> getPossibleBuildingOnCell( Board gameBoard, Cell designatedCell, List<Building> buildings );


    // ======================================================================================


    void initPawn(Board gameBoard, Sex sex, Cell cell);


    List<String> getPossibleAction(Board gameBoard, Pawn designatedPawn);


    List<Cell> getPawnsCoordinates(Board gameBoard);


    void removePawn(Board gameBoard, Pawn designatedPawn);


    void placePawn(Board gameBoard, Pawn designatedPawn, Cell designatedCell);


    void resetNumMove();


    void resetNumBuild();


    // ======================================================================================






}