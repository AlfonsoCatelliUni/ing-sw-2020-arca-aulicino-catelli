package it.polimi.ingsw.controller;

import it.polimi.ingsw.events.CTSEvents.ChosenCellToMoveEvent;
import it.polimi.ingsw.events.CTSEvents.NewConnectionEvent;
import it.polimi.ingsw.events.ClientToServerEvent;
import it.polimi.ingsw.events.STCEvents.GivePossibleActionsEvent;
import it.polimi.ingsw.events.STCEvents.LosingByNoActionEvent;
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


    @Test
    void manageEvent(NewConnectionEvent uselessEvent) {

        /* first player connecting */

        ClientToServerEvent event = new NewConnectionEvent(1, "Alfonso");

        controller.receiveEvent(event);



    }



    @Test
    void manageEvent(ChosenCellToMoveEvent uselessEvent) {
    }



}