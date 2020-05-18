package it.polimi.ingsw.client.GUI;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.FormattedSimpleCell;
import it.polimi.ingsw.client.GUI.FXMLControllers.*;
import it.polimi.ingsw.client.GUI.scenes.*;
import it.polimi.ingsw.events.manager.ServerToClientManager;
import it.polimi.ingsw.view.client.ClientView;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import it.polimi.ingsw.events.STCEvents.*;
import it.polimi.ingsw.events.ServerToClientEvent;

import java.awt.*;
import java.io.IOException;
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

    private FXMLGameController gameSceneController;

    //main stage
    protected Stage stage;

    private Stage waitingRoomStage;



    // MARK : Constructor and Run ======================================================================================

    @Override
    public void run() {
        launch();
    }


    @Override
    public void start(Stage stage) {

        this.stage = stage;

        this.stage.setTitle("Santorini");

        Parent root = null;
        FXMLStartController controller;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader( getClass().getResource("/FXML/SantoriniStartScene.fxml") );
            root = fxmlLoader.load();

            controller = fxmlLoader.getController();
            controller.initStartController(this,stage);

        } catch (IOException e) {
            e.printStackTrace();
        }

        assert root != null;
        Scene scene = new Scene(root);

        this.stage.setScene(scene);
        this.stage.setResizable(false);

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
            Parent root = null;
            FXMLLoginController controller;
            try {
                FXMLLoader fxmlLoader = new FXMLLoader( getClass().getResource("/FXML/LoginScene.fxml") );
                root = fxmlLoader.load();

                controller = fxmlLoader.getController();
                controller.initLoginController(this, event.ID);

            } catch (IOException e) {
                e.printStackTrace();
            }

            assert root != null;
            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.setResizable(false);

            stage.show();
        });

    }


    @Override
    public void manageEvent(FirstConnectedEvent event) {

        System.out.println("RECEIVED FirstConnectedEvent");

        Platform.runLater( () -> {
            Parent root = null;
            FXMLSelectNumberPlayersController controller;
            try {
                FXMLLoader fxmlLoader = new FXMLLoader( getClass().getResource("/FXML/SelectNumberPlayersScene.fxml") );
                root = fxmlLoader.load();

                controller = fxmlLoader.getController();
                controller.initSelectNumberPlayersController(this, stage);

            } catch (IOException e) {
                e.printStackTrace();
            }



            assert root != null;
            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.setResizable(false);

            stage.show();
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
    public void manageEvent(StartGameEvent event) {

        System.out.println("RECEIVED StartGameEvent ");

        Platform.runLater( () -> {

            Parent root = null;

            try {
                FXMLLoader fxmlLoader = new FXMLLoader( getClass().getResource("/FXML/GameScene.fxml") );
                root = fxmlLoader.load();

                gameSceneController = fxmlLoader.getController();
                gameSceneController.initGameController(this, event.info, stage);

            } catch (IOException e) {
                e.printStackTrace();
            }



            assert root != null;
            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.setResizable(false);

            stage.show();
        });

    }


    @Override
    public void manageEvent(ClosedWaitingRoomEvent event) {

        System.out.println("RECEIVED ClosedWaitingRoomEvent ");


        Platform.runLater( () -> {
            lobbyStage.close(event.connectedPlayers);
            VBox waitLayout = new VBox(25);
            waitLayout.setAlignment(Pos.CENTER);
            Label text = new Label("Wait your turn");
            waitLayout.getChildren().add(text);
            Scene nextScene = new Scene(waitLayout, 750,500);
            stage.setScene(nextScene);

        });
    }


    @Override
    public void manageEvent(AskInitPawnsEvent event) {

        Platform.runLater( () -> {
            gameSceneController.chooseInitPawn(event);
        });

    }


    @Override
    public void manageEvent(AskWhichPawnsUseEvent event) {

        System.out.println("RECEIVED AskWhichPawnsUseEvent");

        Platform.runLater(() -> gameSceneController.choosePawnToUse(event));

    }


    @Override
    public void manageEvent(GivePossibleCardsEvent event) {

        System.out.println("RECEIVED GivePossibleCardsEvent ");

        Platform.runLater( () -> {
//            TheScene next = new ChooseCardScene(this, stage, event.cardsName, event.cardsEffect);
//            Scene nextScene = next.getScene();
//
//            stage = new Stage();
//            stage.setMinWidth(750);
//            stage.setMinHeight(500);
//            stage.setScene(nextScene);
//
//            stage.show();
            Parent root = null;
            FXMLChooseCardController controller;
            try {
                FXMLLoader fxmlLoader = new FXMLLoader( getClass().getResource("/FXML/ChooseCardScene.fxml") );
                root = fxmlLoader.load();
                controller = fxmlLoader.getController();

                controller.setController(stage, nickname, clientView, event.cardsName, event.cardsEffect);
                controller.setCards();

            } catch (IOException e) {
                e.printStackTrace();
            }

            assert root != null;
            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.setResizable(false);

            stage.show();
        });

    }


    @Override
    public void manageEvent(GivePossibleActionsEvent event) {

        System.out.println("RECEIVED GivePossibleActionsEvent");

        Platform.runLater(() -> gameSceneController.chooseAction(event));

    }


    @Override
    public void manageEvent(GivePossibleCellsToMoveEvent event) {
        gameSceneController.chooseCellToMove(event, rowUsedPawn, columnUsedPawn);
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

    public void setRowUsedPawn(int rowUsedPawn) {
        this.rowUsedPawn = rowUsedPawn;
    }

    public void setColumnUsedPawn(int columnUsedPawn) {
        this.columnUsedPawn = columnUsedPawn;
    }

    public void setNextActionRow(int nextActionRow) {
        this.nextActionRow = nextActionRow;
    }

    public void setNextActionColumn(int nextActionColumn) {
        this.nextActionColumn = nextActionColumn;
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

    public int getRowUsedPawn() {
        return rowUsedPawn;
    }

    public int getColumnUsedPawn() {
        return columnUsedPawn;
    }

    public int getNextActionRow() {
        return nextActionRow;
    }

    public int getNextActionColumn() {
        return nextActionColumn;
    }


}
