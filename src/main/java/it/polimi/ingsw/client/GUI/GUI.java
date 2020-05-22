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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
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

    private GameScene gameScene;

    /**
     * this is the Main Stage, the one where the game board i showed on
     */
    protected Stage stage;

    /**
     * this is the dialog to show in another stage plain text and important informations during game
     */
    protected Dialog dialog = new Dialog();

    /**
     * this is the Waiting Room Stage, the one where is showed which players are in the lobby
     */
    private Stage waitingRoomStage;


    // Controllers

    private FXMLLobbyController lobbyController;

    private FXMLLoginController loginController;

    private FXMLSelectNumberPlayersController selectNumberPlayersController;

    private FXMLGameController gameSceneController;

    private FXMLChooseCardController chooseCardController;

    private FXMLVictoryController endGameController;



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
            try {
                FXMLLoader fxmlLoader = new FXMLLoader( getClass().getResource("/FXML/LoginScene.fxml") );
                root = fxmlLoader.load();

                loginController = fxmlLoader.getController();
                loginController.initLoginController(this, stage, event.ID);

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

            Stage selectNumberPlayersStage = new Stage();
            selectNumberPlayersStage.setTitle("Santorini");
            Parent root = null;
            try {
                FXMLLoader fxmlLoader = new FXMLLoader( getClass().getResource("/FXML/SelectNumberPlayersScene.fxml") );
                root = fxmlLoader.load();

                selectNumberPlayersController = fxmlLoader.getController();
                selectNumberPlayersController.initSelectNumberPlayersController(this, selectNumberPlayersStage);

            } catch (IOException e) {
                e.printStackTrace();
            }


            assert root != null;
            Scene scene = new Scene(root);

            selectNumberPlayersStage.setScene(scene);
            selectNumberPlayersStage.setResizable(false);

            selectNumberPlayersStage.show();
        });

    }


    @Override
    public void manageEvent(SuccessfullyConnectedEvent event) {

        System.out.println("RECEIVED SuccessfullyConnectedEvent");

        Platform.runLater( () -> {
            Parent root = null;
            try {
                FXMLLoader fxmlLoader = new FXMLLoader( getClass().getResource("/FXML/LobbyScene.fxml") );
                root = fxmlLoader.load();
                lobbyController = fxmlLoader.getController();

                lobbyController.initController(stage, event.connectedPlayers);

            } catch (IOException e) {
                e.printStackTrace();
            }

            assert root != null;
            Scene scene = new Scene(root);

            this.stage.setScene(scene);
            this.stage.setResizable(false);

            this.stage.show();

        });

    }


    @Override
    public void manageEvent(UnavailableNicknameEvent event) {
        System.out.println("RECEIVED UnavailableNicknameEvent");

        Platform.runLater( () -> {
            dialog.display("This Nickname is already used, choose another one");

            Parent root = null;
            try {
                FXMLLoader fxmlLoader = new FXMLLoader( getClass().getResource("/FXML/LoginScene.fxml") );
                root = fxmlLoader.load();

                loginController = fxmlLoader.getController();
                loginController.initLoginController(this, stage, event.ID);

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
    public void manageEvent(DisconnectionClientEvent event) {

        System.out.println("RECEIVED DisconnectionClientEvent ");

        Platform.runLater( () -> {
            // TODO: mettere variabile lo stage del select number
            stage.close();

            dialog.displayExit("YOU HAVE BEEN DISCONNECTED");
        });



    }


    @Override
    public void manageEvent(OneClientDisconnectedEvent event) {

        System.out.println(" RECEIVED OneClientDisconnectedEvent ");

        Platform.runLater( () -> {

            dialog.display(event.disconnected + "is disconnected");

            if (lobbyController != null) {
                lobbyController.fillNicknames(event.connectedPlayers);
            }

        });


    }


    @Override
    public void manageEvent(UnableToEnterWaitingRoomEvent event) {

        System.out.println(" RECEIVED UnableToEnterWaitingRoomEvent ");

        Platform.runLater( () -> {

            stage.close();

            dialog.displayExit("THE WAITING ROOM IS FILLED\n YOU HAVE BEEN DISCONNECTED ");

        });



    }


    @Override
    public void manageEvent(RoomNotFilledEvent event) {

        System.out.println(" RECEIVED RoomNotFilledEvent ");

        Platform.runLater( () -> {

            stage.close();

            dialog.displayExit(event.message + "\nYOU HAVE BEEN DISCONNECTED" );

        });


    }


    @Override
    public void manageEvent(PlainTextEvent event) {

        System.out.println("RECEIVED PlainTextEvent ");

        Platform.runLater( () -> {

            dialog.display(event.message);

        });

    }


    // MARK : Game Event Managers ======================================================================================


    @Override
    public void manageEvent(NotifyStatusEvent event) {
        System.out.println("RECEIVED NotifyStatus");

        Platform.runLater(() -> {
            gameSceneController.updateCells(event.status);
        });
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
            stage.setMinHeight(900);
            stage.setMinWidth(1110);
            stage.setResizable(true);

            stage.show();
        });

    }


    @Override
    public void manageEvent(ClosedWaitingRoomEvent event) {

        System.out.println("RECEIVED ClosedWaitingRoomEvent ");

        Platform.runLater( () -> {
            lobbyController.close(event.connectedPlayers);
//            VBox waitLayout = new VBox(25);
//            waitLayout.setAlignment(Pos.CENTER);
//            Label text = new Label("Wait your turn");
//            waitLayout.getChildren().add(text);
//
//            Scene nextScene = new Scene(waitLayout, 750,500);
//
//            stage.setScene(nextScene);
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

            Parent root = null;
            try {
                FXMLLoader fxmlLoader = new FXMLLoader( getClass().getResource("/FXML/ChooseCardScene.fxml") );
                root = fxmlLoader.load();
                chooseCardController = fxmlLoader.getController();

                chooseCardController.setController(stage, nickname, clientView, event.cardsName, event.cardsEffect);
                chooseCardController.setCards();

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

        Platform.runLater( () -> gameSceneController.chooseAction(event) );

    }


    @Override
    public void manageEvent(GivePossibleCellsToMoveEvent event) {

        System.out.println("RECEIVED GivePossibleCellsToMoveEvent");

        Platform.runLater( () -> gameSceneController.chooseCellToMove(event, rowUsedPawn, columnUsedPawn) );
    }


    @Override
    public void manageEvent(GivePossibleCellsToBuildEvent event) {

        System.out.println("RECEIVED GivePossibleCellsToBuildEvent");

        Platform.runLater( () -> gameSceneController.chooseCellToBuild(event, rowUsedPawn, columnUsedPawn) );

    }


    @Override
    public void manageEvent(GivePossibleBuildingsEvent event) {

        System.out.println("RECEIVED GivePossibleBuildingsEvent");

        Platform.runLater( () -> gameSceneController.chooseLevelBuilding(event));
    }


    @Override
    public void manageEvent(GivePossibleCellsToDestroyEvent event) {

        System.out.println("RECEIVED GivePossibleCellsToDestroyEvent");

        Platform.runLater( () -> gameSceneController.chooseCellToDestroy(event, rowUsedPawn, columnUsedPawn));

    }


    @Override
    public void manageEvent(GivePossibleCellsToForceEvent event) {

        System.out.println("RECEIVED GivePossibleCellsToForceEvent");

        Platform.runLater( () -> gameSceneController.chooseCellToForce(event, rowUsedPawn, columnUsedPawn));


    }


    @Override
    public void manageEvent(LosingByNoActionEvent event) {

        System.out.println("RECEIVED LosingByNoActionEvent");

        Platform.runLater(() -> {

            //this player lost the game
            if(event.nickname.equals(nickname)) {

                Stage losingStage = new Stage();
                losingStage.setTitle("Santorini");

                Parent root = null;
                FXMLLoseGameController controller;
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader( getClass().getResource("/FXML/LoseEndGameScene.fxml") );
                    root = fxmlLoader.load();

                    controller = fxmlLoader.getController();
                    controller.initLoseController(this, losingStage, stage);
                    controller.showLosingByNoAction();

                } catch (IOException e) {
                    e.printStackTrace();
                }

                assert root != null;
                Scene scene = new Scene(root);

                losingStage.setScene(scene);
                losingStage.setResizable(false);
                losingStage.initModality(Modality.APPLICATION_MODAL);

                losingStage.show();

            }
            //tell this player that an opponent player lost the game
            else {

                gameSceneController.deleteOpponentPlayer(event.nickname);

                //TODO : rimuovere i pawns dalla board

            }

        });



    }


    @Override
    public void manageEvent(EndGameSTCEvent event) {

        System.out.println("RECEIVED EndGameSTCEvent");

        if(event.winner.equals(nickname)) {

            Platform.runLater(() -> {
                Parent root = null;
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/VictoryScene.fxml"));
                    root = fxmlLoader.load();

                    endGameController = fxmlLoader.getController();
                    endGameController.initController(stage, event.winner);

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
        else {

            Stage losingStage = new Stage();
            losingStage.setTitle("Santorini");

            Parent root = null;
            FXMLLoseGameController controller;
            try {
                FXMLLoader fxmlLoader = new FXMLLoader( getClass().getResource("/FXML/LoseEndGameScene.fxml") );
                root = fxmlLoader.load();

                controller = fxmlLoader.getController();
                controller.initLoseController(this, losingStage, stage);
                controller.showLosingEndGame(event.winner);

            } catch (IOException e) {
                e.printStackTrace();
            }

            assert root != null;
            Scene scene = new Scene(root);

            losingStage.setScene(scene);
            losingStage.setResizable(false);

            stage.close();

            losingStage.show();

        }


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

    public Dialog getDialog() {
        return dialog;
    }
}
