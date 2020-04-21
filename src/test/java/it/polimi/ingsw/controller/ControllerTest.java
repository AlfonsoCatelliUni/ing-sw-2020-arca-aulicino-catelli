package it.polimi.ingsw.controller;

import it.polimi.ingsw.events.CTSEvents.ChosenCellToMoveEvent;
import it.polimi.ingsw.events.CTSEvents.NewConnectionEvent;
import it.polimi.ingsw.events.ClientToServerEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w3c.dom.events.Event;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {

    Controller controller;

    @BeforeEach
    void setUp() {

        controller = new Controller();

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