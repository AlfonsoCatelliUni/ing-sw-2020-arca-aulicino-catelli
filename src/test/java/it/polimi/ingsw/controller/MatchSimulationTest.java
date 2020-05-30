package it.polimi.ingsw.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import it.polimi.ingsw.JsonHandler;
import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.events.CTSEvents.*;
import it.polimi.ingsw.events.ClientToServerEvent;
import it.polimi.ingsw.events.STCEvents.DisconnectionClientEvent;
import it.polimi.ingsw.events.manager.ClientToServerManager;
import it.polimi.ingsw.model.Actions.*;
import it.polimi.ingsw.model.Board.Building;
import it.polimi.ingsw.model.Board.Cell;
import it.polimi.ingsw.model.Player.Card;
import it.polimi.ingsw.model.Player.State.BuildAndFinishState;
import it.polimi.ingsw.model.Player.State.BuildState;
import it.polimi.ingsw.view.server.VirtualView;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.*;


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

        card1 = JsonHandler.deserializeCardList().get(0).getName();

        card2 = JsonHandler.deserializeCardList().get(1).getName();

        card3 = JsonHandler.deserializeCardList().get(2).getName();

        effect1 = JsonHandler.deserializeCardList().get(0).getEffectDescription();
        effect2 = JsonHandler.deserializeCardList().get(1).getEffectDescription();
        effect3 = JsonHandler.deserializeCardList().get(2).getEffectDescription();



        List<String> chosenCards = new ArrayList<>();
        chosenCards.add(card1);
        chosenCards.add(card2);
        chosenCards.add(card3);

        virtualView.update(new ChosenCardsChallengerEvent(player3, chosenCards));

       //card1 = controller.getPreGameLobby().getPickedCards().get(0).getName();
       // card2 = controller.getPreGameLobby().getPickedCards().get(1).getName();
       // card3 = controller.getPreGameLobby().getPickedCards().get(2).getName();

       // effect1 = controller.getPreGameLobby().getPickedCards().get(0).getEffectDescription();
       // effect2 = controller.getPreGameLobby().getPickedCards().get(1).getEffectDescription();
       // effect3 = controller.getPreGameLobby().getPickedCards().get(2).getEffectDescription();


        System.out.println("Should print a GivePossibleCardEvent with attributes:\n" + player1 + "\n" + card1 + ", " + card2 + ", " + card3 + "\n" + effect1 + ",\n " + effect2 + ",\n " + effect3 + "\n" + "true" + "\n");

        virtualView.update(new ChosenCardEvent(player1,card1));

        System.out.println("Should print a GivePossibleCardEvent with attributes:\n" + player2 + "\n" + card2 + ", " + card3 + "\n" + effect2 + ",\n " + effect3 + "\n" + "true" + "\n");

        virtualView.update(new ChosenCardEvent(player2,card2));

        System.out.println("Should print a GivePossibleCardEvent with attributes:\n" + player3 + "\n" + card3 + "\n" + effect3 + "\n" + "true" + "\n");

        virtualView.update(new ChosenFirstPlayerEvent(player3,player1));

        //virtualView.update(new ChosenCardEvent(player3, card3));

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

        List<String> chosenCards = new ArrayList<>();
        chosenCards.add(Apollo.getName());
        chosenCards.add(Artemis.getName());
        chosenCards.add(Athena.getName());

    //    controller.getPreGameLobby().getPickedCards().add(Apollo);
    //    controller.getPreGameLobby().getPickedCards().add(Artemis);
    //    controller.getPreGameLobby().getPickedCards().add(Athena);

        virtualView.update(new ChosenCardsChallengerEvent(player3, chosenCards));


        virtualView.update(new ChosenCardEvent(player1,Apollo.getName()));

        virtualView.update(new ChosenCardEvent(player2,Artemis.getName()));

        //virtualView.update(new ChosenCardEvent(player3, Athena.getName()));

        virtualView.update(new ChosenFirstPlayerEvent(player3, player1));


        


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
        correctActions.clear();
        correctActions.add(new MoveAction());
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

        retActions = controller.getGame().getPossibleActions(player1, 2,0);
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

        Card Zeus = JsonHandler.deserializeCardList().get(13);

        Card Charon = JsonHandler.deserializeCardList().get(10);

        Card Ares = JsonHandler.deserializeCardList().get(9);

        List<String> chosenCards = new ArrayList<>();
        chosenCards.add(Zeus.getName());
        chosenCards.add(Charon.getName());
        chosenCards.add(Ares.getName());

       // controller.getPreGameLobby().getPickedCards().add(Zeus);
       // controller.getPreGameLobby().getPickedCards().add(Charon);
       // controller.getPreGameLobby().getPickedCards().add(Ares);


       // controller.getPreGameLobby().getPickedCards().add(Zeus);
       // controller.getPreGameLobby().getPickedCards().add(Charon);
       // controller.getPreGameLobby().getPickedCards().add(Ares);

        virtualView.update(new ChosenCardsChallengerEvent(player3, chosenCards));


        virtualView.update(new ChosenCardEvent(player1,Zeus.getName()));

        virtualView.update(new ChosenCardEvent(player2,Charon.getName()));

       // virtualView.update(new ChosenCardEvent(player3, Ares.getName()));

        virtualView.update(new ChosenFirstPlayerEvent(player3,player1));


        virtualView.update(new ChosenInitialPawnCellEvent(player1, 0,0,1,1));


        virtualView.update(new ChosenInitialPawnCellEvent(player2, 2,2,3,3));


        virtualView.update(new ChosenInitialPawnCellEvent(player3, 4,4,2,3));

        List<Building> buildings = controller.getGame().getGameBoard().getBuildings();

        //to test the end of the game, let's build some blocks to go straight to the end

        controller.getGame().getGameBoard().getCell(1,2).buildOnThisCell(buildings.get(0));
        controller.getGame().getGameBoard().getCell(1,2).buildOnThisCell(buildings.get(1));
        controller.getGame().getGameBoard().getCell(1,2).buildOnThisCell(buildings.get(2));
        controller.getGame().getGameBoard().getCell(2,1).buildOnThisCell(buildings.get(0));
        controller.getGame().getGameBoard().getCell(0,2).buildOnThisCell(buildings.get(0));
        controller.getGame().getGameBoard().getCell(0,2).buildOnThisCell(buildings.get(1));
        controller.getGame().getGameBoard().getCell(0,2).buildOnThisCell(buildings.get(2));
        controller.getGame().getGameBoard().getCell(1,3).buildOnThisCell(buildings.get(0));
        controller.getGame().getGameBoard().getCell(4,3).buildOnThisCell(buildings.get(0));
        controller.getGame().getGameBoard().getCell(4,3).buildOnThisCell(buildings.get(1));
        controller.getGame().getGameBoard().getCell(3,2).buildOnThisCell(buildings.get(0));
        controller.getGame().getGameBoard().getCell(4,2).buildOnThisCell(buildings.get(0));
        controller.getGame().getGameBoard().getCell(3,4).buildOnThisCell(buildings.get(0));


        /*

              ╔═══╦════╦════╦════╦════╦════╗
              ║   ║ 0  ║ 1  ║ 2  ║ 3  ║ 4  ║
              ╠═══╬════╬════╬════╬════╬════╣
              ║ 0 ║ A0 ║ 0  ║ 3  ║ 0  ║ 0  ║
              ║ 1 ║ 0  ║ a0 ║ 3  ║ 1  ║ 0  ║
              ║ 2 ║ 0  ║ 1  ║ S0 ║ m0 ║ 0  ║
              ║ 3 ║ 0  ║ 0  ║ 1  ║ s0 ║ 1  ║
              ║ 4 ║ 0  ║ 0  ║ 1  ║ 2  ║ M0 ║
              ╚═══╩════╩════╩════╩════╩════╝


      */

        //player1's turn

        virtualView.update(new ChosenPawnToUseEvent(player1,1,1));


        retActions = controller.getGame().getPossibleActions(player1, 1,1);
        correctActions.clear();
        correctActions.add(new MoveAction());
        Assert.assertEquals(correctActions.size(), retActions.size());
        for (int i = 0; i < correctActions.size(); i++) {
            Assert.assertEquals(correctActions.get(i).getActionID(), retActions.get(i).getActionID());
        }

        virtualView.update(new ChosenMoveActionEvent(player1, "Move", 1,1));


        correctCells.clear();
        correctCells.add(controller.getGame().getGameBoard().getCell(1,0));
        correctCells.add(controller.getGame().getGameBoard().getCell(0,1));
        correctCells.add(controller.getGame().getGameBoard().getCell(2,0));
        correctCells.add(controller.getGame().getGameBoard().getCell(2,1));


        retCells = controller.getGame().wherePawnCanMove(player1, 1,1);

        correctCells.sort(Comparator.comparingInt(Cell::getRowPosition).thenComparingInt(Cell::getColumnPosition));
        retCells.sort(Comparator.comparingInt(Cell::getRowPosition).thenComparingInt(Cell::getColumnPosition));


        assertEquals(correctCells.size(), retCells.size());

        assertEquals(correctCells, retCells);



        virtualView.update(new ChosenCellToMoveEvent(player1, 1,1,2,1));


        /*

              ╔═══╦════╦════╦════╦════╦════╗
              ║   ║ 0  ║ 1  ║ 2  ║ 3  ║ 4  ║
              ╠═══╬════╬════╬════╬════╬════╣
              ║ 0 ║ A0 ║ 0  ║ 3  ║ 0  ║ 0  ║
              ║ 1 ║ 0  ║ 0  ║ 3  ║ 1  ║ 0  ║
              ║ 2 ║ 0  ║ a1 ║ S0 ║ m0 ║ 0  ║
              ║ 3 ║ 0  ║ 0  ║ 1  ║ s0 ║ 1  ║
              ║ 4 ║ 0  ║ 0  ║ 1  ║ 2  ║ M0 ║
              ╚═══╩════╩════╩════╩════╩════╝


      */

        retActions = controller.getGame().getPossibleActions(player1, 2,1);
        correctActions.clear();
        correctActions.add(new BuildAction());
        Assert.assertEquals(correctActions.size(), retActions.size());
        for (int i = 0; i < correctActions.size(); i++) {
            Assert.assertEquals(correctActions.get(i).getActionID(), retActions.get(i).getActionID());
        }


        virtualView.update(new ChosenBuildActionEvent(player1, "Build", 2,1));


        correctCells.clear();
        correctCells.add(controller.getGame().getGameBoard().getCell(1,2));
        correctCells.add(controller.getGame().getGameBoard().getCell(1,1));
        correctCells.add(controller.getGame().getGameBoard().getCell(1,0));
        correctCells.add(controller.getGame().getGameBoard().getCell(2,0));
        correctCells.add(controller.getGame().getGameBoard().getCell(3,0));
        correctCells.add(controller.getGame().getGameBoard().getCell(3,1));
        correctCells.add(controller.getGame().getGameBoard().getCell(3,2));
        correctCells.add(controller.getGame().getGameBoard().getCell(2,1));


        retCells = controller.getGame().wherePawnCanBuild(player1, 2,1);

        correctCells.sort(Comparator.comparingInt(Cell::getRowPosition).thenComparingInt(Cell::getColumnPosition));
        retCells.sort(Comparator.comparingInt(Cell::getRowPosition).thenComparingInt(Cell::getColumnPosition));


        assertEquals(correctCells.size(), retCells.size());

        assertEquals(correctCells, retCells);


        virtualView.update(new ChosenCellToBuildEvent(player1, 2,1,2,1));

        virtualView.update(new ChosenBuildingEvent(player1,2,2,1,2,1));


        /*

              ╔═══╦════╦════╦════╦════╦════╗
              ║   ║ 0  ║ 1  ║ 2  ║ 3  ║ 4  ║
              ╠═══╬════╬════╬════╬════╬════╣
              ║ 0 ║ A0 ║ 0  ║ 3  ║ 0  ║ 0  ║
              ║ 1 ║ 0  ║ 0  ║ 3  ║ 1  ║ 0  ║
              ║ 2 ║ 0  ║ a2 ║ S0 ║ m0 ║ 0  ║
              ║ 3 ║ 0  ║ 0  ║ 1  ║ s0 ║ 1  ║
              ║ 4 ║ 0  ║ 0  ║ 1  ║ 2  ║ M0 ║
              ╚═══╩════╩════╩════╩════╩════╝


      */

        retActions = controller.getGame().getPossibleActions(player1, 2,1);
        correctActions.clear();
        correctActions.add(new FinishAction());
        Assert.assertEquals(correctActions.size(), retActions.size());
        for (int i = 0; i < correctActions.size(); i++) {
            Assert.assertEquals(correctActions.get(i).getActionID(), retActions.get(i).getActionID());
        }

        virtualView.update(new ChosenFinishActionEvent(player1, "End turn"));

        System.out.println(player1 + " reset his state");


        //player2's turn

        virtualView.update(new ChosenPawnToUseEvent(player2, 3,3));


        retActions = controller.getGame().getPossibleActions(player2, 3,3);
        correctActions.clear();
        correctActions.add(new MoveAction());
        correctActions.add(new ForceAction());
        Assert.assertEquals(correctActions.size(), retActions.size());
        for (int i = 0; i < correctActions.size(); i++) {
            Assert.assertEquals(correctActions.get(i).getActionID(), retActions.get(i).getActionID());
        }


        virtualView.update(new ChosenForceActionEvent(player2, "Force", 3,3));


        correctCells.clear();
        correctCells.add(controller.getGame().getGameBoard().getCell(2,3));


        retCells = controller.getGame().getOpponentsNeighboring(player2, 3,3);

        correctCells.sort(Comparator.comparingInt(Cell::getRowPosition).thenComparingInt(Cell::getColumnPosition));
        retCells.sort(Comparator.comparingInt(Cell::getRowPosition).thenComparingInt(Cell::getColumnPosition));


        assertEquals(correctCells.size(), retCells.size());

        assertEquals(correctCells, retCells);


        virtualView.update(new ChosenCellToForceEvent(player2,3,3,2,3));


        /*

              ╔═══╦════╦════╦════╦════╦════╗
              ║   ║ 0  ║ 1  ║ 2  ║ 3  ║ 4  ║
              ╠═══╬════╬════╬════╬════╬════╣
              ║ 0 ║ A0 ║ 0  ║ 3  ║ 0  ║ 0  ║
              ║ 1 ║ 0  ║ 0  ║ 3  ║ 1  ║ 0  ║
              ║ 2 ║ 0  ║ a2 ║ S0 ║ 0  ║ 0  ║
              ║ 3 ║ 0  ║ 0  ║ 1  ║ s0 ║ 1  ║
              ║ 4 ║ 0  ║ 0  ║ 1  ║ m2 ║ M0 ║
              ╚═══╩════╩════╩════╩════╩════╝


        */

        retActions = controller.getGame().getPossibleActions(player2, 3,3);
        correctActions.clear();
        correctActions.add(new MoveAction());
        Assert.assertEquals(correctActions.size(), retActions.size());
        for (int i = 0; i < correctActions.size(); i++) {
            Assert.assertEquals(correctActions.get(i).getActionID(), retActions.get(i).getActionID());
        }

        virtualView.update(new ChosenMoveActionEvent(player2,"Move",3,3));


        correctCells.clear();
        correctCells.add(controller.getGame().getGameBoard().getCell(2,3));
        correctCells.add(controller.getGame().getGameBoard().getCell(2,4));
        correctCells.add(controller.getGame().getGameBoard().getCell(3,2));
        correctCells.add(controller.getGame().getGameBoard().getCell(4,2));
        correctCells.add(controller.getGame().getGameBoard().getCell(3,4));



        retCells = controller.getGame().wherePawnCanMove(player2, 3,3);

        correctCells.sort(Comparator.comparingInt(Cell::getRowPosition).thenComparingInt(Cell::getColumnPosition));
        retCells.sort(Comparator.comparingInt(Cell::getRowPosition).thenComparingInt(Cell::getColumnPosition));


        assertEquals(correctCells.size(), retCells.size());

        assertEquals(correctCells, retCells);


        virtualView.update(new ChosenCellToMoveEvent(player2, 3,3,2,3));


        /*

              ╔═══╦════╦════╦════╦════╦════╗
              ║   ║ 0  ║ 1  ║ 2  ║ 3  ║ 4  ║
              ╠═══╬════╬════╬════╬════╬════╣
              ║ 0 ║ A0 ║ 0  ║ 3  ║ 0  ║ 0  ║
              ║ 1 ║ 0  ║ 0  ║ 3  ║ 1  ║ 0  ║
              ║ 2 ║ 0  ║ a2 ║ S0 ║ s0 ║ 0  ║
              ║ 3 ║ 0  ║ 0  ║ 1  ║ 0  ║ 1  ║
              ║ 4 ║ 0  ║ 0  ║ 1  ║ m2 ║ M0 ║
              ╚═══╩════╩════╩════╩════╩════╝


        */



        retActions = controller.getGame().getPossibleActions(player2, 2,3);
        correctActions.clear();
        correctActions.add(new BuildAction());
        Assert.assertEquals(correctActions.size(), retActions.size());
        for (int i = 0; i < correctActions.size(); i++) {
            Assert.assertEquals(correctActions.get(i).getActionID(), retActions.get(i).getActionID());
        }


        virtualView.update(new ChosenBuildActionEvent(player2, "Build", 2,3));


        correctCells.clear();
        correctCells.add(controller.getGame().getGameBoard().getCell(1,2));
        correctCells.add(controller.getGame().getGameBoard().getCell(1,3));
        correctCells.add(controller.getGame().getGameBoard().getCell(1,4));
        correctCells.add(controller.getGame().getGameBoard().getCell(2,4));
        correctCells.add(controller.getGame().getGameBoard().getCell(3,4));
        correctCells.add(controller.getGame().getGameBoard().getCell(3,3));
        correctCells.add(controller.getGame().getGameBoard().getCell(3,2));


        retCells = controller.getGame().wherePawnCanBuild(player2, 2,3);

        correctCells.sort(Comparator.comparingInt(Cell::getRowPosition).thenComparingInt(Cell::getColumnPosition));
        retCells.sort(Comparator.comparingInt(Cell::getRowPosition).thenComparingInt(Cell::getColumnPosition));


        assertEquals(correctCells.size(), retCells.size());

        assertEquals(correctCells, retCells);


        virtualView.update(new ChosenCellToBuildEvent(player2, 2,3,1,3));

        virtualView.update(new ChosenBuildingEvent(player2,2,2,3,1,3));


        /*

              ╔═══╦════╦════╦════╦════╦════╗
              ║   ║ 0  ║ 1  ║ 2  ║ 3  ║ 4  ║
              ╠═══╬════╬════╬════╬════╬════╣
              ║ 0 ║ A0 ║ 0  ║ 3  ║ 0  ║ 0  ║
              ║ 1 ║ 0  ║ 0  ║ 3  ║ 2  ║ 0  ║
              ║ 2 ║ 0  ║ a2 ║ S0 ║ s0 ║ 0  ║
              ║ 3 ║ 0  ║ 0  ║ 1  ║ 0  ║ 1  ║
              ║ 4 ║ 0  ║ 0  ║ 1  ║ m2 ║ M0 ║
              ╚═══╩════╩════╩════╩════╩════╝


        */


        retActions = controller.getGame().getPossibleActions(player2, 2,3);
        correctActions.clear();
        correctActions.add(new FinishAction());
        Assert.assertEquals(correctActions.size(), retActions.size());
        for (int i = 0; i < correctActions.size(); i++) {
            Assert.assertEquals(correctActions.get(i).getActionID(), retActions.get(i).getActionID());
        }

        virtualView.update(new ChosenFinishActionEvent(player2, "End turn"));

        System.out.println(player2 + " has reset his status");

        //player3's turn

        //Building block until level 4 in [4,2] to test Ares' destroy

        controller.getGame().getGameBoard().getCell(4,2).buildOnThisCell(buildings.get(1));
        controller.getGame().getGameBoard().getCell(4,2).buildOnThisCell(buildings.get(2));
        controller.getGame().getGameBoard().getCell(4,2).buildOnThisCell(buildings.get(3));

        /*

              ╔═══╦════╦════╦════╦════╦════╗
              ║   ║ 0  ║ 1  ║ 2  ║ 3  ║ 4  ║
              ╠═══╬════╬════╬════╬════╬════╣
              ║ 0 ║ A0 ║ 0  ║ 3  ║ 0  ║ 0  ║
              ║ 1 ║ 0  ║ 0  ║ 3  ║ 2  ║ 0  ║
              ║ 2 ║ 0  ║ a2 ║ S0 ║ s0 ║ 0  ║
              ║ 3 ║ 0  ║ 0  ║ 1  ║ 0  ║ 1  ║
              ║ 4 ║ 0  ║ 0  ║ 4x ║ m2 ║ M0 ║
              ╚═══╩════╩════╩════╩════╩════╝


        */

        virtualView.update(new ChosenPawnToUseEvent(player3, 4,4));

        retActions = controller.getGame().getPossibleActions(player3, 4,4);
        correctActions.clear();
        correctActions.add(new MoveAction());
        Assert.assertEquals(correctActions.size(), retActions.size());
        for (int i = 0; i < correctActions.size(); i++) {
            Assert.assertEquals(correctActions.get(i).getActionID(), retActions.get(i).getActionID());
        }


        virtualView.update(new ChosenMoveActionEvent(player3, "Move", 4,4));

        correctCells.clear();
        correctCells.add(controller.getGame().getGameBoard().getCell(3,4));
        correctCells.add(controller.getGame().getGameBoard().getCell(3,3));


        retCells = controller.getGame().wherePawnCanMove(player3, 4,4);

        correctCells.sort(Comparator.comparingInt(Cell::getRowPosition).thenComparingInt(Cell::getColumnPosition));
        retCells.sort(Comparator.comparingInt(Cell::getRowPosition).thenComparingInt(Cell::getColumnPosition));


        assertEquals(correctCells.size(), retCells.size());

        assertEquals(correctCells, retCells);

        virtualView.update(new ChosenCellToMoveEvent(player3, 4,4,3,4));

         /*

              ╔═══╦════╦════╦════╦════╦════╗
              ║   ║ 0  ║ 1  ║ 2  ║ 3  ║ 4  ║
              ╠═══╬════╬════╬════╬════╬════╣
              ║ 0 ║ A0 ║ 0  ║ 3  ║ 0  ║ 0  ║
              ║ 1 ║ 0  ║ 0  ║ 3  ║ 2  ║ 0  ║
              ║ 2 ║ 0  ║ a2 ║ S0 ║ s0 ║ 0  ║
              ║ 3 ║ 0  ║ 0  ║ 1  ║ 0  ║ M1 ║
              ║ 4 ║ 0  ║ 0  ║ 4x ║ m2 ║ 0  ║
              ╚═══╩════╩════╩════╩════╩════╝


        */

        retActions = controller.getGame().getPossibleActions(player3, 3,4);
        correctActions.clear();
        correctActions.add(new BuildAction());
        Assert.assertEquals(correctActions.size(), retActions.size());
        for (int i = 0; i < correctActions.size(); i++) {
            Assert.assertEquals(correctActions.get(i).getActionID(), retActions.get(i).getActionID());
        }

        virtualView.update(new ChosenBuildActionEvent(player3, "Build", 3,4));


        correctCells.clear();
        correctCells.add(controller.getGame().getGameBoard().getCell(4,4));
        correctCells.add(controller.getGame().getGameBoard().getCell(3,3));
        correctCells.add(controller.getGame().getGameBoard().getCell(2,4));


        retCells = controller.getGame().wherePawnCanBuild(player3, 3,4);

        correctCells.sort(Comparator.comparingInt(Cell::getRowPosition).thenComparingInt(Cell::getColumnPosition));
        retCells.sort(Comparator.comparingInt(Cell::getRowPosition).thenComparingInt(Cell::getColumnPosition));


        assertEquals(correctCells.size(), retCells.size());

        assertEquals(correctCells, retCells);

        virtualView.update(new ChosenCellToBuildEvent(player3, 3,4,2,4));

        virtualView.update(new ChosenBuildingEvent(player3,1,3,4,2,4));


        /*

              ╔═══╦════╦════╦════╦════╦════╗
              ║   ║ 0  ║ 1  ║ 2  ║ 3  ║ 4  ║
              ╠═══╬════╬════╬════╬════╬════╣
              ║ 0 ║ A0 ║ 0  ║ 3  ║ 0  ║ 0  ║
              ║ 1 ║ 0  ║ 0  ║ 3  ║ 2  ║ 0  ║
              ║ 2 ║ 0  ║ a2 ║ S0 ║ s0 ║ 1  ║
              ║ 3 ║ 0  ║ 0  ║ 1  ║ 0  ║ M1 ║
              ║ 4 ║ 0  ║ 0  ║ 4x ║ m2 ║ 0  ║
              ╚═══╩════╩════╩════╩════╩════╝


        */

        retActions = controller.getGame().getPossibleActions(player3, 3,4);
        correctActions.clear();
        correctActions.add(new FinishAction());
        correctActions.add(new DestroyAction());
        Assert.assertEquals(correctActions.size(), retActions.size());
        for (int i = 0; i < correctActions.size(); i++) {
            Assert.assertEquals(correctActions.get(i).getActionID(), retActions.get(i).getActionID());
        }

        virtualView.update(new ChosenDestroyActionEvent(player3, "Destroy", 3,4));

        correctCells.clear();
        correctCells.add(controller.getGame().getGameBoard().getCell(3,2));


        retCells = controller.getGame().wherePawnCanDestroy(player3);

        correctCells.sort(Comparator.comparingInt(Cell::getRowPosition).thenComparingInt(Cell::getColumnPosition));
        retCells.sort(Comparator.comparingInt(Cell::getRowPosition).thenComparingInt(Cell::getColumnPosition));


        assertEquals(correctCells.size(), retCells.size());

        assertEquals(correctCells, retCells);

        virtualView.update(new ChosenCellToDestroyEvent(player3, 3,4, 3,2));


        /*

              ╔═══╦════╦════╦════╦════╦════╗
              ║   ║ 0  ║ 1  ║ 2  ║ 3  ║ 4  ║
              ╠═══╬════╬════╬════╬════╬════╣
              ║ 0 ║ A0 ║ 0  ║ 3  ║ 0  ║ 0  ║
              ║ 1 ║ 0  ║ 0  ║ 3  ║ 2  ║ 0  ║
              ║ 2 ║ 0  ║ a2 ║ S0 ║ s0 ║ 1  ║
              ║ 3 ║ 0  ║ 0  ║ 0  ║ 0  ║ M1 ║
              ║ 4 ║ 0  ║ 0  ║ 4x ║ m2 ║ 0  ║
              ╚═══╩════╩════╩════╩════╩════╝


        */

        retActions = controller.getGame().getPossibleActions(player3, 3,4);
        correctActions.clear();
        correctActions.add(new FinishAction());
        Assert.assertEquals(correctActions.size(), retActions.size());
        for (int i = 0; i < correctActions.size(); i++) {
            Assert.assertEquals(correctActions.get(i).getActionID(), retActions.get(i).getActionID());
        }


        virtualView.update(new ChosenFinishActionEvent(player3,"End turn"));

        System.out.println(player3 + "reset his status");


        //player1's turn

        virtualView.update(new ChosenPawnToUseEvent(player1, 2,1));

        retActions = controller.getGame().getPossibleActions(player1, 2,1);
        correctActions.clear();
        correctActions.add(new MoveAction());
        Assert.assertEquals(correctActions.size(), retActions.size());
        for (int i = 0; i < correctActions.size(); i++) {
            Assert.assertEquals(correctActions.get(i).getActionID(), retActions.get(i).getActionID());
        }

        virtualView.update(new ChosenMoveActionEvent(player1, "Move", 2,1));


        correctCells.clear();
        correctCells.add(controller.getGame().getGameBoard().getCell(1,2));
        correctCells.add(controller.getGame().getGameBoard().getCell(1,1));
        correctCells.add(controller.getGame().getGameBoard().getCell(1,0));
        correctCells.add(controller.getGame().getGameBoard().getCell(2,0));
        correctCells.add(controller.getGame().getGameBoard().getCell(3,0));
        correctCells.add(controller.getGame().getGameBoard().getCell(3,1));
        correctCells.add(controller.getGame().getGameBoard().getCell(3,2));



        retCells = controller.getGame().wherePawnCanMove(player3,2,1);

        correctCells.sort(Comparator.comparingInt(Cell::getRowPosition).thenComparingInt(Cell::getColumnPosition));
        retCells.sort(Comparator.comparingInt(Cell::getRowPosition).thenComparingInt(Cell::getColumnPosition));


        assertEquals(correctCells.size(), retCells.size());

        assertEquals(correctCells, retCells);

        virtualView.update(new ChosenCellToMoveEvent(player1, 2,1,1,2));


         /*

              ╔═══╦════╦════╦════╦════╦════╗
              ║   ║ 0  ║ 1  ║ 2  ║ 3  ║ 4  ║
              ╠═══╬════╬════╬════╬════╬════╣
              ║ 0 ║ A0 ║ 0  ║ 3  ║ 0  ║ 0  ║
              ║ 1 ║ 0  ║ 0  ║ a3 ║ 2  ║ 0  ║
              ║ 2 ║ 0  ║ 2  ║ S0 ║ s0 ║ 1  ║
              ║ 3 ║ 0  ║ 0  ║ 0  ║ 0  ║ M1 ║
              ║ 4 ║ 0  ║ 0  ║ 4x ║ m2 ║ 0  ║
              ╚═══╩════╩════╩════╩════╩════╝


        */

        System.out.println(player1 + " should have won the game");


    }


    @Test
    void backToBackLoss() {

        MVCPreGameStartSetUp();

        Card Prometheus = JsonHandler.deserializeCardList().get(8);

        Card Charon = JsonHandler.deserializeCardList().get(10);

        Card Ares = JsonHandler.deserializeCardList().get(9);

        List<String> chosenCards = new ArrayList<>();
        chosenCards.add(Prometheus.getName());
        chosenCards.add(Charon.getName());
        chosenCards.add(Ares.getName());

       // controller.getPreGameLobby().getPickedCards().add(Prometheus);
       // controller.getPreGameLobby().getPickedCards().add(Charon);
       // controller.getPreGameLobby().getPickedCards().add(Ares);


       // controller.getPreGameLobby().getPickedCards().add(Prometheus);
       // controller.getPreGameLobby().getPickedCards().add(Charon);
       // controller.getPreGameLobby().getPickedCards().add(Ares);

        virtualView.update(new ChosenCardsChallengerEvent(player3, chosenCards));

        virtualView.update(new ChosenCardEvent(player1,Prometheus.getName()));

        virtualView.update(new ChosenCardEvent(player2,Charon.getName()));

        //virtualView.update(new ChosenCardEvent(player3, Ares.getName()));

        virtualView.update(new ChosenFirstPlayerEvent(player3,player1));


        virtualView.update(new ChosenInitialPawnCellEvent(player1, 0,0,0,1));


        virtualView.update(new ChosenInitialPawnCellEvent(player2, 4,4,4,3));


        virtualView.update(new ChosenInitialPawnCellEvent(player3, 2,2,2,3));

        List<Building> buildings = controller.getGame().getGameBoard().getBuildings();

        //to test the end of the game, let's build some blocks to go straight to the end

        controller.getGame().getGameBoard().getCell(1,0).buildOnThisCell(buildings.get(0));
        controller.getGame().getGameBoard().getCell(1,0).buildOnThisCell(buildings.get(1));
        controller.getGame().getGameBoard().getCell(1,0).buildOnThisCell(buildings.get(2));
        controller.getGame().getGameBoard().getCell(1,0).buildOnThisCell(buildings.get(3));
        controller.getGame().getGameBoard().getCell(1,1).buildOnThisCell(buildings.get(0));
        controller.getGame().getGameBoard().getCell(1,1).buildOnThisCell(buildings.get(1));
        controller.getGame().getGameBoard().getCell(1,1).buildOnThisCell(buildings.get(2));
        controller.getGame().getGameBoard().getCell(1,1).buildOnThisCell(buildings.get(3));
        controller.getGame().getGameBoard().getCell(1,2).buildOnThisCell(buildings.get(0));
        controller.getGame().getGameBoard().getCell(1,2).buildOnThisCell(buildings.get(1));
        controller.getGame().getGameBoard().getCell(1,2).buildOnThisCell(buildings.get(2));
        controller.getGame().getGameBoard().getCell(1,2).buildOnThisCell(buildings.get(3));
        controller.getGame().getGameBoard().getCell(0,2).buildOnThisCell(buildings.get(0));
        controller.getGame().getGameBoard().getCell(0,2).buildOnThisCell(buildings.get(1));
        controller.getGame().getGameBoard().getCell(0,2).buildOnThisCell(buildings.get(2));

        //not built a level 4 in [0,2] so the game can start right

        controller.getGame().getGameBoard().getCell(4,2).buildOnThisCell(buildings.get(0));
        controller.getGame().getGameBoard().getCell(4,2).buildOnThisCell(buildings.get(1));
        controller.getGame().getGameBoard().getCell(4,2).buildOnThisCell(buildings.get(2));
        controller.getGame().getGameBoard().getCell(4,2).buildOnThisCell(buildings.get(3));
        controller.getGame().getGameBoard().getCell(3,2).buildOnThisCell(buildings.get(0));
        controller.getGame().getGameBoard().getCell(3,2).buildOnThisCell(buildings.get(1));
        controller.getGame().getGameBoard().getCell(3,2).buildOnThisCell(buildings.get(2));
        controller.getGame().getGameBoard().getCell(3,2).buildOnThisCell(buildings.get(3));
        controller.getGame().getGameBoard().getCell(3,3).buildOnThisCell(buildings.get(0));
        controller.getGame().getGameBoard().getCell(3,3).buildOnThisCell(buildings.get(1));
        controller.getGame().getGameBoard().getCell(3,3).buildOnThisCell(buildings.get(2));
        controller.getGame().getGameBoard().getCell(3,3).buildOnThisCell(buildings.get(3));
        controller.getGame().getGameBoard().getCell(3,4).buildOnThisCell(buildings.get(0));
        controller.getGame().getGameBoard().getCell(3,4).buildOnThisCell(buildings.get(1));
        controller.getGame().getGameBoard().getCell(3,4).buildOnThisCell(buildings.get(2));
        controller.getGame().getGameBoard().getCell(3,4).buildOnThisCell(buildings.get(3));

        /*

              ╔═══╦════╦════╦════╦════╦════╗
              ║   ║ 0  ║ 1  ║ 2  ║ 3  ║ 4  ║
              ╠═══╬════╬════╬════╬════╬════╣
              ║ 0 ║ A0 ║ a0 ║ 3  ║ 0  ║ 0  ║
              ║ 1 ║ 4x ║ 4x ║ 4x ║ 0  ║ 0  ║
              ║ 2 ║ 0  ║ 0  ║ M0 ║ m0 ║ 0  ║
              ║ 3 ║ 0  ║ 0  ║ 4x ║ 4x ║ 4x ║
              ║ 4 ║ 0  ║ 0  ║ 4x ║ s0 ║ S0 ║
              ╚═══╩════╩════╩════╩════╩════╝


        */


        virtualView.update(new ChosenPawnToUseEvent(player1,0,1));

        retActions = controller.getGame().getPossibleActions(player1, 0,1);
        correctActions.clear();
        correctActions.add(new BuildAction());
        Assert.assertEquals(correctActions.size(), retActions.size());
        for (int i = 0; i < correctActions.size(); i++) {
            Assert.assertEquals(correctActions.get(i).getActionID(), retActions.get(i).getActionID());
        }

        virtualView.update(new ChosenBuildActionEvent(player1, "Build", 0,1));

        correctCells.clear();
        correctCells.add(controller.getGame().getGameBoard().getCell(0,2));


        retCells = controller.getGame().wherePawnCanBuild(player1,0,1);

        correctCells.sort(Comparator.comparingInt(Cell::getRowPosition).thenComparingInt(Cell::getColumnPosition));
        retCells.sort(Comparator.comparingInt(Cell::getRowPosition).thenComparingInt(Cell::getColumnPosition));


        assertEquals(correctCells.size(), retCells.size());

        assertEquals(correctCells, retCells);

        virtualView.update(new ChosenCellToBuildEvent(player1, 0,1,0,2));

        virtualView.update(new ChosenBuildingEvent(player1, 4,0,1,0,2));

    }


    @Test
    void fakeLoss() {

        MVCPreGameStartSetUp();

        Card Artemis = JsonHandler.deserializeCardList().get(1);

        Card Atlas = JsonHandler.deserializeCardList().get(3);

        Card Zeus = JsonHandler.deserializeCardList().get(13);

        controller.getPreGameLobby().getPickedCards().add(Artemis);
        controller.getPreGameLobby().getPickedCards().add(Atlas);
        controller.getPreGameLobby().getPickedCards().add(Zeus);

        virtualView.update(new ChosenCardEvent(player1, Artemis.getName()));
        virtualView.update(new ChosenCardEvent(player2, Atlas.getName()));
        virtualView.update(new ChosenCardEvent(player3, Zeus.getName()));





    }


    @Test
    void loosingAtFirstTurn(){

        player1 = "Alfantasy";

        player2 = "Mashu7";

        player3 = "Giammi10";


        virtualView.update(new NewConnectionEvent(player1));

        virtualView.update(new ChosenPlayerNumberEvent(player1, 3));

        virtualView.update(new NewConnectionEvent(player2));

        virtualView.update(new NewConnectionEvent(player3));

        //controller.getPreGameLobby().getPickedCards().clear();


        Card Atlas = JsonHandler.deserializeCardList().get(3);

        Card Demeter = JsonHandler.deserializeCardList().get(4);

        Card Hephaestus = JsonHandler.deserializeCardList().get(5);

        //controller.getPreGameLobby().getPickedCards().add(Atlas);
        //controller.getPreGameLobby().getPickedCards().add(Demeter);
        //controller.getPreGameLobby().getPickedCards().add(Hephaestus);

        List<String> chosenCards = new ArrayList<>();
        chosenCards.add(Atlas.getName());
        chosenCards.add(Demeter.getName());
        chosenCards.add(Hephaestus.getName());

        virtualView.update(new ChosenCardsChallengerEvent(player3,chosenCards));


        virtualView.update(new ChosenCardEvent(player1, Atlas.getName()));


        virtualView.update(new ChosenCardEvent(player2, Demeter.getName()));


        //virtualView.update(new ChosenCardEvent(player3, Hephaestus.getName()));

        virtualView.update(new ChosenFirstPlayerEvent(player3, player1));


        virtualView.update(new ChosenInitialPawnCellEvent(player1, 0,0,0,1));


        virtualView.update(new ChosenInitialPawnCellEvent(player2, 1,0,1,1));


        virtualView.update(new ChosenInitialPawnCellEvent(player3, 0,2,1,2));



        /*

              ╔═══╦════╦════╦════╦════╦════╗
              ║   ║ 0  ║ 1  ║ 2  ║ 3  ║ 4  ║
              ╠═══╬════╬════╬════╬════╬════╣
              ║ 0 ║ A0 ║ a0 ║ G0 ║ 0  ║ 0  ║
              ║ 1 ║ M0 ║ m0 ║ g0 ║ 0  ║ 0  ║
              ║ 2 ║ 0  ║ 0  ║ 0  ║ 0  ║ 0  ║
              ║ 3 ║ 0  ║ 0  ║ 0  ║ 0  ║ 0  ║
              ║ 4 ║ 0  ║ 0  ║ 0  ║ 0  ║ 0  ║
              ╚═══╩════╩════╩════╩════╩════╝


      */

        //It's player1's turn but he can not move, he lost

        System.out.println("Should print a LosingByNoActionEvent with attributes: Alfantasy, So Sad");

        assertEquals("Mashu7", controller.getGame().getPlayersNickname().get(0));
        assertEquals("Giammi10", controller.getGame().getPlayersNickname().get(1));

        /*

              ╔═══╦════╦════╦════╦════╦════╗
              ║   ║ 0  ║ 1  ║ 2  ║ 3  ║ 4  ║
              ╠═══╬════╬════╬════╬════╬════╣
              ║ 0 ║ 0  ║ 0  ║ G0 ║ 0  ║ 0  ║
              ║ 1 ║ M0 ║ m0 ║ g0 ║ 0  ║ 0  ║
              ║ 2 ║ 0  ║ 0  ║ 0  ║ 0  ║ 0  ║
              ║ 3 ║ 0  ║ 0  ║ 0  ║ 0  ║ 0  ║
              ║ 4 ║ 0  ║ 0  ║ 0  ║ 0  ║ 0  ║
              ╚═══╩════╩════╩════╩════╩════╝


      */


        //now it's player2's turn, Mashu7

        virtualView.update(new ChosenPawnToUseEvent(player2,1,0 ));


        //now i try to send a illegal action
        virtualView.update(new ChosenBuildActionEvent(player2, "Build", 1,0 ));


        //now it's legal move
        virtualView.update(new ChosenMoveActionEvent(player2, "Move", 1,0));

        virtualView.update(new ChosenCellToMoveEvent(player2, 1,0,0,0));

        assertEquals(true, controller.getGame().getGameBoard().getCell(0,0).isPawnHere());
        assertEquals(false, controller.getGame().getGameBoard().getCell(1,0).isPawnHere());


    /*

              ╔═══╦════╦════╦════╦════╦════╗
              ║   ║ 0  ║ 1  ║ 2  ║ 3  ║ 4  ║
              ╠═══╬════╬════╬════╬════╬════╣
              ║ 0 ║ M0 ║ 0  ║ G0 ║ 0  ║ 0  ║
              ║ 1 ║ 0  ║ m0 ║ g0 ║ 0  ║ 0  ║
              ║ 2 ║ 0  ║ 0  ║ 0  ║ 0  ║ 0  ║
              ║ 3 ║ 0  ║ 0  ║ 0  ║ 0  ║ 0  ║
              ║ 4 ║ 0  ║ 0  ║ 0  ║ 0  ║ 0  ║
              ╚═══╩════╩════╩════╩════╩════╝

    */


        //now i try to send a illegal action
        virtualView.update(new ChosenFinishActionEvent(player2, "Finish"));

        assertEquals("Build", controller.getGame().getLastActionsList().get(0).getActionID());

        virtualView.update(new ChosenBuildActionEvent(player2, "Build", 0,0));

        virtualView.update(new ChosenCellToBuildEvent(player2, 0,0, 1,0));

        virtualView.update(new ChosenBuildingEvent(player2, 1, 0,0,1, 0));

        assertEquals(1, controller.getGame().getGameBoard().getCell(1,0).getHeight());

        assertEquals("Build", controller.getGame().getLastActionsList().get(1).getActionID());
        assertEquals("End turn", controller.getGame().getLastActionsList().get(0).getActionID());

        virtualView.update(new ChosenFinishActionEvent(player2, "End turn"));



    /*

              ╔═══╦════╦════╦════╦════╦════╗
              ║   ║ 0  ║ 1  ║ 2  ║ 3  ║ 4  ║
              ╠═══╬════╬════╬════╬════╬════╣
              ║ 0 ║ M0 ║ 0  ║ G0 ║ 0  ║ 0  ║
              ║ 1 ║ 1  ║ m0 ║ g0 ║ 0  ║ 0  ║
              ║ 2 ║ 0  ║ 0  ║ 0  ║ 0  ║ 0  ║
              ║ 3 ║ 0  ║ 0  ║ 0  ║ 0  ║ 0  ║
              ║ 4 ║ 0  ║ 0  ║ 0  ║ 0  ║ 0  ║
              ╚═══╩════╩════╩════╩════╩════╝

    */

        //now it's the turn of player3

        //illegal pawn used
        virtualView.update(new ChosenPawnToUseEvent(player3, 0,1));

        virtualView.update(new ChosenPawnToUseEvent(player3, 1,2));

        assertEquals("Move", controller.getGame().getLastActionsList().get(0).getActionID());

        virtualView.update(new ChosenMoveActionEvent(player3, "Move", 1,2));

        virtualView.update(new ChosenCellToMoveEvent(player3, 1,2,2,1));

        assertTrue(controller.getGame().getGameBoard().getCell(2,1).isPawnHere());

        assertEquals("Build", controller.getGame().getLastActionsList().get(0).getActionID());

        virtualView.update(new ChosenBuildActionEvent(player3, "Build",2,1));

        virtualView.update(new ChosenCellToBuildEvent(player3, 2,1,1,2));

        //illegal building
        virtualView.update(new ChosenBuildingEvent(player3,2,2,1,1,2));

        assertNotEquals(2, controller.getGame().getGameBoard().getCell(1, 2).getHeight());

        virtualView.update(new ChosenBuildingEvent(player3,1,2,1,1,2));

        assertEquals(1, controller.getGame().getGameBoard().getCell(1, 2).getHeight());

        assertEquals("End turn", controller.getGame().getLastActionsList().get(0).getActionID());
        assertEquals("Build", controller.getGame().getLastActionsList().get(1).getActionID());

        virtualView.update(new ChosenBuildActionEvent(player3, "Build", 2,1));

        //illegal second build
        virtualView.update(new ChosenCellToBuildEvent(player3, 2,1,3,1));

        assertFalse(controller.getGame().getLastCellsList().contains(controller.getGame().getGameBoard().getCell(3,1)));

        virtualView.update(new ChosenCellToBuildEvent(player3, 2,1, 1,2));

        virtualView.update(new ChosenBuildingEvent(player3, 2, 2,1, 1,2));

        assertEquals(2, controller.getGame().getGameBoard().getCell(1, 2).getHeight());

        assertEquals("End turn", controller.getGame().getLastActionsList().get(0).getActionID());

        virtualView.update(new ChosenFinishActionEvent(player3, "End turn"));


    /*

              ╔═══╦════╦════╦════╦════╦════╗
              ║   ║ 0  ║ 1  ║ 2  ║ 3  ║ 4  ║
              ╠═══╬════╬════╬════╬════╬════╣
              ║ 0 ║ M0 ║ 0  ║ G0 ║ 0  ║ 0  ║
              ║ 1 ║ 1  ║ m0 ║ 2  ║ 0  ║ 0  ║
              ║ 2 ║ 0  ║ g0 ║ 0  ║ 0  ║ 0  ║
              ║ 3 ║ 0  ║ 0  ║ 0  ║ 0  ║ 0  ║
              ║ 4 ║ 0  ║ 0  ║ 0  ║ 0  ║ 0  ║
              ╚═══╩════╩════╩════╩════╩════╝

    */

        //it's player2's turn

        virtualView.update(new ChosenPawnToUseEvent(player2, 1,1));

        assertTrue(controller.getGame().getGameBoard().getPawnByCoordinates(1,1).isChosen());

        //illegal pawn chosen
        virtualView.update(new ChosenMoveActionEvent(player2, "Move", 0,0));

        assertFalse(controller.getGame().getGameBoard().getPawnByCoordinates(0,0).isChosen());

        virtualView.update(new ChosenMoveActionEvent(player2, "Move", 1,1));
        virtualView.update(new ChosenCellToMoveEvent(player2, 1,1,0,1));

        virtualView.update(new ChosenBuildActionEvent(player2, "Build", 0,1));

        virtualView.update(new ChosenCellToBuildEvent(player2, 0,1,1,0));
        virtualView.update(new ChosenBuildingEvent(player2, 2,0,1,1,0));

        virtualView.update(new ChosenFinishActionEvent(player2, "End turn"));



    /*

              ╔═══╦════╦════╦════╦════╦════╗
              ║   ║ 0  ║ 1  ║ 2  ║ 3  ║ 4  ║
              ╠═══╬════╬════╬════╬════╬════╣
              ║ 0 ║ M0 ║ m0 ║ G0 ║ 0  ║ 0  ║
              ║ 1 ║ 2  ║ 0  ║ 2  ║ 0  ║ 0  ║
              ║ 2 ║ 0  ║ g0 ║ 0  ║ 0  ║ 0  ║
              ║ 3 ║ 0  ║ 0  ║ 0  ║ 0  ║ 0  ║
              ║ 4 ║ 0  ║ 0  ║ 0  ║ 0  ║ 0  ║
              ╚═══╩════╩════╩════╩════╩════╝

    */

        //it's player3's turn

        virtualView.update(new ChosenPawnToUseEvent(player3, 2,1));

        virtualView.update(new ChosenMoveActionEvent(player3, "Move", 2,1));

        virtualView.update(new ChosenCellToMoveEvent(player3, 2,1,1,1));

        virtualView.update(new ChosenBuildActionEvent(player3, "Build", 1,1));

        virtualView.update(new ChosenCellToBuildEvent(player3, 1,1,1,0));

        virtualView.update(new ChosenBuildingEvent(player3, 3, 1,1,1,0));

        virtualView.update(new ChosenFinishActionEvent(player3, "End turn"));

        //now player2 lost because he can do no action with his pawns

        assertNull(controller.getGame());




    }


    @Test
    void panVictoryTest(){

        player1 = "Alfantasy";

        player2 = "Mashu7";

        player3 = "Giammi10";


        virtualView.update(new NewConnectionEvent(player1));

        virtualView.update(new ChosenPlayerNumberEvent(player1, 3));

        virtualView.update(new NewConnectionEvent(player2));

        virtualView.update(new NewConnectionEvent(player3));

        controller.getPreGameLobby().getPickedCards().clear();


        Card Pan = JsonHandler.deserializeCardList().get(7);

        Card Ares = JsonHandler.deserializeCardList().get(9);

        Card Hestia = JsonHandler.deserializeCardList().get(11);

        List<String> chosenCards = new ArrayList<>();
        chosenCards.add(Pan.getName());
        chosenCards.add(Ares.getName());
        chosenCards.add(Hestia.getName());

        virtualView.update(new ChosenCardsChallengerEvent(player3, chosenCards));


        //controller.getPreGameLobby().getPickedCards().add(Pan);
        //controller.getPreGameLobby().getPickedCards().add(Ares);
        //controller.getPreGameLobby().getPickedCards().add(Hestia);


        virtualView.update(new ChosenCardEvent(player1, Pan.getName()));


        virtualView.update(new ChosenCardEvent(player2, Ares.getName()));


        //virtualView.update(new ChosenCardEvent(player3, Hestia.getName()));

        virtualView.update(new ChosenFirstPlayerEvent(player3,player1));


        virtualView.update(new ChosenInitialPawnCellEvent(player1, 0,0,0,1));


        virtualView.update(new ChosenInitialPawnCellEvent(player2, 1,2,2,2));


        virtualView.update(new ChosenInitialPawnCellEvent(player3, 3,0,3,1));

        /*

              ╔═══╦════╦════╦════╦════╦════╗
              ║   ║ 0  ║ 1  ║ 2  ║ 3  ║ 4  ║
              ╠═══╬════╬════╬════╬════╬════╣
              ║ 0 ║ A0 ║ a0 ║ 0  ║ 0  ║ 0  ║
              ║ 1 ║ 0  ║ 0  ║ M0 ║ 0  ║ 0  ║
              ║ 2 ║ 0  ║ 0  ║ m0 ║ 0  ║ 0  ║
              ║ 3 ║ g0 ║ G0 ║ 0  ║ 0  ║ 0  ║
              ║ 4 ║ 0  ║ 0  ║ 0  ║ 0  ║ 0  ║
              ╚═══╩════╩════╩════╩════╩════╝


      */

       // it's player1's first turn

       assertEquals("Alfantasy", controller.getGame().getCurrentPlayer());

       controller.update(new ChosenPawnToUseEvent(player1, 0,0));

       controller.update(new ChosenMoveActionEvent(player1, "Move",0, 0));

       controller.update(new ChosenCellToMoveEvent(player1, 0,0,1,0));

       controller.update(new ChosenBuildActionEvent(player1, "Build", 1,0));

       controller.update(new ChosenCellToBuildEvent(player1, 1,0,0,0));

       controller.update(new ChosenBuildingEvent(player1,1,1,0, 0,0));

       controller.update(new ChosenFinishActionEvent(player1, "End turn"));

       assertTrue(controller.getGame().getGameBoard().getCell(1,0).isPawnHere());
       assertEquals(controller.getGame().getGameBoard().getCell(1,0), controller.getGame().getPlayerByName("Alfantasy").getPawnInCoordinates(1,0).getPosition());
       assertEquals(1, controller.getGame().getGameBoard().getCell(0,0).getHeight());



      /*

              ╔═══╦════╦════╦════╦════╦════╗
              ║   ║ 0  ║ 1  ║ 2  ║ 3  ║ 4  ║
              ╠═══╬════╬════╬════╬════╬════╣
              ║ 0 ║ 1  ║ a0 ║ 0  ║ 0  ║ 0  ║
              ║ 1 ║ A0 ║ 0  ║ M0 ║ 0  ║ 0  ║
              ║ 2 ║ 0  ║ 0  ║ m0 ║ 0  ║ 0  ║
              ║ 3 ║ g0 ║ G0 ║ 0  ║ 0  ║ 0  ║
              ║ 4 ║ 0  ║ 0  ║ 0  ║ 0  ║ 0  ║
              ╚═══╩════╩════╩════╩════╩════╝

      */

       // player 2's turn

       assertEquals("Mashu7", controller.getGame().getCurrentPlayer());

       controller.update(new ChosenPawnToUseEvent(player2,1,2));

       controller.update(new ChosenMoveActionEvent(player2, "Move", 1,2));

       controller.update(new ChosenCellToMoveEvent(player2, 1,2,1,1));

        controller.update(new ChosenBuildActionEvent(player2, "Build", 1,1));

        controller.update(new ChosenCellToBuildEvent(player2, 1,1,2,1));

        controller.update(new ChosenBuildingEvent(player2,1,1,1, 2,1));

        controller.update(new ChosenFinishActionEvent(player2, "End turn"));

        assertTrue(controller.getGame().getGameBoard().getCell(1,1).isPawnHere());
        assertEquals(controller.getGame().getGameBoard().getCell(1,1), controller.getGame().getPlayerByName(player2).getPawnInCoordinates(1,1).getPosition());
        assertEquals(1, controller.getGame().getGameBoard().getCell(2,1).getHeight());

        /*

              ╔═══╦════╦════╦════╦════╦════╗
              ║   ║ 0  ║ 1  ║ 2  ║ 3  ║ 4  ║
              ╠═══╬════╬════╬════╬════╬════╣
              ║ 0 ║ 1  ║ a0 ║ 0  ║ 0  ║ 0  ║
              ║ 1 ║ A0 ║ M0 ║ 0  ║ 0  ║ 0  ║
              ║ 2 ║ 0  ║ 1  ║ m0 ║ 0  ║ 0  ║
              ║ 3 ║ g0 ║ G0 ║ 0  ║ 0  ║ 0  ║
              ║ 4 ║ 0  ║ 0  ║ 0  ║ 0  ║ 0  ║
              ╚═══╩════╩════╩════╩════╩════╝

      */

        // player 3's turn

        assertEquals("Giammi10", controller.getGame().getCurrentPlayer());

        controller.update(new ChosenPawnToUseEvent(player3,3,1));

        controller.update(new ChosenMoveActionEvent(player3, "Move", 3,1));

        controller.update(new ChosenCellToMoveEvent(player3, 3,1,2,1));

        controller.update(new ChosenBuildActionEvent(player3, "Build", 2,1));

        controller.update(new ChosenCellToBuildEvent(player3, 2,1,2,0));

        controller.update(new ChosenBuildingEvent(player3,1,2,1, 2,0));

        controller.update(new ChosenBuildActionEvent(player3, "Build", 2,1));

        //illegal build
        controller.update(new ChosenCellToBuildEvent(player3, 2,1,2,0));

        //legal build
        controller.update(new ChosenCellToBuildEvent(player3, 2,1,1,2));

        controller.update(new ChosenBuildingEvent(player3,1,2,1, 1,2));

        controller.update(new ChosenFinishActionEvent(player3, "End turn"));

        assertTrue(controller.getGame().getGameBoard().getCell(2,1).isPawnHere());
        assertEquals(controller.getGame().getGameBoard().getCell(2,1), controller.getGame().getPlayerByName(player3).getPawnInCoordinates(2,1).getPosition());
        assertEquals(1, controller.getGame().getGameBoard().getCell(2,0).getHeight());
        assertEquals(1, controller.getGame().getGameBoard().getCell(1,2).getHeight());

        /*

              ╔═══╦════╦════╦════╦════╦════╗
              ║   ║ 0  ║ 1  ║ 2  ║ 3  ║ 4  ║
              ╠═══╬════╬════╬════╬════╬════╣
              ║ 0 ║ 1  ║ a0 ║ 0  ║ 0  ║ 0  ║
              ║ 1 ║ A0 ║ M0 ║ 1  ║ 0  ║ 0  ║
              ║ 2 ║ 1  ║ G1 ║ m0 ║ 0  ║ 0  ║
              ║ 3 ║ g0 ║ 0  ║ 0  ║ 0  ║ 0  ║
              ║ 4 ║ 0  ║ 0  ║ 0  ║ 0  ║ 0  ║
              ╚═══╩════╩════╩════╩════╩════╝

      */

        // it's player1's turn

        assertEquals("Alfantasy", controller.getGame().getCurrentPlayer());

        controller.update(new ChosenPawnToUseEvent(player1, 1,0));

        controller.update(new ChosenMoveActionEvent(player1, "Move",1, 0));

        controller.update(new ChosenCellToMoveEvent(player1, 1,0,0,0));

        controller.update(new ChosenBuildActionEvent(player1, "Build", 0,0));

        controller.update(new ChosenCellToBuildEvent(player1, 0,0,1,0));

        controller.update(new ChosenBuildingEvent(player1,1,0,0, 1,0));

        controller.update(new ChosenFinishActionEvent(player1, "End turn"));

        assertTrue(controller.getGame().getGameBoard().getCell(0,0).isPawnHere());
        assertFalse(controller.getGame().getGameBoard().getCell(1,0).isPawnHere());
        assertEquals(controller.getGame().getGameBoard().getCell(0,0), controller.getGame().getPlayerByName("Alfantasy").getPawnInCoordinates(0,0).getPosition());
        assertEquals(1, controller.getGame().getGameBoard().getCell(1,0).getHeight());

       /*

              ╔═══╦════╦════╦════╦════╦════╗
              ║   ║ 0  ║ 1  ║ 2  ║ 3  ║ 4  ║
              ╠═══╬════╬════╬════╬════╬════╣
              ║ 0 ║ A1 ║ a0 ║ 0  ║ 0  ║ 0  ║
              ║ 1 ║ 1  ║ M0 ║ 1  ║ 0  ║ 0  ║
              ║ 2 ║ 1  ║ G1 ║ m0 ║ 0  ║ 0  ║
              ║ 3 ║ g0 ║ 0  ║ 0  ║ 0  ║ 0  ║
              ║ 4 ║ 0  ║ 0  ║ 0  ║ 0  ║ 0  ║
              ╚═══╩════╩════╩════╩════╩════╝

      */

        // player 2's turn

        assertEquals("Mashu7", controller.getGame().getCurrentPlayer());

        controller.update(new ChosenPawnToUseEvent(player2,1,1));

        controller.update(new ChosenMoveActionEvent(player2, "Move", 1,1));

        controller.update(new ChosenCellToMoveEvent(player2, 1,1,2,0));

        controller.update(new ChosenBuildActionEvent(player2, "Build", 2,0));

        controller.update(new ChosenCellToBuildEvent(player2, 2,0,1,0));

        controller.update(new ChosenBuildingEvent(player2,2,2,0, 1,0));

        controller.update(new ChosenDestroyActionEvent(player2, "Destroy", 2,0 ));

        //illegal destroy cell
        controller.update(new ChosenCellToDestroyEvent(player2, 2,0,1,0));

        controller.update(new ChosenCellToDestroyEvent(player2, 2,0, 1,2));

        controller.update(new ChosenFinishActionEvent(player2, "End turn"));

        assertTrue(controller.getGame().getGameBoard().getCell(2,0).isPawnHere());
        assertEquals(controller.getGame().getGameBoard().getCell(2,0), controller.getGame().getPlayerByName(player2).getPawnInCoordinates(2,0).getPosition());
        assertEquals(2, controller.getGame().getGameBoard().getCell(1,0).getHeight());
        assertEquals(0, controller.getGame().getGameBoard().getCell(1,2).getHeight());

       /*

              ╔═══╦════╦════╦════╦════╦════╗
              ║   ║ 0  ║ 1  ║ 2  ║ 3  ║ 4  ║
              ╠═══╬════╬════╬════╬════╬════╣
              ║ 0 ║ A1 ║ a0 ║ 0  ║ 0  ║ 0  ║
              ║ 1 ║ 2  ║ 0  ║ 0  ║ 0  ║ 0  ║
              ║ 2 ║ M1 ║ G1 ║ m0 ║ 0  ║ 0  ║
              ║ 3 ║ g0 ║ 0  ║ 0  ║ 0  ║ 0  ║
              ║ 4 ║ 0  ║ 0  ║ 0  ║ 0  ║ 0  ║
              ╚═══╩════╩════╩════╩════╩════╝

      */

        // player 3's turn

        assertEquals("Giammi10", controller.getGame().getCurrentPlayer());

        controller.update(new ChosenPawnToUseEvent(player3,2,1));

        controller.update(new ChosenMoveActionEvent(player3, "Move", 2,1));

        controller.update(new ChosenCellToMoveEvent(player3, 2,1,1,2));

        controller.update(new ChosenBuildActionEvent(player3, "Build", 1,2));

        controller.update(new ChosenCellToBuildEvent(player3, 1,2,0,2));

        controller.update(new ChosenBuildingEvent(player3,1,1,2, 0,2));

        assertEquals(BuildAndFinishState.class, controller.getGame().getPlayerByName("Giammi10").getEffect().getState().getClass());

        controller.update(new ChosenFinishActionEvent(player3, "End turn"));


        assertTrue(controller.getGame().getGameBoard().getCell(1,2).isPawnHere());
        assertEquals(controller.getGame().getGameBoard().getCell(1,2), controller.getGame().getPlayerByName(player3).getPawnInCoordinates(1,2).getPosition());
        assertEquals(1, controller.getGame().getGameBoard().getCell(0,2).getHeight());

       /*

              ╔═══╦════╦════╦════╦════╦════╗
              ║   ║ 0  ║ 1  ║ 2  ║ 3  ║ 4  ║
              ╠═══╬════╬════╬════╬════╬════╣
              ║ 0 ║ A1 ║ a0 ║ 1  ║ 0  ║ 0  ║
              ║ 1 ║ 2  ║ 0  ║ G0 ║ 0  ║ 0  ║
              ║ 2 ║ M1 ║ 1  ║ m0 ║ 0  ║ 0  ║
              ║ 3 ║ g0 ║ 0  ║ 0  ║ 0  ║ 0  ║
              ║ 4 ║ 0  ║ 0  ║ 0  ║ 0  ║ 0  ║
              ╚═══╩════╩════╩════╩════╩════╝

      */

        // it's player1's turn

        assertEquals("Alfantasy", controller.getGame().getCurrentPlayer());

        controller.update(new ChosenPawnToUseEvent(player1, 0,0));

        controller.update(new ChosenMoveActionEvent(player1, "Move",0, 0));

        controller.update(new ChosenCellToMoveEvent(player1, 0,0,1,0));

        controller.update(new ChosenBuildActionEvent(player1, "Build", 1,0));

        controller.update(new ChosenCellToBuildEvent(player1, 1,0,0,0));

        controller.update(new ChosenBuildingEvent(player1,2,1,0, 0,0));

        controller.update(new ChosenFinishActionEvent(player1, "End turn"));

        assertTrue(controller.getGame().getGameBoard().getCell(1,0).isPawnHere());
        assertFalse(controller.getGame().getGameBoard().getCell(0,0).isPawnHere());
        assertEquals(controller.getGame().getGameBoard().getCell(1,0), controller.getGame().getPlayerByName("Alfantasy").getPawnInCoordinates(1,0).getPosition());
        assertEquals(2, controller.getGame().getGameBoard().getCell(0,0).getHeight());

      /*

              ╔═══╦════╦════╦════╦════╦════╗
              ║   ║ 0  ║ 1  ║ 2  ║ 3  ║ 4  ║
              ╠═══╬════╬════╬════╬════╬════╣
              ║ 0 ║ 2  ║ a0 ║ 1  ║ 0  ║ 0  ║
              ║ 1 ║ A2 ║ 0  ║ G0 ║ 0  ║ 0  ║
              ║ 2 ║ M1 ║ 1  ║ m0 ║ 0  ║ 0  ║
              ║ 3 ║ g0 ║ 0  ║ 0  ║ 0  ║ 0  ║
              ║ 4 ║ 0  ║ 0  ║ 0  ║ 0  ║ 0  ║
              ╚═══╩════╩════╩════╩════╩════╝

      */

        // player 2's turn

        assertEquals("Mashu7", controller.getGame().getCurrentPlayer());

        controller.update(new ChosenPawnToUseEvent(player2,2,2));

        controller.update(new ChosenMoveActionEvent(player2, "Move", 2,2));

        controller.update(new ChosenCellToMoveEvent(player2, 2,2,2,3));

        controller.update(new ChosenBuildActionEvent(player2, "Build", 2,3));

        controller.update(new ChosenCellToBuildEvent(player2, 2,3,2,4));

        controller.update(new ChosenBuildingEvent(player2,1,2,3, 2,4));


        controller.update(new ChosenFinishActionEvent(player2, "End turn"));

        assertTrue(controller.getGame().getGameBoard().getCell(2,3).isPawnHere());
        assertEquals(controller.getGame().getGameBoard().getCell(2,3), controller.getGame().getPlayerByName(player2).getPawnInCoordinates(2,3).getPosition());
        assertEquals(1, controller.getGame().getGameBoard().getCell(2,4).getHeight());

        /*

              ╔═══╦════╦════╦════╦════╦════╗
              ║   ║ 0  ║ 1  ║ 2  ║ 3  ║ 4  ║
              ╠═══╬════╬════╬════╬════╬════╣
              ║ 0 ║ 2  ║ a0 ║ 1  ║ 0  ║ 0  ║
              ║ 1 ║ A2 ║ 0  ║ G0 ║ 0  ║ 0  ║
              ║ 2 ║ M1 ║ 1  ║ 0  ║ m0 ║ 1  ║
              ║ 3 ║ g0 ║ 0  ║ 0  ║ 0  ║ 0  ║
              ║ 4 ║ 0  ║ 0  ║ 0  ║ 0  ║ 0  ║
              ╚═══╩════╩════╩════╩════╩════╝

      */

        // player 3's turn

        assertEquals("Giammi10", controller.getGame().getCurrentPlayer());

        controller.update(new ChosenPawnToUseEvent(player3,3,0));

        controller.update(new ChosenMoveActionEvent(player3, "Move", 3,0));

        controller.update(new ChosenCellToMoveEvent(player3, 3,0,4,0));

        assertEquals(BuildState.class, controller.getGame().getPlayerByName("Giammi10").getEffect().getState().getClass());

        controller.update(new ChosenBuildActionEvent(player3, "Build", 4,0));

        controller.update(new ChosenCellToBuildEvent(player3, 4,0,4,1));

        controller.update(new ChosenBuildingEvent(player3,1,4,0, 4,1));

        assertEquals(BuildAndFinishState.class, controller.getGame().getPlayerByName("Giammi10").getEffect().getState().getClass());

        controller.update(new ChosenFinishActionEvent(player3, "End turn"));


        assertTrue(controller.getGame().getGameBoard().getCell(4,0).isPawnHere());
        assertEquals(controller.getGame().getGameBoard().getCell(4,0), controller.getGame().getPlayerByName(player3).getPawnInCoordinates(4,0).getPosition());
        assertEquals(1, controller.getGame().getGameBoard().getCell(4,1).getHeight());

       /*

              ╔═══╦════╦════╦════╦════╦════╗
              ║   ║ 0  ║ 1  ║ 2  ║ 3  ║ 4  ║
              ╠═══╬════╬════╬════╬════╬════╣
              ║ 0 ║ 2  ║ a0 ║ 1  ║ 0  ║ 0  ║
              ║ 1 ║ A2 ║ 0  ║ G0 ║ 0  ║ 0  ║
              ║ 2 ║ M1 ║ 1  ║ 0  ║ m0 ║ 1  ║
              ║ 3 ║  0 ║ 0  ║ 0  ║ 0  ║ 0  ║
              ║ 4 ║ g0 ║ 1  ║ 0  ║ 0  ║ 0  ║
              ╚═══╩════╩════╩════╩════╩════╝

      */

        // it's player1's turn, victory using Pan effect

        assertEquals("Alfantasy", controller.getGame().getCurrentPlayer());

        controller.update(new ChosenPawnToUseEvent(player1, 1,0));

        controller.update(new ChosenMoveActionEvent(player1, "Move",1, 0));

        controller.update(new ChosenCellToMoveEvent(player1, 1,0,1,1));

        //Pan won the game, so controller ends game and send Victory Event to Clients and field game becomes Null

        assertNull(controller.getGame());

      /*

              ╔═══╦════╦════╦════╦════╦════╗
              ║   ║ 0  ║ 1  ║ 2  ║ 3  ║ 4  ║
              ╠═══╬════╬════╬════╬════╬════╣
              ║ 0 ║ 2  ║ a0 ║ 1  ║ 0  ║ 0  ║
              ║ 1 ║ 2  ║ A0 ║ G0 ║ 0  ║ 0  ║
              ║ 2 ║ M1 ║ 1  ║ 0  ║ m0 ║ 1  ║
              ║ 3 ║  0 ║ 0  ║ 0  ║ 0  ║ 0  ║
              ║ 4 ║ g0 ║ 1  ║ 0  ║ 0  ║ 0  ║
              ╚═══╩════╩════╩════╩════╩════╝

      */




    }

    @Test
    void disconnectionControllerTest(){

        Controller controllerTest = new Controller();

        player1 = "Alfantasy";

        player2 = "Mashu7";

        virtualView.update(new NewConnectionEvent(player1));

        virtualView.update(new ChosenPlayerNumberEvent(player1, 3));

        virtualView.update(new NewConnectionEvent(player2));

        virtualView.update(new ClientDisconnectionEvent(player2));

        assertEquals(player1, controller.getPreGameLobby().getConnectedPlayers().get(0));

        assertEquals(3, controller.getPreGameLobby().getNumberOfPlayers());

        virtualView.update(new ClientDisconnectionEvent(player1));

        assertEquals(-1, controller.getPreGameLobby().getNumberOfPlayers());




    }


    @Test
    void disconnectionDuringGameControllerTest(){

        player1 = "Alfantasy";

        player2 = "Mashu7";

        player3 = "Giammi10";


        virtualView.update(new NewConnectionEvent(player1));

        virtualView.update(new ChosenPlayerNumberEvent(player1, 3));

        virtualView.update(new NewConnectionEvent(player2));

        virtualView.update(new NewConnectionEvent(player3));

        controller.getPreGameLobby().getPickedCards().clear();


        Card Atlas = JsonHandler.deserializeCardList().get(3);

        Card Demeter = JsonHandler.deserializeCardList().get(4);

        Card Hephaestus = JsonHandler.deserializeCardList().get(5);

        controller.getPreGameLobby().getPickedCards().add(Atlas);
        controller.getPreGameLobby().getPickedCards().add(Demeter);
        controller.getPreGameLobby().getPickedCards().add(Hephaestus);


        virtualView.update(new ChosenCardEvent(player1, Atlas.getName()));

        virtualView.update(new ClientDisconnectionEvent(player2));

        assertNull(controller.getGame());


    }

    @Test
    void challengerEvents(){

        player1 = "Alfantasy";

        player2 = "Mashu7";

        List<String> chosenCard = new ArrayList<>();

        chosenCard.add(JsonHandler.deserializeCardList().get(0).getName());

        chosenCard.add(JsonHandler.deserializeCardList().get(1).getName());

        virtualView.update(new NewConnectionEvent(player1));

        virtualView.update(new ChosenPlayerNumberEvent(player1, 2));

        virtualView.update(new NewConnectionEvent(player2));

        virtualView.update(new ChosenCardsChallengerEvent(player2, chosenCard));

        virtualView.update(new ChosenCardEvent(player1, chosenCard.get(0)));

        virtualView.update(new ChosenFirstPlayerEvent(player2, player2));

        assertEquals(player2, controller.getGame().getCurrentPlayer());



    }
}
