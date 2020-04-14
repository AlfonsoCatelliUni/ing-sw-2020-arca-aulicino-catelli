package it.polimi.ingsw.model.Player;

import it.polimi.ingsw.model.Actions.Action;
import it.polimi.ingsw.model.Actions.Consequence;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Sex;

import it.polimi.ingsw.model.Board.*;

import java.util.List;

/**
 * interface according with pattern decorator of the player
 */
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


    void setNumMove(int numMove);


    void setNumBuild(int numBuild);


    void resetPlayerStatus();


    // ======================================================================================


    Consequence movePawn(Board gameBoard, Pawn designatedPawn, Cell nextPosition);


    List<Cell> wherePawnCanMove(Board gameBoard, Pawn designatedPawn);


    void forcePawn(Pawn designatedPawn, Cell nextPosition);


    // ======================================================================================


    Consequence pawnBuild(Pawn designatedPawn, Cell designatedCell, int chosenLevel, List<Building> buildings);


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
