package it.polimi.ingsw;

import static org.junit.Assert.assertTrue;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.events.CTSEvents.*;
import it.polimi.ingsw.events.ServerToClientEvent;
import it.polimi.ingsw.model.Actions.Action;
import it.polimi.ingsw.model.Actions.BuildAction;
import it.polimi.ingsw.model.Actions.FinishAction;
import it.polimi.ingsw.model.Actions.MoveAction;
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

    void preGameSettingsSetUp() {


        virtualView.update(new NewConnectionEvent(player1));

        virtualView.update(new ChosenPlayerNumberEvent(player1, 3));

        virtualView.update(new NewConnectionEvent(player2));

        virtualView.update(new NewConnectionEvent(player3));


        card1 = controller.getPreGameLobby().getPickedCards().get(0).getName();
        card2 = controller.getPreGameLobby().getPickedCards().get(1).getName();
        card3 = controller.getPreGameLobby().getPickedCards().get(2).getName();

        effect1 = controller.getPreGameLobby().getPickedCards().get(0).getEffectDescription();
        effect2 = controller.getPreGameLobby().getPickedCards().get(1).getEffectDescription();
        effect3 = controller.getPreGameLobby().getPickedCards().get(2).getEffectDescription();


        virtualView.update(new ChosenCardEvent(player1,card1));


        virtualView.update(new ChosenCardEvent(player2,card2));


        virtualView.update(new ChosenCardEvent(player3, card3));


        virtualView.update(new ChosenInitialPawnCellEvent(player1, 0,0,1,1));


        virtualView.update(new ChosenInitialPawnCellEvent(player2, 2,2,3,3));


        virtualView.update(new ChosenInitialPawnCellEvent(player3, 4,4,2,3));

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
    void preGameSettings() {

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
    void ApolloAthenaArtemis() {

        preGameSettingsSetUp();

        //alfonso's turn

       virtualView.update(new ChosenPawnToUseEvent(player1,0,0));


//      retActions = gameTest.getPossibleActions("alfonso", 0,0);
//      correctActions.add(new MoveAction());

//      Assert.assertEquals(correctActions.get(0).getActionID(), retActions.get(0).getActionID());

//      gameTest.movePawn("alfonso", 0,0, 0,1);

//      /*

//              ╔═══╦════╦════╦════╦════╦════╗
//              ║   ║ 0  ║ 1  ║ 2  ║ 3  ║ 4  ║
//              ╠═══╬════╬════╬════╬════╬════╣
//              ║ 0 ║ 0  ║ A0 ║ 0  ║ 0  ║ 0  ║
//              ║ 1 ║ 0  ║ a  ║ 0  ║ 0  ║ 0  ║
//              ║ 2 ║ 0  ║ 0  ║ M  ║ g  ║ 0  ║
//              ║ 3 ║ 0  ║ 0  ║ 0  ║ m  ║ 0  ║
//              ║ 4 ║ 0  ║ 0  ║ 0  ║ 0  ║ G  ║
//              ╚═══╩════╩════╩════╩════╩════╝


//      */

//      retActions = gameTest.getPossibleActions("alfonso", 0,1);
//      correctActions.clear();
//      correctActions.add(new BuildAction());

//      Assert.assertEquals(correctActions.get(0).getActionID(), retActions.get(0).getActionID());

//      gameTest.pawnBuild("alfonso", 0,1, 1,0, 0);


//      /*

//              ╔═══╦════╦════╦════╦════╦════╗
//              ║   ║ 0  ║ 1  ║ 2  ║ 3  ║ 4  ║
//              ╠═══╬════╬════╬════╬════╬════╣
//              ║ 0 ║ 0  ║ A0 ║ 0  ║ 0  ║ 0  ║
//              ║ 1 ║ 1  ║ a  ║ 0  ║ 0  ║ 0  ║
//              ║ 2 ║ 0  ║ 0  ║ M  ║ g  ║ 0  ║
//              ║ 3 ║ 0  ║ 0  ║ 0  ║ m  ║ 0  ║
//              ║ 4 ║ 0  ║ 0  ║ 0  ║ 0  ║ G  ║
//              ╚═══╩════╩════╩════╩════╩════╝


//      */

//      retActions = gameTest.getPossibleActions("alfonso", 0,1);
//      correctActions.clear();
//      correctActions.add(new FinishAction());

//      Assert.assertEquals(correctActions.get(0).getActionID(), retActions.get(0).getActionID());

//      gameTest.resetPlayerStatus("alfonso");

//      //massi's turn

//      retActions = gameTest.getPossibleActions("massi", 2,2);
//      correctActions.clear();
//      correctActions.add(new MoveAction());

//      Assert.assertEquals(correctActions.get(0).getActionID(), retActions.get(0).getActionID());


//      gameTest.movePawn("massi", 2,2, 2,1);

//      /*

//              ╔═══╦════╦════╦════╦════╦════╗
//              ║   ║ 0  ║ 1  ║ 2  ║ 3  ║ 4  ║
//              ╠═══╬════╬════╬════╬════╬════╣
//              ║ 0 ║ 0  ║ A0 ║ 0  ║ 0  ║ 0  ║
//              ║ 1 ║ 1  ║ a  ║ 0  ║ 0  ║ 0  ║
//              ║ 2 ║ 0  ║ M  ║ 0  ║ g  ║ 0  ║
//              ║ 3 ║ 0  ║ 0  ║ 0  ║ m  ║ 0  ║
//              ║ 4 ║ 0  ║ 0  ║ 0  ║ 0  ║ G  ║
//              ╚═══╩════╩════╩════╩════╩════╝


//      */

//      retActions = gameTest.getPossibleActions("massi", 2,1);
//      correctActions.clear();
//      correctActions.add(new MoveAction());
//      correctActions.add(new BuildAction());

//      Assert.assertEquals(correctActions.size(), retActions.size());

//      for (int i = 0; i < correctActions.size(); i++) {
//          Assert.assertEquals(correctActions.get(i).getActionID(), retActions.get(i).getActionID());

//      }

//      gameTest.movePawn("massi", 2,1,3,1);

//      /*

//              ╔═══╦════╦════╦════╦════╦════╗
//              ║   ║ 0  ║ 1  ║ 2  ║ 3  ║ 4  ║
//              ╠═══╬════╬════╬════╬════╬════╣
//              ║ 0 ║ 0  ║ A0 ║ 0  ║ 0  ║ 0  ║
//              ║ 1 ║ 1  ║ a  ║ 0  ║ 0  ║ 0  ║
//              ║ 2 ║ 0  ║ 0  ║ 0  ║ g  ║ 0  ║
//              ║ 3 ║ 0  ║ M  ║ 0  ║ m  ║ 0  ║
//              ║ 4 ║ 0  ║ 0  ║ 0  ║ 0  ║ G  ║
//              ╚═══╩════╩════╩════╩════╩════╝


//      */


//      retActions = gameTest.getPossibleActions("massi", 3,1);
//      correctActions.clear();
//      correctActions.add(new BuildAction());

//      Assert.assertEquals(correctActions.size(), retActions.size());

//      for (int i = 0; i < correctActions.size(); i++) {
//          Assert.assertEquals(correctActions.get(i).getActionID(), retActions.get(i).getActionID());

//      }

//      gameTest.pawnBuild("massi", 3,1,4,0,1);

//      /*

//              ╔═══╦════╦════╦════╦════╦════╗
//              ║   ║ 0  ║ 1  ║ 2  ║ 3  ║ 4  ║
//              ╠═══╬════╬════╬════╬════╬════╣
//              ║ 0 ║ 0  ║ A0 ║ 0  ║ 0  ║ 0  ║
//              ║ 1 ║ 1  ║ a  ║ 0  ║ 0  ║ 0  ║
//              ║ 2 ║ 0  ║ 0  ║ 0  ║ g  ║ 0  ║
//              ║ 3 ║ 0  ║ M  ║ 0  ║ m  ║ 0  ║
//              ║ 4 ║ 1  ║ 0  ║ 0  ║ 0  ║ G  ║
//              ╚═══╩════╩════╩════╩════╩════╝


//      */

//      retActions = gameTest.getPossibleActions("massi", 3,1);
//      correctActions.clear();
//      correctActions.add(new FinishAction());

//      Assert.assertEquals(correctActions.size(), retActions.size());

//      for (int i = 0; i < correctActions.size(); i++) {
//          Assert.assertEquals(correctActions.get(i).getActionID(), retActions.get(i).getActionID());

//      }

//      gameTest.resetPlayerStatus("massi");

//      //giamma's turn

//      retActions = gameTest.getPossibleActions("giamma", 2,3);
//      correctActions.clear();
//      correctActions.add(new MoveAction());

//      Assert.assertEquals(correctActions.size(), retActions.size());

//      for (int i = 0; i < correctActions.size(); i++) {
//          Assert.assertEquals(correctActions.get(i).getActionID(), retActions.get(i).getActionID());

//      }

//      gameTest.movePawn("giamma", 2,3,1,2);

//      /*

//              ╔═══╦════╦════╦════╦════╦════╗
//              ║   ║ 0  ║ 1  ║ 2  ║ 3  ║ 4  ║
//              ╠═══╬════╬════╬════╬════╬════╣
//              ║ 0 ║ 0  ║ A0 ║ 0  ║ 0  ║ 0  ║
//              ║ 1 ║ 1  ║ a  ║ g  ║ 0  ║ 0  ║
//              ║ 2 ║ 0  ║ 0  ║ 0  ║ 0  ║ 0  ║
//              ║ 3 ║ 0  ║ M  ║ 0  ║ m  ║ 0  ║
//              ║ 4 ║ 1  ║ 0  ║ 0  ║ 0  ║ G  ║
//              ╚═══╩════╩════╩════╩════╩════╝


//      */

//      retActions = gameTest.getPossibleActions("giamma", 1,2);
//      correctActions.clear();
//      correctActions.add(new BuildAction());

//      Assert.assertEquals(correctActions.size(), retActions.size());

//      for (int i = 0; i < correctActions.size(); i++) {
//          Assert.assertEquals(correctActions.get(i).getActionID(), retActions.get(i).getActionID());

//      }

//      gameTest.pawnBuild("giamma", 1,2, 0,2,1);


//      /*

//              ╔═══╦════╦════╦════╦════╦════╗
//              ║   ║ 0  ║ 1  ║ 2  ║ 3  ║ 4  ║
//              ╠═══╬════╬════╬════╬════╬════╣
//              ║ 0 ║ 0  ║ A0 ║ 1  ║ 0  ║ 0  ║
//              ║ 1 ║ 1  ║ a0 ║ g0 ║ 0  ║ 0  ║
//              ║ 2 ║ 0  ║ 0  ║ 0  ║ 0  ║ 0  ║
//              ║ 3 ║ 0  ║ M0 ║ 0  ║ m0 ║ 0  ║
//              ║ 4 ║ 1  ║ 0  ║ 0  ║ 0  ║ G0 ║
//              ╚═══╩════╩════╩════╩════╩════╝


//      */


//      retActions = gameTest.getPossibleActions("giamma", 1,2);
//      correctActions.clear();
//      correctActions.add(new FinishAction());

//      Assert.assertEquals(correctActions.size(), retActions.size());

//      for (int i = 0; i < correctActions.size(); i++) {
//          Assert.assertEquals(correctActions.get(i).getActionID(), retActions.get(i).getActionID());

//      }

//      gameTest.resetPlayerStatus("giamma");


//      //alfonso's turn

//      retActions = gameTest.getPossibleActions("alfonso", 0,1);
//      correctActions.clear();
//      correctActions.add(new MoveAction());

//      Assert.assertEquals(correctActions.size(), retActions.size());

//      for (int i = 0; i < correctActions.size(); i++) {
//          Assert.assertEquals(correctActions.get(i).getActionID(), retActions.get(i).getActionID());

//      }

//      gameTest.movePawn("alfonso", 0,1, 1,2);

//      /*

//              ╔═══╦════╦════╦════╦════╦════╗
//              ║   ║ 0  ║ 1  ║ 2  ║ 3  ║ 4  ║
//              ╠═══╬════╬════╬════╬════╬════╣
//              ║ 0 ║ 0  ║ g0 ║ 1  ║ 0  ║ 0  ║
//              ║ 1 ║ 1  ║ a0 ║ A0 ║ 0  ║ 0  ║
//              ║ 2 ║ 0  ║ 0  ║ 0  ║ 0  ║ 0  ║
//              ║ 3 ║ 0  ║ M0 ║ 0  ║ m0 ║ 0  ║
//              ║ 4 ║ 1  ║ 0  ║ 0  ║ 0  ║ G0 ║
//              ╚═══╩════╩════╩════╩════╩════╝

//      */


//      retActions = gameTest.getPossibleActions("alfonso", 1,2);
//      correctActions.clear();
//      correctActions.add(new BuildAction());

//      Assert.assertEquals(correctActions.size(), retActions.size());

//      for (int i = 0; i < correctActions.size(); i++) {
//          Assert.assertEquals(correctActions.get(i).getActionID(), retActions.get(i).getActionID());

//      }



    }

}
