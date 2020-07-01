package it.polimi.ingsw.client;

import it.polimi.ingsw.client.CLI.CLI;
import it.polimi.ingsw.client.GUI.GUI;
import it.polimi.ingsw.server.Server;


public class App {


    /* HOW TO RUN A MAIN WITH A PARAMETER
    * if you want to run this main in IntelliJ with a parameter
    * you have to drop the window between the Build icon (hammer)
    * and the Run icon (play), the goes to "Edit Configuration"
    * controlling that is selected "ClientApp" in the left section.
    * Once selected the right Application to run look in the main section
    * of the window and search for "Program arguments:" in "Configuration"
    * tab, in this line you have to type the parameter for the run
    * ...........
    * in this case you have only to insert "cli" or "run", without "" */


    /**
     * the main function of the client part
     * this method needs only one parameter that allows the user to decide between
     * Command Line Interface and Graphic User Interface.
     * to use the CLI the user need to insert "cli"
     * to use the GUI the user need to insert "gui"
     * @param args the arguments
     */
    public static void main(String[] args) {

        Client client = null;
        Server server = null;

        String mode = args[0];

        switch (mode) {
            case "cli":
                client = new CLI();
                client.run();
                break;
            case "gui" :
                client = new GUI();
                client.run();
                break;
            case "server" :
                server = new Server();
                server.run();
                break;
            default:
                System.err.println("Invalid Running Mode!");
                System.exit(0);
                break;
        }

    }




}
