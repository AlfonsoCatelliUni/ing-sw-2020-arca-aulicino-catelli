package it.polimi.ingsw;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.events.CTSEvents.*;
import it.polimi.ingsw.events.ServerToClientEvent;
import it.polimi.ingsw.model.Actions.Action;
import it.polimi.ingsw.model.Actions.BuildAction;
import it.polimi.ingsw.model.Actions.FinishAction;
import it.polimi.ingsw.model.Actions.MoveAction;
import it.polimi.ingsw.model.Board.Building;
import it.polimi.ingsw.model.Board.Cell;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player.Card;
import it.polimi.ingsw.model.Player.Effect.*;
import it.polimi.ingsw.model.Player.Player;
import it.polimi.ingsw.server.Connection;
import it.polimi.ingsw.view.server.VirtualView;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;


public class MatchSimulationTest  {


    List<Action> retActions;
    List<Action> correctActions;
    List<Cell> retCells;
    List<Cell> correctCells;

    VirtualView virtualView;

    Controller controller;

    String card1;

    String card2;

    String card3;

    String effect1;

    String effect2;

    String effect3;

    String player1;

    String player2;

    String player3;


    @BeforeEach
    void setUp() {

        retActions = new ArrayList<>();

        correctActions = new ArrayList<>();

        retCells = new ArrayList<>();

        correctCells = new ArrayList<>();

        virtualView = new VirtualView();

        controller = new Controller(virtualView);

        virtualView.addObserver(controller);

        player1 = "Alfantasy";

        player2 = "Scoiattolo";

        player3 = "Mascarpone";

    }

    void MVCPreGameStartSetUp() {


        virtualView.update(new NewConnectionEvent(player1));

        virtualView.update(new ChosenPlayerNumberEvent(player1, 3));

        virtualView.update(new NewConnectionEvent(player2));

        virtualView.update(new NewConnectionEvent(player3));

        controller.getPreGameLobby().getPickedCards().clear();

        /*

              ╔═══╦════╦════╦════╦════╦════╗
              ║   ║ 0  ║ 1  ║ 2  ║ 3  ║ 4  ║
              ╠═══╬════╬════╬════╬════╬════╣
              ║ 0 ║ A0 ║ 0  ║ 0  ║ 0  ║ 0  ║
              ║ 1 ║ 0  ║ a0 ║ 0  ║ 0  ║ 0  ║
              ║ 2 ║ 0  ║ 0  ║ S0 ║ m0 ║ 0  ║
              ║ 3 ║ 0  ║ 0  ║ 0  ║ s0 ║ 0  ║
              ║ 4 ║ 0  ║ 0  ║ 0  ║ 0  ║ M0 ║
              ╚═══╩════╩════╩════╩════╩════╝


      */


    }

    @Test
    void MVCPreGameStart() {

        String player1 = "Alfantasy";

        String player2 = "Scoiattolo";

        String player3 = "Mascarpone";


        virtualView.update(new NewConnectionEvent(player1));

        System.out.println("Should print a SuccessfullyConnectedEvent with attributes:\n" + player1 + "\n" + "Alfantasy\n");

        System.out.println("Should print a FirstConnectionEvent with attributes:\n" + player1 + "\n");


        virtualView.update(new ChosenPlayerNumberEvent(player1, 3));

        virtualView.update(new NewConnectionEvent(player2));

        System.out.println("Should print a SuccessfullyConnectedEvent with attributes:\n" + player1 + ", " + player2 + "\n" + player2 + "\n");

        virtualView.update(new NewConnectionEvent(player3));

        System.out.println("Should print a SuccessfullyConnectedEvent with attributes:\n" + player1 + ", " + player2 + ", " + player3 + "\n" + player3 + "\n");

        System.out.println("The virtualView sends a CloseWaitingRoomEvent to everybody\n");

        card1 = controller.getPreGameLobby().getPickedCards().get(0).getName();
        card2 = controller.getPreGameLobby().getPickedCards().get(1).getName();
        card3 = controller.getPreGameLobby().getPickedCards().get(2).getName();

        effect1 = controller.getPreGameLobby().getPickedCards().get(0).getEffectDescription();
        effect2 = controller.getPreGameLobby().getPickedCards().get(1).getEffectDescription();
        effect3 = controller.getPreGameLobby().getPickedCards().get(2).getEffectDescription();


        System.out.println("Should print a GivePossibleCardEvent with attributes:\n" + player1 + "\n" + card1 + ", " + card2 + ", " + card3 + "\n" + effect1 + ",\n " + effect2 + ",\n " + effect3 + "\n" + "true" + "\n");

        virtualView.update(new ChosenCardEvent(player1,card1));

        System.out.println("Should print a GivePossibleCardEvent with attributes:\n" + player2 + "\n" + card2 + ", " + card3 + "\n" + effect2 + ",\n " + effect3 + "\n" + "true" + "\n");

        virtualView.update(new ChosenCardEvent(player2,card2));

        System.out.println("Should print a GivePossibleCardEvent with attributes:\n" + player3 + "\n" + card3 + "\n" + effect3 + "\n" + "true" + "\n");

        virtualView.update(new ChosenCardEvent(player3, card3));

        System.out.println("Should print a AskInitPawnsEvent with attributes:\n" + player1 + "\n" + "true\n" + "info = []\n");

        virtualView.update(new ChosenInitialPawnCellEvent(player1, 0,0,1,1));

        System.out.println("Should print a AskInitPawnsEvent with attributes:\n" + player2 + "\n" + "true\n" + "info = [0,0;1,1]\n");

        virtualView.update(new ChosenInitialPawnCellEvent(player2, 2,2,3,3));

        System.out.println("Should print a AskInitPawnsEvent with attributes:\n" + player3 + "\n" + "true\n" + "info = [0,0;1,1;2,2;3,3]\n");

        virtualView.update(new ChosenInitialPawnCellEvent(player3, 4,4,2,3));

        System.out.println("Should print a AskWhichPawnsUseEvent with attributes:\n" + player1 + "\n" + "true\n" + "info = [0,0;1,1]\n");

        /*

              ╔═══╦════╦════╦════╦════╦════╗
              ║   ║ 0  ║ 1  ║ 2  ║ 3  ║ 4  ║
              ╠═══╬════╬════╬════╬════╬════╣
              ║ 0 ║ A0 ║ 0  ║ 0  ║ 0  ║ 0  ║
              ║ 1 ║ 0  ║ a0 ║ 0  ║ 0  ║ 0  ║
              ║ 2 ║ 0  ║ 0  ║ M0 ║ g0 ║ 0  ║
              ║ 3 ║ 0  ║ 0  ║ 0  ║ m0 ║ 0  ║
              ║ 4 ║ 0  ║ 0  ║ 0  ║ 0  ║ G0 ║
              ╚═══╩════╩════╩════╩════╩════╝


      */

    }

    @Test
    void MVCGameStart() {

        MVCPreGameStartSetUp();

        Card Apollo = JsonHandler.deserializeCardList().get(0);

        Card Artemis = JsonHandler.deserializeCardList().get(1);

        Card Athena = JsonHandler.deserializeCardList().get(2);

        controller.getPreGameLobby().getPickedCards().add(Apollo);
        controller.getPreGameLobby().getPickedCards().add(Artemis);
        controller.getPreGameLobby().getPickedCards().add(Athena);


        virtualView.update(new ChosenCardEvent(player1,Apollo.getName()));

        virtualView.update(new ChosenCardEvent(player2,Artemis.getName()));

        virtualView.update(new ChosenCardEvent(player3, Athena.getName()));


        virtualView.update(new ChosenInitialPawnCellEvent(player1, 0,0,1,1));


        virtualView.update(new ChosenInitialPawnCellEvent(player2, 2,2,3,3));


        virtualView.update(new ChosenInitialPawnCellEvent(player3, 4,4,2,3));

        List<Building> buildings = controller.getGame().getGameBoard().getBuildings();

        //player1's turn

        virtualView.update(new ChosenPawnToUseEvent(player1,0,0));

        retActions = controller.getGame().getPossibleActions(player1, 0,0);
        correctActions.add(new MoveAction());

        Assert.assertEquals(correctActions.get(0).getActionID(), retActions.get(0).getActionID());

        virtualView.update(new ChosenMoveActionEvent(player1, "Move", 0,0));

        correctCells.add(controller.getGame().getGameBoard().getCell(1,0));
        correctCells.add(controller.getGame().getGameBoard().getCell(0,1));

        retCells = controller.getGame().wherePawnCanMove(player1, 0,0);

        correctCells.sort(Comparator.comparingInt(Cell::getRowPosition).thenComparingInt(Cell::getColumnPosition));
        retCells.sort(Comparator.comparingInt(Cell::getRowPosition).thenComparingInt(Cell::getColumnPosition));


        assertEquals(correctCells.size(), retCells.size());

        assertEquals(correctCells, retCells);

        virtualView.update(new ChosenCellToMoveEvent(player1, 0,0,0,1));
        /*

                   ╔═══╦════╦════╦════╦════╦════╗
                   ║   ║ 0  ║ 1  ║ 2  ║ 3  ║ 4  ║
                   ╠═══╬════╬════╬════╬════╬════╣
                   ║ 0 ║ 0  ║ A0 ║ 0  ║ 0  ║ 0  ║
                   ║ 1 ║ 0  ║ a0 ║ 0  ║ 0  ║ 0  ║
                   ║ 2 ║ 0  ║ 0  ║ S0 ║ m0 ║ 0  ║
                   ║ 3 ║ 0  ║ 0  ║ 0  ║ s0 ║ 0  ║
                   ║ 4 ║ 0  ║ 0  ║ 0  ║ 0  ║ M0 ║
                   ╚═══╩════╩════╩════╩════╩════╝


           */

        retActions = controller.getGame().getPossibleActions(player1, 0,1);
        correctActions.clear();
        correctActions.add(new BuildAction());

        Assert.assertEquals(correctActions.get(0).getActionID(), retActions.get(0).getActionID());

        virtualView.update(new ChosenBuildActionEvent(player1, "Build", 0,1));

        correctCells.clear();
        correctCells.add(controller.getGame().getGameBoard().getCell(0,0));
        correctCells.add(controller.getGame().getGameBoard().getCell(1,0));
        correctCells.add(controller.getGame().getGameBoard().getCell(1,2));
        correctCells.add(controller.getGame().getGameBoard().getCell(0,2));


        retCells = controller.getGame().wherePawnCanBuild(player1, 0,1);

        correctCells.sort(Comparator.comparingInt(Cell::getRowPosition).thenComparingInt(Cell::getColumnPosition));
        retCells.sort(Comparator.comparingInt(Cell::getRowPosition).thenComparingInt(Cell::getColumnPosition));


        assertEquals(correctCells.size(), retCells.size());

        assertEquals(correctCells, retCells);


        virtualView.update(new ChosenCellToBuildEvent(player1,0,1,1,0));

        virtualView.update(new ChosenBuildingEvent(player1, 1,0,1,1,0));

        /*

                   ╔═══╦════╦════╦════╦════╦════╗
                   ║   ║ 0  ║ 1  ║ 2  ║ 3  ║ 4  ║
                   ╠═══╬════╬════╬════╬════╬════╣
                   ║ 0 ║ 0  ║ A0 ║ 0  ║ 0  ║ 0  ║
                   ║ 1 ║ 1  ║ a0 ║ 0  ║ 0  ║ 0  ║
                   ║ 2 ║ 0  ║ 0  ║ S0 ║ m0 ║ 0  ║
                   ║ 3 ║ 0  ║ 0  ║ 0  ║ s0 ║ 0  ║
                   ║ 4 ║ 0  ║ 0  ║ 0  ║ 0  ║ M0 ║
                   ╚═══╩════╩════╩════╩════╩════╝


           */

        retActions = controller.getGame().getPossibleActions(player1, 0,1);
        correctActions.clear();
        correctActions.add(new FinishAction());
        Assert.assertEquals(correctActions.get(0).getActionID(), retActions.get(0).getActionID());

        virtualView.update(new ChosenFinishActionEvent(player1,"End turn"));

        System.out.println(player1 + " status reset");

        //player2's turn

        virtualView.update(new ChosenPawnToUseEvent(player2, 2,2));

        retActions = controller.getGame().getPossibleActions(player2, 2,2);
        correctActions.clear();
        correctActions.add(new MoveAction());
        Assert.assertEquals(correctActions.get(0).getActionID(), retActions.get(0).getActionID());

        virtualView.update(new ChosenMoveActionEvent(player2, "Move", 2,2));

        correctCells.clear();
        correctCells.add(controller.getGame().getGameBoard().getCell(3,2));
        correctCells.add(controller.getGame().getGameBoard().getCell(3,1));
        correctCells.add(controller.getGame().getGameBoard().getCell(1,2));
        correctCells.add(controller.getGame().getGameBoard().getCell(2,1));
        correctCells.add(controller.getGame().getGameBoard().getCell(1,3));

        retCells = controller.getGame().wherePawnCanMove(player2, 2,2);

        correctCells.sort(Comparator.comparingInt(Cell::getRowPosition).thenComparingInt(Cell::getColumnPosition));
        retCells.sort(Comparator.comparingInt(Cell::getRowPosition).thenComparingInt(Cell::getColumnPosition));


        assertEquals(correctCells.size(), retCells.size());

        assertEquals(correctCells, retCells);


        virtualView.update(new ChosenCellToMoveEvent(player2, 2,2,2,1));

        /*

                   ╔═══╦════╦════╦════╦════╦════╗
                   ║   ║ 0  ║ 1  ║ 2  ║ 3  ║ 4  ║
                   ╠═══╬════╬════╬════╬════╬════╣
                   ║ 0 ║ 0  ║ A0 ║ 0  ║ 0  ║ 0  ║
                   ║ 1 ║ 1  ║ a0 ║ 0  ║ 0  ║ 0  ║
                   ║ 2 ║ 0  ║ S0 ║ 0  ║ m0 ║ 0  ║
                   ║ 3 ║ 0  ║ 0  ║ 0  ║ s0 ║ 0  ║
                   ║ 4 ║ 0  ║ 0  ║ 0  ║ 0  ║ M0 ║
                   ╚═══╩════╩════╩════╩════╩════╝


           */


        retActions = controller.getGame().getPossibleActions(player2, 2,1);
        correctActions.clear();
        correctActions.add(new MoveAction());
        correctActions.add(new BuildAction());
        Assert.assertEquals(correctActions.size(), retActions.size());
        for (int i = 0; i < correctActions.size(); i++) {
            Assert.assertEquals(correctActions.get(i).getActionID(), retActions.get(i).getActionID());
        }

        virtualView.update(new ChosenMoveActionEvent(player2, "Move",2,1));

        correctCells.clear();
        correctCells.add(controller.getGame().getGameBoard().getCell(1,0));
        correctCells.add(controller.getGame().getGameBoard().getCell(2,0));
        correctCells.add(controller.getGame().getGameBoard().getCell(3,0));
        correctCells.add(controller.getGame().getGameBoard().getCell(3,1));
        correctCells.add(controller.getGame().getGameBoard().getCell(3,2));
        correctCells.add(controller.getGame().getGameBoard().getCell(1,2));

        retCells = controller.getGame().wherePawnCanMove(player2, 2,1);

        correctCells.sort(Comparator.comparingInt(Cell::getRowPosition).thenComparingInt(Cell::getColumnPosition));
        retCells.sort(Comparator.comparingInt(Cell::getRowPosition).thenComparingInt(Cell::getColumnPosition));


        assertEquals(correctCells.size(), retCells.size());

        assertEquals(correctCells, retCells);


        virtualView.update(new ChosenCellToMoveEvent(player2, 2,1,2,0));

         /*

                   ╔═══╦════╦════╦════╦════╦════╗
                   ║   ║ 0  ║ 1  ║ 2  ║ 3  ║ 4  ║
                   ╠═══╬════╬════╬════╬════╬════╣
                   ║ 0 ║ 0  ║ A0 ║ 0  ║ 0  ║ 0  ║
                   ║ 1 ║ 1  ║ a0 ║ 0  ║ 0  ║ 0  ║
                   ║ 2 ║ S0 ║ 0  ║ 0  ║ m0 ║ 0  ║
                   ║ 3 ║ 0  ║ 0  ║ 0  ║ s0 ║ 0  ║
                   ║ 4 ║ 0  ║ 0  ║ 0  ║ 0  ║ M0 ║
                   ╚═══╩════╩════╩════╩════╩════╝


           */

        retActions = controller.getGame().getPossibleActions(player2, 2,0);
        correctActions.clear();
        correctActions.add(new BuildAction());
        Assert.assertEquals(correctActions.size(), retActions.size());
        for (int i = 0; i < correctActions.size(); i++) {
            Assert.assertEquals(correctActions.get(i).getActionID(), retActions.get(i).getActionID());
        }

        virtualView.update(new ChosenBuildActionEvent(player2,"Build", 2,0));


        correctCells.clear();
        correctCells.add(controller.getGame().getGameBoard().getCell(1,0));
        correctCells.add(controller.getGame().getGameBoard().getCell(3,0));
        correctCells.add(controller.getGame().getGameBoard().getCell(3,1));
        correctCells.add(controller.getGame().getGameBoard().getCell(2,1));


        retCells = controller.getGame().wherePawnCanBuild(player2, 2,0);

        correctCells.sort(Comparator.comparingInt(Cell::getRowPosition).thenComparingInt(Cell::getColumnPosition));
        retCells.sort(Comparator.comparingInt(Cell::getRowPosition).thenComparingInt(Cell::getColumnPosition));


        assertEquals(correctCells.size(), retCells.size());

        assertEquals(correctCells, retCells);

        virtualView.update(new ChosenCellToBuildEvent(player2, 2,0,3,0));

        virtualView.update(new ChosenBuildingEvent(player2, 1,2,0,3,0));

          /*

                   ╔═══╦════╦════╦════╦════╦════╗
                   ║   ║ 0  ║ 1  ║ 2  ║ 3  ║ 4  ║
                   ╠═══╬════╬════╬════╬════╬════╣
                   ║ 0 ║ 0  ║ A0 ║ 0  ║ 0  ║ 0  ║
                   ║ 1 ║ 1  ║ a0 ║ 0  ║ 0  ║ 0  ║
                   ║ 2 ║ S0 ║ 0  ║ 0  ║ m0 ║ 0  ║
                   ║ 3 ║ 1  ║ 0  ║ 0  ║ s0 ║ 0  ║
                   ║ 4 ║ 0  ║ 0  ║ 0  ║ 0  ║ M0 ║
                   ╚═══╩════╩════╩════╩════╩════╝


           */


        retActions = controller.getGame().getPossibleActions(player2, 2,0);
        correctActions.clear();
        correctActions.add(new FinishAction());

        Assert.assertEquals(correctActions.size(), retActions.size());
        for (int i = 0; i < correctActions.size(); i++) {
            Assert.assertEquals(correctActions.get(i).getActionID(), retActions.get(i).getActionID());
        }

        virtualView.update(new ChosenFinishActionEvent(player2, "End turn"));

        System.out.println(player2 + " status reset");


        //player3's turn

        //building a level 1 block to active Athena's effect
        controller.getGame().getGameBoard().getCell(1,2).buildOnThisCell(buildings.get(0));

        virtualView.update(new ChosenPawnToUseEvent(player3, 2,3));

        retActions = controller.getGame().getPossibleActions(player3, 2,3);
        correctActions.clear();correctActions.add(new MoveAction());
        Assert.assertEquals(correctActions.size(), retActions.size());
        for (int i = 0; i < correctActions.size(); i++) {
            Assert.assertEquals(correctActions.get(i).getActionID(), retActions.get(i).getActionID());
        }

        virtualView.update(new ChosenMoveActionEvent(player3, "Move", 2,3));

        correctCells.clear();
        correctCells.add(controller.getGame().getGameBoard().getCell(3,4));
        correctCells.add(controller.getGame().getGameBoard().getCell(2,4));
        correctCells.add(controller.getGame().getGameBoard().getCell(1,4));
        correctCells.add(controller.getGame().getGameBoard().getCell(1,3));
        correctCells.add(controller.getGame().getGameBoard().getCell(3,2));
        correctCells.add(controller.getGame().getGameBoard().getCell(2,2));
        correctCells.add(controller.getGame().getGameBoard().getCell(1,2));


        retCells = controller.getGame().wherePawnCanMove(player3, 2,3);

        correctCells.sort(Comparator.comparingInt(Cell::getRowPosition).thenComparingInt(Cell::getColumnPosition));
        retCells.sort(Comparator.comparingInt(Cell::getRowPosition).thenComparingInt(Cell::getColumnPosition));


        assertEquals(correctCells.size(), retCells.size());

        assertEquals(correctCells, retCells);

        virtualView.update(new ChosenCellToMoveEvent(player3, 2,3,1,2));
        //ATHENA'S EFFECT ACTIVATED

        /*

                   ╔═══╦════╦════╦════╦════╦════╗
                   ║   ║ 0  ║ 1  ║ 2  ║ 3  ║ 4  ║
                   ╠═══╬════╬════╬════╬════╬════╣
                   ║ 0 ║ 0  ║ A0 ║ 0  ║ 0  ║ 0  ║
                   ║ 1 ║ 1  ║ a0 ║ m1 ║ 0  ║ 0  ║
                   ║ 2 ║ S0 ║ 0  ║ 0  ║ 0  ║ 0  ║
                   ║ 3 ║ 1  ║ 0  ║ 0  ║ s0 ║ 0  ║
                   ║ 4 ║ 0  ║ 0  ║ 0  ║ 0  ║ M0 ║
                   ╚═══╩════╩════╩════╩════╩════╝


           */

        retActions = controller.getGame().getPossibleActions(player3, 1,2);
        correctActions.clear();
        correctActions.add(new BuildAction());
        Assert.assertEquals(correctActions.size(), retActions.size());
        for (int i = 0; i < correctActions.size(); i++) {
            Assert.assertEquals(correctActions.get(i).getActionID(), retActions.get(i).getActionID());
        }

        virtualView.update(new ChosenBuildActionEvent(player3,"Build",1,2));

        correctCells.clear();
        correctCells.add(controller.getGame().getGameBoard().getCell(0,2));
        correctCells.add(controller.getGame().getGameBoard().getCell(0,3));
        correctCells.add(controller.getGame().getGameBoard().getCell(1,3));
        correctCells.add(controller.getGame().getGameBoard().getCell(2,3));
        correctCells.add(controller.getGame().getGameBoard().getCell(2,2));
        correctCells.add(controller.getGame().getGameBoard().getCell(2,1));


        retCells = controller.getGame().wherePawnCanBuild(player3, 1,2);

        correctCells.sort(Comparator.comparingInt(Cell::getRowPosition).thenComparingInt(Cell::getColumnPosition));
        retCells.sort(Comparator.comparingInt(Cell::getRowPosition).thenComparingInt(Cell::getColumnPosition));


        assertEquals(correctCells.size(), retCells.size());

        assertEquals(correctCells, retCells);

        virtualView.update(new ChosenCellToBuildEvent(player3, 1,2,0,2));

        virtualView.update(new ChosenBuildingEvent(player3, 1,1,2,0,2));

        /*

                   ╔═══╦════╦════╦════╦════╦════╗
                   ║   ║ 0  ║ 1  ║ 2  ║ 3  ║ 4  ║
                   ╠═══╬════╬════╬════╬════╬════╣
                   ║ 0 ║ 0  ║ A0 ║ 1  ║ 0  ║ 0  ║
                   ║ 1 ║ 1  ║ a0 ║ m1 ║ 0  ║ 0  ║
                   ║ 2 ║ S0 ║ 0  ║ 0  ║ 0  ║ 0  ║
                   ║ 3 ║ 1  ║ 0  ║ 0  ║ s0 ║ 0  ║
                   ║ 4 ║ 0  ║ 0  ║ 0  ║ 0  ║ M0 ║
                   ╚═══╩════╩════╩════╩════╩════╝


           */

        retActions = controller.getGame().getPossibleActions(player3, 1,2);
        correctActions.clear();
        correctActions.add(new FinishAction());
        Assert.assertEquals(correctActions.size(), retActions.size());
        for (int i = 0; i < correctActions.size(); i++) {
            Assert.assertEquals(correctActions.get(i).getActionID(), retActions.get(i).getActionID());
        }

        virtualView.update(new ChosenFinishActionEvent(player3, "End turn"));

        System.out.println(player3 + " status reset");


        //player1's turn

        virtualView.update(new ChosenPawnToUseEvent(player1, 1,1));

        retActions = controller.getGame().getPossibleActions(player1, 1,1);
        correctActions.clear();
        correctActions.add(new MoveAction());
        Assert.assertEquals(correctActions.size(), retActions.size());
        for (int i = 0; i < correctActions.size(); i++) {
            Assert.assertEquals(correctActions.get(i).getActionID(), retActions.get(i).getActionID());
        }
        virtualView.update(new ChosenMoveActionEvent(player1, "Move", 1,1));

        //athena's effect is active, so player1 cannot move up
        correctCells.clear();
        correctCells.add(controller.getGame().getGameBoard().getCell(0,0));
        correctCells.add(controller.getGame().getGameBoard().getCell(2,0));
        correctCells.add(controller.getGame().getGameBoard().getCell(2,2));
        correctCells.add(controller.getGame().getGameBoard().getCell(2,1));

        retCells = controller.getGame().wherePawnCanMove(player1, 1,1);

        correctCells.sort(Comparator.comparingInt(Cell::getRowPosition).thenComparingInt(Cell::getColumnPosition));
        retCells.sort(Comparator.comparingInt(Cell::getRowPosition).thenComparingInt(Cell::getColumnPosition));


        assertEquals(correctCells.size(), retCells.size());

        assertEquals(correctCells, retCells);

        virtualView.update(new ChosenCellToMoveEvent(player1, 1,1,2,0));

        /*

                   ╔═══╦════╦════╦════╦════╦════╗
                   ║   ║ 0  ║ 1  ║ 2  ║ 3  ║ 4  ║
                   ╠═══╬════╬════╬════╬════╬════╣
                   ║ 0 ║ 0  ║ A0 ║ 1  ║ 0  ║ 0  ║
                   ║ 1 ║ 1  ║ S0 ║ m1 ║ 0  ║ 0  ║
                   ║ 2 ║ a0 ║ 0  ║ 0  ║ 0  ║ 0  ║
                   ║ 3 ║ 1  ║ 0  ║ 0  ║ s0 ║ 0  ║
                   ║ 4 ║ 0  ║ 0  ║ 0  ║ 0  ║ M0 ║
                   ╚═══╩════╩════╩════╩════╩════╝

           */

        retActions = controller.getGame().getPossibleActions(player1, 1,2);
        correctActions.clear();
        correctActions.add(new BuildAction());
        Assert.assertEquals(correctActions.size(), retActions.size());
        for (int i = 0; i < correctActions.size(); i++) {
            Assert.assertEquals(correctActions.get(i).getActionID(), retActions.get(i).getActionID());
        }

        virtualView.update(new ChosenBuildActionEvent(player1,"Build", 2,0));

        correctCells.clear();
        correctCells.add(controller.getGame().getGameBoard().getCell(1,0));
        correctCells.add(controller.getGame().getGameBoard().getCell(3,0));
        correctCells.add(controller.getGame().getGameBoard().getCell(3,1));
        correctCells.add(controller.getGame().getGameBoard().getCell(2,1));


        retCells = controller.getGame().wherePawnCanBuild(player1, 2,0);

        correctCells.sort(Comparator.comparingInt(Cell::getRowPosition).thenComparingInt(Cell::getColumnPosition));
        retCells.sort(Comparator.comparingInt(Cell::getRowPosition).thenComparingInt(Cell::getColumnPosition));


        assertEquals(correctCells.size(), retCells.size());

        assertEquals(correctCells, retCells);

        virtualView.update(new ChosenCellToBuildEvent(player1, 2,0,3,0));

        virtualView.update(new ChosenBuildingEvent(player1, 2,2,0,3,0));

        /*

                   ╔═══╦════╦════╦════╦════╦════╗
                   ║   ║ 0  ║ 1  ║ 2  ║ 3  ║ 4  ║
                   ╠═══╬════╬════╬════╬════╬════╣
                   ║ 0 ║ 0  ║ A0 ║ 1  ║ 0  ║ 0  ║
                   ║ 1 ║ 1  ║ S0 ║ m1 ║ 0  ║ 0  ║
                   ║ 2 ║ a0 ║ 0  ║ 0  ║ 0  ║ 0  ║
                   ║ 3 ║ 2  ║ 0  ║ 0  ║ s0 ║ 0  ║
                   ║ 4 ║ 0  ║ 0  ║ 0  ║ 0  ║ M0 ║
                   ╚═══╩════╩════╩════╩════╩════╝

           */

        retActions = controller.getGame().getPossibleActions(player1, 2,0);
        correctActions.clear();
        correctActions.add(new FinishAction());
        Assert.assertEquals(correctActions.get(0).getActionID(), retActions.get(0).getActionID());

        virtualView.update(new ChosenFinishActionEvent(player1,"End turn"));

        System.out.println(player1 + " status reset");

    }


    @Test
    void MVCEndGame() {

        MVCPreGameStartSetUp();

        Card Ares = JsonHandler.deserializeCardList().get(9);

        Card Charon = JsonHandler.deserializeCardList().get(10);

        Card Zeus = JsonHandler.deserializeCardList().get(13);

        controller.getPreGameLobby().getPickedCards().add(Ares);
        controller.getPreGameLobby().getPickedCards().add(Charon);
        controller.getPreGameLobby().getPickedCards().add(Zeus);


        controller.getPreGameLobby().getPickedCards().add(Ares);
        controller.getPreGameLobby().getPickedCards().add(Charon);
        controller.getPreGameLobby().getPickedCards().add(Zeus);


        virtualView.update(new ChosenCardEvent(player1,Ares.getName()));

        virtualView.update(new ChosenCardEvent(player2,Charon.getName()));

        virtualView.update(new ChosenCardEvent(player3, Zeus.getName()));


        virtualView.update(new ChosenInitialPawnCellEvent(player1, 0,0,1,1));


        virtualView.update(new ChosenInitialPawnCellEvent(player2, 2,2,3,3));


        virtualView.update(new ChosenInitialPawnCellEvent(player3, 4,4,2,3));

        List<Building> buildings = controller.getGame().getGameBoard().getBuildings();

        /*

              ╔═══╦════╦════╦════╦════╦════╗
              ║   ║ 0  ║ 1  ║ 2  ║ 3  ║ 4  ║
              ╠═══╬════╬════╬════╬════╬════╣
              ║ 0 ║ A0 ║ 0  ║ 0  ║ 0  ║ 0  ║
              ║ 1 ║ 0  ║ a0 ║ 0  ║ 0  ║ 0  ║
              ║ 2 ║ 0  ║ 0  ║ S0 ║ m0 ║ 0  ║
              ║ 3 ║ 0  ║ 0  ║ 0  ║ s0 ║ 0  ║
              ║ 4 ║ 0  ║ 0  ║ 0  ║ 0  ║ M0 ║
              ╚═══╩════╩════╩════╩════╩════╝


      */

        //player1's turn

        virtualView.update(new ChosenPawnToUseEvent(player1,0,0));

        virtualView.update(new ChosenMoveActionEvent(player1, "Move", 0,0));

        virtualView.update(new ChosenCellToMoveEvent(player1, 0,0,0,1));

        virtualView.update(new ChosenBuildActionEvent(player1, "Build", 0,1));

        virtualView.update(new ChosenCellToBuildEvent(player1, 0,1,1,0));

        virtualView.update(new ChosenBuildingEvent(player1,1,0,1,1,0));

        retActions = controller.getGame().getPossibleActions(player1, 0,1);

        virtualView.update(new ChosenFinishActionEvent(player1, "End turn"));

        System.out.println(player1 + " reset his state");


        //player2's turn

        virtualView.update(new ChosenPawnToUseEvent(player2, 3,3));

        virtualView.update(new ChosenForceActionEvent(player2, "Force", 3,3));

        virtualView.update(new ChosenCellToForceEvent(player2,3,3,2,3));



    }

}
