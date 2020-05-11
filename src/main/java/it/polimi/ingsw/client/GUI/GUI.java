package it.polimi.ingsw.client.GUI;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.GUI.scenes.LoginScene;
import it.polimi.ingsw.client.GUI.scenes.StartScene;
import it.polimi.ingsw.client.GUI.scenes.TheScene;
import it.polimi.ingsw.events.manager.ServerToClientManager;
import it.polimi.ingsw.view.client.ClientView;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import it.polimi.ingsw.events.STCEvents.*;
import it.polimi.ingsw.events.ServerToClientEvent;

public class GUI extends Application implements Client, ServerToClientManager {



    private final String nicknamePattern = "^[aA-zZ]\\w{5,29}$";

    private String nickname;

    private int rowUsedPawn;

    private int columnUsedPawn;

    private int nextActionRow;

    private int nextActionColumn;

    private ClientView clientView;


    protected Stage stage;






    // ======================================================================================

    @Override
    public void run() {
        launch();
    }


    @Override
    public void start(Stage stage) {

        this.stage = stage;

        this.stage.setTitle("Santorini");

        TheScene next = new StartScene(this, this.stage);
        Scene nextScene = next.getScene();

        this.stage.setScene(nextScene);

        this.stage.show();
    }

    public static void main(String[] args) {
        GUI gui = new GUI();
        gui.run();
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setClientView(ClientView clientView) {
        this.clientView = clientView;
    }

    @Override
    public void receiveEvent(ServerToClientEvent event) {
        event.accept(this);
    }

    @Override
    public void manageEvent(ConnectionEstablishedEvent event) {

        System.out.println("RECEIVED ConnectionEstablished EVENT");

       /* TheScene next = new LoginScene(this, this.stage);
        Scene nextScene = next.getScene();
        stage.setScene(nextScene);*/

        Platform.runLater( () -> {
            TheScene next = new LoginScene(this, this.stage);
            Scene nextScene = next.getScene();
            stage.setScene(nextScene);});

    }

    @Override
    public void manageEvent(FirstConnectedEvent event) {

    }

    @Override
    public void manageEvent(SuccessfullyConnectedEvent event) {

    }

    @Override
    public void manageEvent(NotifyStatusEvent event) {

    }

    @Override
    public void manageEvent(OneClientDisconnectedEvent event) {

    }

    @Override
    public void manageEvent(UnableToEnterWaitingRoomEvent event) {

    }

    @Override
    public void manageEvent(PlainTextEvent event) {

    }

    @Override
    public void manageEvent(ClosedWaitingRoomEvent event) {

    }

    @Override
    public void manageEvent(RoomNotFilled event) {

    }

    @Override
    public void manageEvent(AskInitPawnsEvent event) {

    }

    @Override
    public void manageEvent(AskWhichPawnsUseEvent event) {

    }

    @Override
    public void manageEvent(UnavailableNicknameEvent event) {

    }

    @Override
    public void manageEvent(GivePossibleCardsEvent event) {

    }

    @Override
    public void manageEvent(GivePossibleActionsEvent event) {

    }

    @Override
    public void manageEvent(GivePossibleCellsToMoveEvent event) {

    }

    @Override
    public void manageEvent(GivePossibleCellsToBuildEvent event) {

    }

    @Override
    public void manageEvent(GivePossibleBuildingsEvent event) {

    }

    @Override
    public void manageEvent(GivePossibleCellsToDestroyEvent event) {

    }

    @Override
    public void manageEvent(GivePossibleCellsToForceEvent event) {

    }

    @Override
    public void manageEvent(StartGameEvent event) {

    }

    @Override
    public void manageEvent(DisconnectionClientEvent event) {

    }

    @Override
    public void manageEvent(LosingByNoActionEvent event) {

    }

    @Override
    public void manageEvent(EndGameSTCEvent event) {

    }

}
