package it.polimi.ingsw.model.Player;

import it.polimi.ingsw.model.BoardPack.Board;
import it.polimi.ingsw.model.BoardPack.Building;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Sex;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DownTwoPlayerTest {

    @Test
    void movePawn() {
        Board gameBoard = new Board();
        Player player = new DownTwoPlayer(new BasicPlayer("test", Color.GREY, "Pan"));
        player.initPawn(gameBoard, Sex.MALE, gameBoard.getCell(0,0));
        gameBoard.getCell(1,1).buildOnThisCell(new Building(1,22));
        gameBoard.getCell(1,1).buildOnThisCell(new Building(2,22));
        player.getPawns()[0].moveTo(gameBoard.getCell(1,1));

        //int num = player.movePawn(gameBoard,player.getPawns()[0],gameBoard.getCell(0,0));
        int num = 0;

        assertEquals(2,num);



    }
}