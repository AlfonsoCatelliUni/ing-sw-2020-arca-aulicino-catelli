package it.polimi.ingsw.client.GUI;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.GUI.scenes.*;
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

    private LobbyStage lobbyScene;

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


    public ClientView getClientView() {
        return clientView;
    }


    public String getNickname() {
        return nickname;
    }

    @Override
    public void receiveEvent(ServerToClientEvent event) {
        event.accept(this);
    }

    @Override
    public void manageEvent(ConnectionEstablishedEvent event) {

        System.out.println("RECEIVED ConnectionEstablishedEvent");


        Platform.runLater( () -> {
            TheScene next = new LoginScene(this, event.ID);
            Scene nextScene = next.getScene();
            stage.setScene(nextScene);
        });





    }

    @Override
    public void manageEvent(FirstConnectedEvent event) {

        System.out.println("RECEIVED FirstConnectedEvent");

        Platform.runLater( () -> {
            TheScene next = new SelectNumberPlayersScene(this, stage);
            Scene nextScene = next.getScene();
            stage.setScene(nextScene);
        });

    }

    @Override
    public void manageEvent(SuccessfullyConnectedEvent event) {

        System.out.println("RECEIVED SuccessfullyConnectedEvent");


        Platform.runLater( () -> {
        lobbyScene = new LobbyStage(event.connectedPlayers);
        lobbyScene.display();

        });


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

        System.out.println("RECEIVED ClosedWaitingRoomEvent ");

        // need to fix
        lobbyScene.close();

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

        System.out.println("RECEIVED UnavailableNicknameEvent");

        Platform.runLater( () -> {
            Dialog.display("This Nickname is already used, choose another one");
        });


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
