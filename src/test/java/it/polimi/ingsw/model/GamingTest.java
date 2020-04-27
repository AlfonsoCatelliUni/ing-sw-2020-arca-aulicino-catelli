package it.polimi.ingsw.model;


import it.polimi.ingsw.JsonHandler;
import it.polimi.ingsw.model.Actions.Action;
import it.polimi.ingsw.model.Actions.BuildAction;
import it.polimi.ingsw.model.Actions.FinishAction;
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
    void GameTestingApolloArtemisAthena() {

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

        gameTest.initializePawn("alfonso", 0,0);
        gameTest.initializePawn("alfonso", 1,1);
        gameTest.initializePawn("massi", 2,2);
        gameTest.initializePawn("massi", 3,3);
        gameTest.initializePawn("giamma", 4,4);
        gameTest.initializePawn("giamma", 2,3);

        /*

                ╔═══╦════╦════╦════╦════╦════╗
                ║   ║ 0  ║ 1  ║ 2  ║ 3  ║ 4  ║
                ╠═══╬════╬════╬════╬════╬════╣
                ║ 0 ║ A0 ║ 0  ║ 0  ║ 0  ║ 0  ║
                ║ 1 ║ 0  ║ a  ║ 0  ║ 0  ║ 0  ║
                ║ 2 ║ 0  ║ 0  ║ M  ║ g  ║ 0  ║
                ║ 3 ║ 0  ║ 0  ║ 0  ║ m  ║ 0  ║
                ║ 4 ║ 0  ║ 0  ║ 0  ║ 0  ║ G  ║
                ╚═══╩════╩════╩════╩════╩════╝


        */

        //alfonso's turn

        retActions = gameTest.getPossibleActions("alfonso", 0,0);
        correctActions.add(new MoveAction());

        Assert.assertEquals(correctActions.get(0).getActionID(), retActions.get(0).getActionID());

        gameTest.movePawn("alfonso", 0,0, 0,1);

        /*

                ╔═══╦════╦════╦════╦════╦════╗
                ║   ║ 0  ║ 1  ║ 2  ║ 3  ║ 4  ║
                ╠═══╬════╬════╬════╬════╬════╣
                ║ 0 ║ 0  ║ A0 ║ 0  ║ 0  ║ 0  ║
                ║ 1 ║ 0  ║ a  ║ 0  ║ 0  ║ 0  ║
                ║ 2 ║ 0  ║ 0  ║ M  ║ g  ║ 0  ║
                ║ 3 ║ 0  ║ 0  ║ 0  ║ m  ║ 0  ║
                ║ 4 ║ 0  ║ 0  ║ 0  ║ 0  ║ G  ║
                ╚═══╩════╩════╩════╩════╩════╝


        */

        retActions = gameTest.getPossibleActions("alfonso", 0,1);
        correctActions.clear();
        correctActions.add(new BuildAction());

        Assert.assertEquals(correctActions.get(0).getActionID(), retActions.get(0).getActionID());

        gameTest.pawnBuild("alfonso", 0,1, 1,0, 0);


        /*

                ╔═══╦════╦════╦════╦════╦════╗
                ║   ║ 0  ║ 1  ║ 2  ║ 3  ║ 4  ║
                ╠═══╬════╬════╬════╬════╬════╣
                ║ 0 ║ 0  ║ A0 ║ 0  ║ 0  ║ 0  ║
                ║ 1 ║ 1  ║ a  ║ 0  ║ 0  ║ 0  ║
                ║ 2 ║ 0  ║ 0  ║ M  ║ g  ║ 0  ║
                ║ 3 ║ 0  ║ 0  ║ 0  ║ m  ║ 0  ║
                ║ 4 ║ 0  ║ 0  ║ 0  ║ 0  ║ G  ║
                ╚═══╩════╩════╩════╩════╩════╝


        */

        retActions = gameTest.getPossibleActions("alfonso", 0,1);
        correctActions.clear();
        correctActions.add(new FinishAction());

        Assert.assertEquals(correctActions.get(0).getActionID(), retActions.get(0).getActionID());

        gameTest.resetPlayerStatus("alfonso");

        //massi's turn

        retActions = gameTest.getPossibleActions("massi", 2,2);
        correctActions.clear();
        correctActions.add(new MoveAction());

        Assert.assertEquals(correctActions.get(0).getActionID(), retActions.get(0).getActionID());


        gameTest.movePawn("massi", 2,2, 2,1);

        /*

                ╔═══╦════╦════╦════╦════╦════╗
                ║   ║ 0  ║ 1  ║ 2  ║ 3  ║ 4  ║
                ╠═══╬════╬════╬════╬════╬════╣
                ║ 0 ║ 0  ║ A0 ║ 0  ║ 0  ║ 0  ║
                ║ 1 ║ 1  ║ a  ║ 0  ║ 0  ║ 0  ║
                ║ 2 ║ 0  ║ M  ║ 0  ║ g  ║ 0  ║
                ║ 3 ║ 0  ║ 0  ║ 0  ║ m  ║ 0  ║
                ║ 4 ║ 0  ║ 0  ║ 0  ║ 0  ║ G  ║
                ╚═══╩════╩════╩════╩════╩════╝


        */

        retActions = gameTest.getPossibleActions("massi", 2,1);
        correctActions.clear();
        correctActions.add(new MoveAction());
        correctActions.add(new BuildAction());

        Assert.assertEquals(correctActions.size(), retActions.size());

        for (int i = 0; i < correctActions.size(); i++) {
            Assert.assertEquals(correctActions.get(i).getActionID(), retActions.get(i).getActionID());

        }

        gameTest.movePawn("massi", 2,1,3,1);

        /*

                ╔═══╦════╦════╦════╦════╦════╗
                ║   ║ 0  ║ 1  ║ 2  ║ 3  ║ 4  ║
                ╠═══╬════╬════╬════╬════╬════╣
                ║ 0 ║ 0  ║ A0 ║ 0  ║ 0  ║ 0  ║
                ║ 1 ║ 1  ║ a  ║ 0  ║ 0  ║ 0  ║
                ║ 2 ║ 0  ║ 0  ║ 0  ║ g  ║ 0  ║
                ║ 3 ║ 0  ║ M  ║ 0  ║ m  ║ 0  ║
                ║ 4 ║ 0  ║ 0  ║ 0  ║ 0  ║ G  ║
                ╚═══╩════╩════╩════╩════╩════╝


        */


        retActions = gameTest.getPossibleActions("massi", 3,1);
        correctActions.clear();
        correctActions.add(new BuildAction());

        Assert.assertEquals(correctActions.size(), retActions.size());

        for (int i = 0; i < correctActions.size(); i++) {
            Assert.assertEquals(correctActions.get(i).getActionID(), retActions.get(i).getActionID());

        }

        gameTest.pawnBuild("massi", 3,1,4,0,1);

        /*

                ╔═══╦════╦════╦════╦════╦════╗
                ║   ║ 0  ║ 1  ║ 2  ║ 3  ║ 4  ║
                ╠═══╬════╬════╬════╬════╬════╣
                ║ 0 ║ 0  ║ A0 ║ 0  ║ 0  ║ 0  ║
                ║ 1 ║ 1  ║ a  ║ 0  ║ 0  ║ 0  ║
                ║ 2 ║ 0  ║ 0  ║ 0  ║ g  ║ 0  ║
                ║ 3 ║ 0  ║ M  ║ 0  ║ m  ║ 0  ║
                ║ 4 ║ 1  ║ 0  ║ 0  ║ 0  ║ G  ║
                ╚═══╩════╩════╩════╩════╩════╝


        */

        retActions = gameTest.getPossibleActions("massi", 3,1);
        correctActions.clear();
        correctActions.add(new FinishAction());

        Assert.assertEquals(correctActions.size(), retActions.size());

        for (int i = 0; i < correctActions.size(); i++) {
            Assert.assertEquals(correctActions.get(i).getActionID(), retActions.get(i).getActionID());

        }

        gameTest.resetPlayerStatus("massi");

        //giamma's turn

        retActions = gameTest.getPossibleActions("giamma", 2,3);
        correctActions.clear();
        correctActions.add(new MoveAction());

        Assert.assertEquals(correctActions.size(), retActions.size());

        for (int i = 0; i < correctActions.size(); i++) {
            Assert.assertEquals(correctActions.get(i).getActionID(), retActions.get(i).getActionID());

        }

        gameTest.movePawn("giamma", 2,3,1,2);

        /*

                ╔═══╦════╦════╦════╦════╦════╗
                ║   ║ 0  ║ 1  ║ 2  ║ 3  ║ 4  ║
                ╠═══╬════╬════╬════╬════╬════╣
                ║ 0 ║ 0  ║ A0 ║ 0  ║ 0  ║ 0  ║
                ║ 1 ║ 1  ║ a  ║ g  ║ 0  ║ 0  ║
                ║ 2 ║ 0  ║ 0  ║ 0  ║ 0  ║ 0  ║
                ║ 3 ║ 0  ║ M  ║ 0  ║ m  ║ 0  ║
                ║ 4 ║ 1  ║ 0  ║ 0  ║ 0  ║ G  ║
                ╚═══╩════╩════╩════╩════╩════╝


        */

        retActions = gameTest.getPossibleActions("giamma", 1,2);
        correctActions.clear();
        correctActions.add(new BuildAction());

        Assert.assertEquals(correctActions.size(), retActions.size());

        for (int i = 0; i < correctActions.size(); i++) {
            Assert.assertEquals(correctActions.get(i).getActionID(), retActions.get(i).getActionID());

        }

        gameTest.pawnBuild("giamma", 1,2, 0,2,1);


        /*

                ╔═══╦════╦════╦════╦════╦════╗
                ║   ║ 0  ║ 1  ║ 2  ║ 3  ║ 4  ║
                ╠═══╬════╬════╬════╬════╬════╣
                ║ 0 ║ 0  ║ A0 ║ 1  ║ 0  ║ 0  ║
                ║ 1 ║ 1  ║ a0 ║ g0 ║ 0  ║ 0  ║
                ║ 2 ║ 0  ║ 0  ║ 0  ║ 0  ║ 0  ║
                ║ 3 ║ 0  ║ M0 ║ 0  ║ m0 ║ 0  ║
                ║ 4 ║ 1  ║ 0  ║ 0  ║ 0  ║ G0 ║
                ╚═══╩════╩════╩════╩════╩════╝


        */


        retActions = gameTest.getPossibleActions("giamma", 1,2);
        correctActions.clear();
        correctActions.add(new FinishAction());

        Assert.assertEquals(correctActions.size(), retActions.size());

        for (int i = 0; i < correctActions.size(); i++) {
            Assert.assertEquals(correctActions.get(i).getActionID(), retActions.get(i).getActionID());

        }

        gameTest.resetPlayerStatus("giamma");


        //alfonso's turn

        retActions = gameTest.getPossibleActions("alfonso", 0,1);
        correctActions.clear();
        correctActions.add(new MoveAction());

        Assert.assertEquals(correctActions.size(), retActions.size());

        for (int i = 0; i < correctActions.size(); i++) {
            Assert.assertEquals(correctActions.get(i).getActionID(), retActions.get(i).getActionID());

        }

        gameTest.movePawn("alfonso", 0,1, 1,2);

        /*

                ╔═══╦════╦════╦════╦════╦════╗
                ║   ║ 0  ║ 1  ║ 2  ║ 3  ║ 4  ║
                ╠═══╬════╬════╬════╬════╬════╣
                ║ 0 ║ 0  ║ g0 ║ 1  ║ 0  ║ 0  ║
                ║ 1 ║ 1  ║ a0 ║ A0 ║ 0  ║ 0  ║
                ║ 2 ║ 0  ║ 0  ║ 0  ║ 0  ║ 0  ║
                ║ 3 ║ 0  ║ M0 ║ 0  ║ m0 ║ 0  ║
                ║ 4 ║ 1  ║ 0  ║ 0  ║ 0  ║ G0 ║
                ╚═══╩════╩════╩════╩════╩════╝

        */


        retActions = gameTest.getPossibleActions("alfonso", 1,2);
        correctActions.clear();
        correctActions.add(new BuildAction());

        Assert.assertEquals(correctActions.size(), retActions.size());

        for (int i = 0; i < correctActions.size(); i++) {
            Assert.assertEquals(correctActions.get(i).getActionID(), retActions.get(i).getActionID());

        }


    }


}
