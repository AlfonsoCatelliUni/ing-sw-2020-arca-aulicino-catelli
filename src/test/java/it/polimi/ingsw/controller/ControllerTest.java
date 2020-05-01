package it.polimi.ingsw.controller;

import it.polimi.ingsw.client.ClientJsonHandler;
import it.polimi.ingsw.client.Couple;
import it.polimi.ingsw.events.CTSEvents.ChosenCellToMoveEvent;
import it.polimi.ingsw.events.CTSEvents.NewConnectionEvent;
import it.polimi.ingsw.events.ClientToServerEvent;
import it.polimi.ingsw.events.STCEvents.GivePossibleActionsEvent;
import it.polimi.ingsw.events.STCEvents.LosingByNoActionEvent;
import it.polimi.ingsw.model.Actions.*;
import it.polimi.ingsw.model.Board.Building;
import it.polimi.ingsw.model.Board.Cell;
import it.polimi.ingsw.model.Player.Card;
import it.polimi.ingsw.model.Player.State.BuildAndFinishState;
import it.polimi.ingsw.model.Player.State.MoveState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w3c.dom.events.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {

    private Controller controller;

    private List<String> nicknames;

    private Random random;

    @BeforeEach
    void setUp() {

        controller = new Controller();

        random = new Random();

        nicknames = new ArrayList<>();
        nicknames.add("Alfonso");
        nicknames.add("Massi");
        nicknames.add("Giamma");

    }


    @Test
    void GeneralTestMethod(){

        int randomID = 0;

        //a ServerToClientEvent throws a new RuntimeException
        Exception exception = assertThrows(RuntimeException.class, () -> {
            controller.update(new LosingByNoActionEvent(nicknames.get(0), "sad message!"));
        });
        System.out.println(exception.getMessage());

        randomID = random.nextInt(69420);

        controller.update(new NewConnectionEvent(randomID, nicknames.get(0)));





    }


    // MARK : Test for Json Generator ======================================================================================


    @Test
    void generateJsonActions() {

        String generatedJson = "";
        List<String> generatedList = new ArrayList<>();
        List<Action> actionList = new ArrayList<>();

        //first test with two elements
        actionList.add(new MoveAction());
        actionList.add(new BuildAction());

        generatedJson = controller.generateJsonActions(actionList);
        System.out.println(generatedJson);

        generatedList = ClientJsonHandler.generateActionsList(generatedJson);
        System.out.println(generatedList);


        //second test with a new element
        actionList.add(new FinishAction());

        generatedJson = controller.generateJsonActions(actionList);
        System.out.println(generatedJson);

        generatedList = ClientJsonHandler.generateActionsList(generatedJson);
        System.out.println(generatedList);


        //third test with two removed elements
        actionList.removeIf(a -> a.getClass().equals(MoveAction.class));
        actionList.removeIf(a -> a.getClass().equals(BuildAction.class));

        generatedJson = controller.generateJsonActions(actionList);
        System.out.println(generatedJson);

        generatedList = ClientJsonHandler.generateActionsList(generatedJson);
        System.out.println(generatedList);


        //fourth test with a new element
        actionList.add(new DestroyAction());

        generatedJson = controller.generateJsonActions(actionList);
        System.out.println(generatedJson);

        generatedList = ClientJsonHandler.generateActionsList(generatedJson);
        System.out.println(generatedList);


    }


    @Test
    void generateJsonCells() {

        String generatedJson = "";
        List<Couple<Integer, Integer>> generatedList = new ArrayList<>();
        List<Cell> cellsList = new ArrayList<>();


        //first test with a three cells list
        cellsList.add(new Cell(1,1));
        cellsList.add(new Cell(0,0));
        cellsList.add(new Cell(1,2));

        generatedJson = controller.generateJsonCells(cellsList);
        System.out.println(generatedJson);

        generatedList = ClientJsonHandler.generateCellsList(generatedJson);
        for(int i = 0; i < generatedList.size(); i++)
            System.out.print(generatedList.get(i).getFirst()+"-"+generatedList.get(i).getSecond()+" ");
        System.out.println();


        //second test with a new cell
        cellsList.add(new Cell(0,1));

        generatedJson = controller.generateJsonCells(cellsList);
        System.out.println(generatedJson);

        generatedList = ClientJsonHandler.generateCellsList(generatedJson);
        for(int i = 0; i < generatedList.size(); i++)
            System.out.print(generatedList.get(i).getFirst()+"-"+generatedList.get(i).getSecond()+" ");
        System.out.println();


        //third test with a removed cell
        cellsList.removeIf(c -> c.getRowPosition() == 0 && c.getColumnPosition() == 0);

        generatedJson = controller.generateJsonCells(cellsList);
        System.out.println(generatedJson);

        generatedList = ClientJsonHandler.generateCellsList(generatedJson);
        for(int i = 0; i < generatedList.size(); i++)
            System.out.print(generatedList.get(i).getFirst()+"-"+generatedList.get(i).getSecond()+" ");
        System.out.println();


        //last test with empty list
        cellsList.clear();

        generatedJson = controller.generateJsonCells(cellsList);
        System.out.println(generatedJson);

        generatedList = ClientJsonHandler.generateCellsList(generatedJson);
        for(int i = 0; i < generatedList.size(); i++)
            System.out.print(generatedList.get(i).getFirst()+"-"+generatedList.get(i).getSecond()+" ");
        System.out.println();


    }


    @Test
    void generateJsonBuildings() {

        String generatedJson = "";
        List<Couple<Integer, Boolean>> generatedList = new ArrayList<>();
        List<Building> buildingList = new ArrayList<>();


        //
        buildingList.add(new Building(1,30));
        buildingList.add(new Building(2,30));

        generatedJson = controller.generateJsonBuildings(buildingList);
        System.out.println(generatedJson);

        generatedList = ClientJsonHandler.generateBuildingsList(generatedJson);
        for(int i = 0; i < generatedList.size(); i++)
            System.out.print(generatedList.get(i).getFirst()+"-"+generatedList.get(i).getSecond()+" ");
        System.out.println();


        //
        buildingList.add(new Building(4, 30));

        generatedJson = controller.generateJsonBuildings(buildingList);
        System.out.println(generatedJson);

        generatedList = ClientJsonHandler.generateBuildingsList(generatedJson);
        for(int i = 0; i < generatedList.size(); i++)
            System.out.print(generatedList.get(i).getFirst()+"-"+generatedList.get(i).getSecond()+" ");
        System.out.println();


        //last test with empty list
        buildingList.clear();

        generatedJson = controller.generateJsonBuildings(buildingList);
        System.out.println(generatedJson);

        generatedList = ClientJsonHandler.generateBuildingsList(generatedJson);
        for(int i = 0; i < generatedList.size(); i++)
            System.out.print(generatedList.get(i).getFirst()+"-"+generatedList.get(i).getSecond()+" ");
        System.out.println();


    }


    @Test
    void generateJsonCards() {

        String generatedJson = "";
        List<Couple<String, String>> generatedList = new ArrayList<>();
        List<Card> cardList = new ArrayList<>();

        generatedJson = controller.generateJsonCards(cardList);
        System.out.println(generatedJson);

        cardList.add(new Card("Apollo", true, "effetto_apollo"));
        cardList.add(new Card("Artemide", true, "effetto_artemide"));

        generatedList = ClientJsonHandler.generateCardsList(generatedJson);
        for(int i = 0; i < generatedList.size(); i++)
            System.out.print(generatedList.get(i).getFirst()+"-"+generatedList.get(i).getSecond()+" ");
        System.out.println();



        generatedJson = controller.generateJsonCards(cardList);
        System.out.println(generatedJson);

        cardList.add(new Card("Atlas", true, "effetto_atlas"));
        cardList.removeIf(c -> c.getName().equals("Apollo"));

        generatedList = ClientJsonHandler.generateCardsList(generatedJson);
        for(int i = 0; i < generatedList.size(); i++)
            System.out.print(generatedList.get(i).getFirst()+"-"+generatedList.get(i).getSecond()+" ");
        System.out.println();



        generatedJson = controller.generateJsonCards(cardList);
        System.out.println(generatedJson);

        generatedList = ClientJsonHandler.generateCardsList(generatedJson);
        for(int i = 0; i < generatedList.size(); i++)
            System.out.print(generatedList.get(i).getFirst()+"-"+generatedList.get(i).getSecond()+" ");
        System.out.println();



        cardList.add(new Card("Zeus", true, "effetto_zeus"));

        generatedJson = controller.generateJsonCards(cardList);
        System.out.println(generatedJson);

        generatedList = ClientJsonHandler.generateCardsList(generatedJson);
        for(int i = 0; i < generatedList.size(); i++)
            System.out.print(generatedList.get(i).getFirst()+"-"+generatedList.get(i).getSecond()+" ");
        System.out.println();

    }


    // ======================================================================================



}