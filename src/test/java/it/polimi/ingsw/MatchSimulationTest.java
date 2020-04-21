package it.polimi.ingsw;

import static org.junit.Assert.assertTrue;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.events.CTSEvents.NewConnectionEvent;
import it.polimi.ingsw.server.Connection;
import it.polimi.ingsw.view.server.VirtualView;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class MatchSimulationTest  {


    private VirtualView virtualView;

    private Controller controller;

    private List<String> nicknames;

    private Random random;


    @BeforeEach
    void setUp() {

        virtualView = new VirtualView();
        //controller = new Controller();

        random = new Random();

        nicknames = new ArrayList<>();
        nicknames.add("Alfonso");
        nicknames.add("Massi");
        nicknames.add("Giamma");

    }


    @Test
    public void GeneralTest() {

        //int randomID = random.nextInt(69420);
        int randomID = 1;

        if(VirtualView.isValidID(randomID)) {
            VirtualView.newConnection(randomID, new Connection(0, new Socket()));
        }

        virtualView.update(new NewConnectionEvent(randomID, nicknames.get(0)));




    }




}
