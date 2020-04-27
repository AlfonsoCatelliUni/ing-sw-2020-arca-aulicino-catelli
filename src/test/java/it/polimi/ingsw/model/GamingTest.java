package it.polimi.ingsw.model;


import it.polimi.ingsw.JsonHandler;
import it.polimi.ingsw.model.Actions.Action;
import it.polimi.ingsw.model.Actions.BuildAction;
import it.polimi.ingsw.model.Actions.MoveAction;
import it.polimi.ingsw.model.Board.Board;
import it.polimi.ingsw.model.Board.Cell;
import it.polimi.ingsw.model.Player.Card;
import it.polimi.ingsw.model.Player.Effect.*;
import it.polimi.ingsw.model.Player.Player;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GamingTest {

    List<Action> retActions = new ArrayList<>();
    List<Action> correctActions = new ArrayList<>();
    List<Cell> retCells = new ArrayList<>();
    List<Cell> correctCells = new ArrayList<>();

    @Test
    void GameTesting() {

        List<Player> players = new ArrayList<>();

        List<Card> cards = JsonHandler.deserializeCardList();

        //apollo
        Card alfonsoCard = cards.get(0);
        Effect alfonsoEffect = new CanSwitchOpponentEffect(new SwitchEffect(new BasicEffect()));

        Player alfonso = new Player("alfonso", Color.BLUE, alfonsoCard, alfonsoEffect );

        //artemis
        Card massiCard = cards.get(1);
        Effect massiEffect = new MoreMoveEffect(new BasicEffect());

        Player massi = new Player("massi", Color.WHITE, massiCard, massiEffect );

        //athena
        Card giammaCard = cards.get(0);
        Effect giammaEffect = new BlockOpponentEffect(new SwitchEffect(new BasicEffect()));

        Player giamma = new Player("giamma", Color.GREY, giammaCard, giammaEffect );

        players.add(alfonso);
        players.add(massi);
        players.add(giamma);

        Game gameTest = new Game(players);



        retActions = gameTest.getPossibleActions("alfonso", 0,0);
        correctActions.add(new MoveAction());

        Assert.assertEquals(correctActions.get(0).getActionID(), retActions.get(0).getActionID());

        gameTest.movePawn("alfonso", 0,0, 0,1);

        retActions = gameTest.getPossibleActions("alfonso", 0,1);
        correctActions.clear();
        correctActions.add(new BuildAction());

        Assert.assertEquals(correctActions.get(0).getActionID(), retActions.get(0).getActionID());

        gameTest.pawnBuild("alfonso", 0,1, 1,1, 1);


    }



    @Test
    void GameTestingProva() {

        Board gameBoard = new Board();

        Player player = new Player("nome", Color.BLUE, new Card("Apollo", true, "effect", new CanSwitchOpponentEffect(new SwitchEffect(new BasicEffect()))), new BasicEffect());
        player.setEffect(player.getCard().getBaseEffect());

        player.initPawn(gameBoard, gameBoard.getCell(0,0));
        player.move(gameBoard, gameBoard.getPawnByCoordinates(0,0), gameBoard.getCell(1,1));

        player.build(gameBoard.getPawnByCoordinates(1,1), gameBoard.getCell(1,2), 1, gameBoard.getBuildings());


        Board gameBoard2 = new Board();

        List<Card> allCards = JsonHandler.deserializeCardList();

        Card tritonCard = allCards.get(12);

        Player player2 = new Player("prova", Color.BLUE, tritonCard, tritonCard.getBaseEffect());

        player2.initPawn(gameBoard2, gameBoard2.getCell(0,0));

        player2.move(gameBoard2, gameBoard2.getPawnByCoordinates(0,0), gameBoard2.getCell(1,0));

        player2.build(gameBoard2.getPawnByCoordinates(1,0), gameBoard2.getCell(2,0),1,gameBoard2.getBuildings());





    }
}
