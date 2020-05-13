package it.polimi.ingsw.client.GUI;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.FormattedSimpleCell;
import it.polimi.ingsw.client.GUI.scenes.*;
import it.polimi.ingsw.events.manager.ServerToClientManager;
import it.polimi.ingsw.view.client.ClientView;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import it.polimi.ingsw.events.STCEvents.*;
import it.polimi.ingsw.events.ServerToClientEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GUI extends Application implements Client, ServerToClientManager {


    private final String nicknamePattern = "^[aA-zZ]\\w{5,29}$";

    private String nickname;

    private int rowUsedPawn;

    private int columnUsedPawn;

    private int nextActionRow;

    private int nextActionColumn;

    private ClientView clientView;

    private LobbyStage lobbyStage;

    private GameScene gameScene;

    protected Stage stage;

    private FXMLController fxmlController;


    // MARK : Constructor and Run ======================================================================================

    @Override
    public void run() {
        launch();
    }


    @Override
    public void start(Stage stage) {

        this.stage = stage;
        this.fxmlController = new FXMLController();

        this.stage.setTitle("Santorini");

//        this.stage.setMinHeight(900);
//        this.stage.setMinWidth(1110);

        TheScene next = new StartScene(this, this.stage);
        Scene nextScene = next.getScene();

        this.stage.setScene(nextScene);

        this.stage.show();

    }


    public static void main(String[] args) {
        GUI gui = new GUI();
        gui.run();
    }


    // MARK : Network Event Managers ======================================================================================


    @Override
    public void receiveEvent(ServerToClientEvent event) {
        event.accept(this);
    }


    @Override
    public void manageEvent(ConnectionEstablishedEvent event) {

        System.out.println("RECEIVED ConnectionEstablishedEvent");


        Platform.runLater( () -> {
            TheScene next = new LoginScene(this, stage, event.ID);
            Scene nextScene = next.getScene();

            stage.setScene(nextScene);
            this.stage.setResizable(true);
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
            lobbyStage = new LobbyStage(event.connectedPlayers);
            lobbyStage.display();
            VBox waitLayout = new VBox(25);
            waitLayout.setAlignment(Pos.CENTER);
            Label text = new Label("Wait until the lobby is full");
            waitLayout.getChildren().add(text);
            Scene nextScene = new Scene(waitLayout, 750,500);
            stage.setScene(nextScene);

        });

    }


    @Override
    public void manageEvent(UnavailableNicknameEvent event) {
        System.out.println("RECEIVED UnavailableNicknameEvent");

        Platform.runLater( () -> {
            Dialog.display("This Nickname is already used, choose another one");
        });
    }


    @Override
    public void manageEvent(DisconnectionClientEvent event) {

    }


    @Override
    public void manageEvent(OneClientDisconnectedEvent event) {

    }


    @Override
    public void manageEvent(UnableToEnterWaitingRoomEvent event) {

    }


    @Override
    public void manageEvent(RoomNotFilled event) {

    }


    @Override
    public void manageEvent(PlainTextEvent event) {

    }


    // MARK : Game Event Managers ======================================================================================


    @Override
    public void manageEvent(NotifyStatusEvent event) {

    }


    @Override
    public void manageEvent(ClosedWaitingRoomEvent event) {
        System.out.println("RECEIVED ClosedWaitingRoomEvent ");


        Platform.runLater( () -> {
            lobbyStage.close(event.connectedPlayers);
        });
    }


    @Override
    public void manageEvent(AskInitPawnsEvent event) {

    }


    @Override
    public void manageEvent(AskWhichPawnsUseEvent event) {

    }


    @Override
    public void manageEvent(GivePossibleCardsEvent event) {

        System.out.println("RECEIVED GivePossibleCardsEvent ");

        Platform.runLater( () -> {
            TheScene next = new ChooseCardScene(this, stage, event);
            Scene nextScene = next.getScene();
            stage = new Stage();
            stage.setScene(nextScene);
            stage.show();
        });

    }


    @Override
    public void manageEvent(GivePossibleActionsEvent event) {

    }


    @Override
    public void manageEvent(GivePossibleCellsToMoveEvent event) {
        gameScene.manageEvent(event);
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
    public void manageEvent(LosingByNoActionEvent event) {

    }


    @Override
    public void manageEvent(EndGameSTCEvent event) {

    }


    // MARK : Support Methods ======================================================================================


    private List<FormattedSimpleCell> generateSimpleCellList(List<Point> pointList) {

        List<FormattedSimpleCell> simpleCellList = new ArrayList<>();

        for (Point p : pointList ) {
            simpleCellList.add( new FormattedSimpleCell(p.x, p.y) );
        }

        return simpleCellList;
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
        return this.nickname;
    }

    public LobbyStage getLobbyStage() {
        return lobbyStage;
    }



}
