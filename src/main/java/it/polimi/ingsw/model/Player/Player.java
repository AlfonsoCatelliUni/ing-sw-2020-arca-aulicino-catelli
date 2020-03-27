package it.polimi.ingsw.model.Player;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Pawn;
import it.polimi.ingsw.model.Sex;

import it.polimi.ingsw.model.BoardPack.*;

import java.util.ArrayList;


public interface Player {


    void movePawn(Board gameBoard, Pawn designatedPawn, Cell nextPosition);


    void pawnBuild(Pawn designatedPawn, Cell designatedCell, int chosenLevel, ArrayList<Building> buildings);


    void forcePawn(Pawn designatedPawn, Cell nextPosition);


    void initPawn(Board gameBoard, Sex sex, Cell cell);


    ArrayList<Cell> wherePawnCanMove(Board gameBoard, Pawn designatedPawn);


    ArrayList<Cell> wherePawnCanBuild(Board gameBoard, Pawn designatedPawn);


    ArrayList<Building> getPossibleBuildingOnCell( Board gameBoard, Cell designatedCell, ArrayList<Building> buildings );


    void removePawn(Board gameBoard, Pawn designatedPawn);


    void placePawn(Board gameBoard, Pawn designatedPawn, Cell designatedCell);


    ArrayList<String> getPossibleAction();


    String getName();


    Color getColor();


    void setName(String name);


    void setColor(Color color);


    void setCard(Card card);


    Pawn[] getPawns();


}
