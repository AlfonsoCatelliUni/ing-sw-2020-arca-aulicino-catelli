package it.polimi.ingsw.client.GUI.FXMLControllers;

import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.sun.javafx.scene.control.IntegerField;
import it.polimi.ingsw.client.ClientJsonHandler;
import it.polimi.ingsw.client.FormattedPlayerInfo;
import it.polimi.ingsw.client.FormattedSimpleCell;
import it.polimi.ingsw.client.GUI.GUI;
import it.polimi.ingsw.events.CTSEvents.*;
import it.polimi.ingsw.events.STCEvents.*;
import it.polimi.ingsw.view.client.ClientView;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.image.Image;

public class FXMLGameController {



    private GUI gui;

    private Stage stage;

    private List<Pane> cellsList;

    private List<Label> playersNicknames;

    private List<Label> playersColors;

    private List<Label> playersEffects;

    private List<Label> actionsLabel;

    private List<Pane> actionsPane;

    // 0 ROW ==================================================================================
    @FXML
    private Pane cell0;
    @FXML
    private Pane cell1;
    @FXML
    private Pane cell2;
    @FXML
    private Pane cell3;
    @FXML
    private Pane cell4;
    // 1 ROW ==================================================================================
    @FXML
    private Pane cell5;
    @FXML
    private Pane cell6;
    @FXML
    private Pane cell7;
    @FXML
    private Pane cell8;
    @FXML
    private Pane cell9;
    // 2 ROW ==================================================================================
    @FXML
    private Pane cell10;
    @FXML
    private Pane cell11;
    @FXML
    private Pane cell12;
    @FXML
    private Pane cell13;
    @FXML
    private Pane cell14;
    // 3 ROW ==================================================================================
    @FXML
    private Pane cell15;
    @FXML
    private Pane cell16;
    @FXML
    private Pane cell17;
    @FXML
    private Pane cell18;
    @FXML
    private Pane cell19;
    // 4 ROW ==================================================================================
    @FXML
    private Pane cell20;
    @FXML
    private Pane cell21;
    @FXML
    private Pane cell22;
    @FXML
    private Pane cell23;
    @FXML
    private Pane cell24;

    @FXML
    private HBox ControlsBoxSection;

    @FXML
    private ImageView gameBoardImage;

    @FXML
    private GridPane gameBoardGrid;

    @FXML
    private GridPane infoGrid;


    private ClientView clientView;




    @FXML
    private Label player1_nickname;

    @FXML
    private Label player1_color;

    @FXML
    private Label player1_effect;

    @FXML
    private Label player2_nickname;

    @FXML
    private Label player2_color;

    @FXML
    private Label player2_effect;

    @FXML
    private Label player3_nickname;

    @FXML
    private Label player3_color;

    @FXML
    private Label player3_effect;

    
    @FXML
    private Pane titlePane;
    
    @FXML
    private Label titleLabel;

    @FXML
    private Pane action1Pane;
    
    @FXML
    private Label action1Label;
    
    @FXML
    private Pane action2Pane;
    
    @FXML
    private Label action2Label;

    @FXML
    private Pane action3Pane;

    @FXML
    private Label action3Label;

    @FXML
    private Pane action4Pane;

    @FXML
    private Label action4Label;

    

    @FXML
    private URL location;

    @FXML
    private ResourceBundle resources;


    private FormattedSimpleCell maleCellSelected;



    //MARK : Constructor =================================================================================


    public FXMLGameController() {
    }


    //MARK : Initialization Methods =================================================================================


    public void initGameController( GUI gui, String info, Stage stage ) {

        this.gui = gui;
        this.clientView = gui.getClientView();
        this.stage = stage;

        List<FormattedPlayerInfo> infoPlayers = ClientJsonHandler.generatePlayersList(info);


        for (int i = 0 ; i < infoPlayers.size(); i++){


            playersNicknames.get(i).setText(infoPlayers.get(i).getNickname());
            playersColors.get(i).setText(infoPlayers.get(i).getColor());
            playersEffects.get(i).setText(infoPlayers.get(i).getCard().getSecond());

            playersNicknames.get(i).setVisible(true);
            playersColors.get(i).setVisible(true);
            playersEffects.get(i).setVisible(true);

        }

    }


    @FXML
    private void initialize() {

        this.cellsList = new ArrayList<>();
        this.maleCellSelected = null;

        initializeCells();
        initializeInfoGrid();
        initializeActionsGrid();


        Image imageGrid = new Image(getClass().getResourceAsStream("/Graphics/backgroundPanel.png"));

        Background backgroundGrid = new Background(
                new BackgroundImage(imageGrid, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                        new BackgroundSize(infoGrid.getWidth(), infoGrid.getHeight(),
                                true, true, true, true)));

        infoGrid.setBackground(backgroundGrid);



    }


    private void initializeCells() {

        cellsList.add(cell0);
        cellsList.add(cell1);
        cellsList.add(cell2);
        cellsList.add(cell3);
        cellsList.add(cell4);
        cellsList.add(cell5);
        cellsList.add(cell6);
        cellsList.add(cell7);
        cellsList.add(cell8);
        cellsList.add(cell9);
        cellsList.add(cell10);
        cellsList.add(cell11);
        cellsList.add(cell12);
        cellsList.add(cell13);
        cellsList.add(cell14);
        cellsList.add(cell15);
        cellsList.add(cell16);
        cellsList.add(cell17);
        cellsList.add(cell18);
        cellsList.add(cell19);
        cellsList.add(cell20);
        cellsList.add(cell21);
        cellsList.add(cell22);
        cellsList.add(cell23);
        cellsList.add(cell24);

        setVisibilityAllCells(true);

        //at each cell in cellsList assign a FormattedSimpleCell
        int index = 0;
        for(int row = 0; row < 5; row++) {
            for(int column = 0; column < 5; column++) {

                index = (row * 5) + column;
                cellsList.get(index).setUserData( new FormattedSimpleCell(row, column) );
            }
        }


        //moved in method enableCell
//        //at each cell in cellsList assign a setOnMouseClicked action
//        for (Pane cell : cellsList) {
//
//            //highlight the border
//            cell.setOnMouseEntered(event -> {
//                Pane selectedCell = (Pane) event.getSource();
//                selectedCell.setStyle("-fx-opacity: 1; -fx-border-color: aquamarine; -fx-border-width: 5");
//            });
//
//            //clear the border
//            cell.setOnMouseExited(event -> {
//                Pane selectedCell = (Pane) event.getSource();
//                selectedCell.setStyle("-fx-opacity: 0");
//            });
//
//            //selected cell
//            cell.setOnMouseClicked(event -> {
//                Pane selectedCell = (Pane) event.getSource();
//                cellSelectionHandler(selectedCell);
//            });
//        }

    }


    private void initializeInfoGrid(){

        this.playersNicknames = new ArrayList<>();
        this.playersColors = new ArrayList<>();
        this.playersEffects = new ArrayList<>();

        playersNicknames.add(player1_nickname);
        playersNicknames.add(player2_nickname);
        playersNicknames.add(player3_nickname);

        playersColors.add(player1_color);
        playersColors.add(player2_color);
        playersColors.add(player3_color);

        playersEffects.add(player1_effect);
        playersEffects.add(player2_effect);
        playersEffects.add(player3_effect);

//        for (Label label : playersNicknames){
//            label.setFont(Font.loadFont(
//                    getClass().getResource("/Font/DisneyHeroic.ttf").toExternalForm(),
//                    20
//            ));
//        }
//
//        for (Label label : playersColors){
//            label.setFont(Font.loadFont(
//                    getClass().getResource("/Font/DisneyHeroic.ttf").toExternalForm(),
//                    20
//            ));
//        }
//
//        for (Label label : playersEffects){
//            label.setFont(Font.loadFont(
//                    getClass().getResource("/Font/DisneyHeroic.ttf").toExternalForm(),
//                    20
//            ));
//    }

    }


    private void initializeActionsGrid(){

        this.actionsLabel = new ArrayList<>();
        this.actionsPane = new ArrayList<>();


        actionsLabel.add(action1Label);
        actionsLabel.add(action2Label);
        actionsLabel.add(action3Label);
        actionsLabel.add(action4Label);


        actionsPane.add(action1Pane);
        actionsPane.add(action2Pane);
        actionsPane.add(action3Pane);
        actionsPane.add(action4Pane);

        titleLabel.setVisible(true);

        for (Pane pane : actionsPane) {

            //highlight the border
            pane.setOnMouseEntered(event -> {
                Pane actionPane = (Pane) event.getSource();
                actionPane.setStyle("-fx-opacity: 1; -fx-border-color: aquamarine; -fx-border-width: 5");
            });

            //clear the border
            pane.setOnMouseExited(event -> {
                Pane actionPane = (Pane) event.getSource();
                actionPane.setStyle("-fx-opacity: 0");
            });

        }



    }


    //MARK : Main Methods =================================================================================


    public void chooseInitPawn(AskInitPawnsEvent event) {

        titleLabel.setText("SELECT THE CELL FOR THE MALE PAWN");

        List<Point> freeCells = new ArrayList<>();
        for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 5; j++) {
                Point np = new Point(i, j);
                if(!event.info.contains(np)) {
                    freeCells.add(np);
                }
            }
        }

        List<FormattedSimpleCell> cells = FormattedSimpleCell.generateFromPointList(freeCells);

        List<Pane> visibleCells = showAvailableCells(cells);

        enableCells(visibleCells);



        for(Pane cell : visibleCells) {

            cell.setOnMouseClicked(e -> {

                Pane selectedCell = (Pane) e.getSource();


                //andrà sostituito con l'immagine del pawn
                selectedCell.setStyle("-fx-opacity: 1; -fx-border-color: red; -fx-border-width: 5");
                disableCell(selectedCell);


                FormattedSimpleCell info = (FormattedSimpleCell) selectedCell.getUserData();

                titleLabel.setText("SELECT THE CELL FOR THE FEMALE PAWN");

                if(maleCellSelected == null) {
                    maleCellSelected = info;
                }
                else {
                    titleLabel.setText("WAIT UNTIL YOUR TURN");
                    setVisibilityAllCells(false);
                    clientView.sendCTSEvent( new ChosenInitialPawnCellEvent(event.nickname, maleCellSelected.getRow(), maleCellSelected.getColumn(), info.getRow(), info.getColumn()) );
                    disableCells(visibleCells);

                }

            });

        }

    }


    public void chooseCellToMove(GivePossibleCellsToMoveEvent event, int pawnRow, int pawnColumn) {

        titleLabel.setText("CHOOSE CELL TO MOVE");

        checkValidity(event.isValid);

        List<FormattedSimpleCell> cells = FormattedSimpleCell.generateFromPointList(event.cellsAvailableToMove);

        List<Pane> visibleCells = showAvailableCells(cells);
        enableCells(visibleCells);

        for(Pane cell : visibleCells) {

            cell.setOnMouseClicked(e -> {

                disableCells(visibleCells);

                Pane selectedCell = (Pane) e.getSource();
                FormattedSimpleCell info = (FormattedSimpleCell) selectedCell.getUserData();

                gui.setNextActionRow(info.getRow());
                gui.setNextActionColumn(info.getColumn());

                titleLabel.setText("");
                clientView.sendCTSEvent(new ChosenCellToMoveEvent(event.receiverNickname, pawnRow, pawnColumn, info.getRow(), info.getColumn()));

                //save the new position of the pawn
                gui.setRowUsedPawn(info.getRow());
                gui.setColumnUsedPawn(info.getColumn());
            });

        }

    }


    public void chooseCellToBuild(GivePossibleCellsToBuildEvent event, int pawnRow, int pawnColumn){
        titleLabel.setText("CHOOSE CELL TO BUILD");

        checkValidity(event.isValid);

        List<FormattedSimpleCell> cells = FormattedSimpleCell.generateFromPointList(event.cellsAvailableToBuild);

        List<Pane> visibleCells = showAvailableCells(cells);
        enableCells(visibleCells);

        for(Pane cell : visibleCells) {

            cell.setOnMouseClicked(e -> {

                disableCells(visibleCells);

                Pane selectedCell = (Pane) e.getSource();
                FormattedSimpleCell info = (FormattedSimpleCell) selectedCell.getUserData();

                titleLabel.setText("");
                clientView.sendCTSEvent(new ChosenCellToBuildEvent(event.receiverNickname, pawnRow, pawnColumn, info.getRow(), info.getColumn()));

                gui.setNextActionRow(info.getRow());
                gui.setNextActionColumn(info.getColumn());



            });

        }



    }


    public void chooseCellToDestroy(GivePossibleCellsToDestroyEvent event, int pawnRow, int pawnColumn) {

        titleLabel.setText("CHOOSE CELL TO DESTROY");

        checkValidity(event.isValid);

        List<FormattedSimpleCell> cells = FormattedSimpleCell.generateFromPointList(event.cells);

        List<Pane> visibleCells = showAvailableCells(cells);
        enableCells(visibleCells);

        for(Pane cell : visibleCells) {

            cell.setOnMouseClicked(e -> {

                disableCells(visibleCells);

                Pane selectedCell = (Pane) e.getSource();
                FormattedSimpleCell info = (FormattedSimpleCell) selectedCell.getUserData();

                titleLabel.setText("");
                clientView.sendCTSEvent(new ChosenCellToDestroyEvent(event.nickname, pawnRow, pawnColumn, info.getRow(), info.getColumn()));

            });

        }
    }


    public void chooseCellToForce(GivePossibleCellsToForceEvent event, int pawnRow, int pawnColumn) {

        titleLabel.setText("CHOOSE CELL TO FORCE");

        checkValidity(event.isValid);

        List<FormattedSimpleCell> cells = FormattedSimpleCell.generateFromPointList(event.cells);

        List<Pane> visibleCells = showAvailableCells(cells);
        enableCells(visibleCells);

        for(Pane cell : visibleCells) {

            cell.setOnMouseClicked(e -> {

                disableCells(visibleCells);

                Pane selectedCell = (Pane) e.getSource();
                FormattedSimpleCell info = (FormattedSimpleCell) selectedCell.getUserData();

                titleLabel.setText("");
                clientView.sendCTSEvent(new ChosenCellToForceEvent(event.nickname, pawnRow, pawnColumn, info.getRow(), info.getColumn()));


                //save the new position of the pawn
                gui.setRowUsedPawn(info.getRow());
                gui.setColumnUsedPawn(info.getColumn());
            });

        }

    }


    public void choosePawnToUse(AskWhichPawnsUseEvent event) {

        titleLabel.setText("CHOOSE PAWN TO USE");

        List<FormattedSimpleCell> cells = new ArrayList<>();

        List<Point> points = event.info;

        checkValidity(event.isValid);

        for(Point point : points) {
            cells.add(new FormattedSimpleCell(point.x ,point.y));
        }

        List<Pane> visibleCells = showAvailableCells(cells);
        enableCells(visibleCells);

        for(Pane cell : visibleCells) {

            cell.setOnMouseClicked(e -> {
                //first i disable cells
                disableCells(visibleCells);

                Pane selectedCell = (Pane) e.getSource();
                FormattedSimpleCell info = (FormattedSimpleCell) selectedCell.getUserData();
                clientView.sendCTSEvent((new ChosenPawnToUseEvent(event.nickname, info.getRow(), info.getColumn())));
                gui.setRowUsedPawn(info.getRow());
                gui.setColumnUsedPawn(info.getColumn());

            });

        }

    }
    

    public void chooseAction(GivePossibleActionsEvent event) {

        checkValidity(event.isValid);


        titleLabel.setText("CHOOSE YOUR ACTION");

        for (int i = 0; i < event.actions.size(); i++) {

            actionsLabel.get(i).setText(event.actions.get(i));
            actionsLabel.get(i).setUserData(event.actions.get(i));
            actionsLabel.get(i).setVisible(true);
            actionsPane.get(i).setVisible(true);
            enableLabel(actionsLabel.get(i));


            actionsLabel.get(i).setOnMouseClicked(mouseEvent -> {

                Label labelSelected = (Label) mouseEvent.getSource();
                String actionSelected = (String) labelSelected.getUserData();

                switch (actionSelected) {

                    case "Move":
                        clientView.sendCTSEvent(new ChosenMoveActionEvent(event.receiverNickname, "Move", gui.getRowUsedPawn(), gui.getColumnUsedPawn()));
                        clearMouseClick(actionsPane);
                        disableLabels(actionsLabel);
                        break;

                    case "Build":
                        clientView.sendCTSEvent(new ChosenBuildActionEvent(event.receiverNickname, "Build", gui.getRowUsedPawn(), gui.getColumnUsedPawn()));
                        clearMouseClick(actionsPane);
                        disableLabels(actionsLabel);
                        break;

                    case "Force":
                        clientView.sendCTSEvent(new ChosenForceActionEvent(event.receiverNickname, "Force", gui.getRowUsedPawn(), gui.getColumnUsedPawn()));
                        clearMouseClick(actionsPane);
                        disableLabels(actionsLabel);
                        break;

                    case "Destroy":
                        clientView.sendCTSEvent(new ChosenDestroyActionEvent(event.receiverNickname, "Destroy", gui.getRowUsedPawn(), gui.getColumnUsedPawn()));
                        clearMouseClick(actionsPane);
                        disableLabels(actionsLabel);
                        break;

                    case "End turn":
                        clientView.sendCTSEvent(new ChosenFinishActionEvent(event.receiverNickname, "End turn"));
                        clearMouseClick(actionsPane);
                        disableLabels(actionsLabel);
                        titleLabel.setText("-- WAIT UNTIL YOUR NEXT TURN --");
                        break;

                    default:
                        throw new RuntimeException("Error while selecting the next action!");
                }

            });
        }

    }


    public void chooseLevelBuilding(GivePossibleBuildingsEvent event){

        checkValidity(event.isValid);

        List<Integer> buildingsLevel = event.buildings;
        int selectedLevel = 0;

        if(buildingsLevel.size() > 1) {

            titleLabel.setText("Choose the building to build on the selected cell");

            for (int i = 0; i<buildingsLevel.size(); i++){
                actionsLabel.get(i).setText(event.buildings.get(i).toString());
                actionsLabel.get(i).setUserData(event.buildings.get(i));
                actionsLabel.get(i).setVisible(true);
                actionsPane.get(i).setVisible(true);
                enableLabel(actionsLabel.get(i));

                actionsLabel.get(i).setOnMouseClicked(e -> {
                    //first i disable labels
                    disableLabels(actionsLabel);

                    Label labelSelected = (Label) e.getSource();
                    Integer chosenLevel = (Integer) labelSelected.getUserData();

                    clientView.sendCTSEvent(new ChosenBuildingEvent(event.receiverNickname, chosenLevel, gui.getRowUsedPawn(), gui.getColumnUsedPawn(), gui.getNextActionRow(), gui.getNextActionColumn()));
                });
            }

        } else {
            // if the size is 1
            clientView.sendCTSEvent(new ChosenBuildingEvent(event.receiverNickname, buildingsLevel.get(selectedLevel), gui.getRowUsedPawn(), gui.getColumnUsedPawn(), gui.getNextActionRow(), gui.getNextActionColumn()));
        }

    }


    private void cellSelectionHandler(Pane cell) {
        FormattedSimpleCell cellInfo = (FormattedSimpleCell) cell.getUserData();
        System.out.println("row " + cellInfo.getRow() + " column " + cellInfo.getColumn());
    }


    //MARK : Support Methods =================================================================================


    public List<Pane> showAvailableCells(List<FormattedSimpleCell> availableCells) {

        setVisibilityAllCells(false);

        List<Pane> visiblePanes = new ArrayList<>();

        for(FormattedSimpleCell cell : availableCells) {
            cellsList.get(cell.getNumber()).setVisible(true);
            visiblePanes.add( cellsList.get(cell.getNumber()) );
        }

        return visiblePanes;
    }


    public void setVisibleCells(List<Pane> cells){
        for (Pane cell : cells){
            cell.setVisible(true);
        }
    }


    public void setVisibilityAllCells(Boolean visibility) {
        for (Pane cell : cellsList){
            cell.setVisible(visibility);
        }
    }


    private void clearMouseClick(List<Pane> visibleCells) {
        for (Pane p : visibleCells) {
            p.setOnMouseClicked(e -> {});
        }
    }

    private void resetStyleCell(Pane cell){
        cell.setStyle("-fx-opacity: 0");
    }


    private void clearMouseClick(Pane cell){
        cell.setOnMouseClicked(e -> {});
    }


    public void setBoardDimensions() {
    }

    public double getControlBoxSectionH() {
        return this.ControlsBoxSection.getHeight();
    }
    public double getControlBoxSectionW() {
        return this.ControlsBoxSection.getWidth();
    }

    public void checkValidity(boolean isValid) {

        if(!isValid) {
            titleLabel.setText("Apparently an error occurred, please reinsert your choice");
        }

    }

    //MARK : Graphic Methods =================================================================================


    private void clearLabels(List<Label> actionsLabel) {
        for (Label label : actionsLabel) {
            label.setText("");
            label.setOnMouseEntered(e -> {});

        }

    }


    private void enableCells(List<Pane> cells){
        for (Pane cell : cells){

            cell.setOnMouseEntered(event -> {
                Pane selectedCell = (Pane) event.getSource();
                selectedCell.setStyle("-fx-opacity: 1; -fx-border-color: aquamarine; -fx-border-width: 5");
            });

            //clear the border
            cell.setOnMouseExited(event -> {
                Pane selectedCell = (Pane) event.getSource();
                selectedCell.setStyle("-fx-opacity: 0");
            });


        }
    }


    private void enableLabels(List<Label> labels){
        for (Label label  : labels) {

            //highlight the border
            label.setOnMouseEntered(event -> {
                Label actionLabel = (Label) event.getSource();
                actionLabel.setStyle("-fx-opacity: 1; -fx-border-color: aquamarine; -fx-border-width: 5");
            });

            //clear the border
            label.setOnMouseExited(event -> {
                Label actionLabel = (Label) event.getSource();
                actionLabel.setStyle("-fx-opacity: 0");
            });

        }

    }


    private void enableLabel(Label label){
        //highlight the border
        label.setOnMouseEntered(event -> {
            Label actionLabel = (Label) event.getSource();
            actionLabel.setStyle("-fx-opacity: 1; -fx-border-color: aquamarine; -fx-border-width: 5");
        });

        //clear the border
        label.setOnMouseExited(event -> {
            Label actionLabel = (Label) event.getSource();
            actionLabel.setStyle("");
        });

    }


    private void disableLabels(List<Label> labels){
        for (Label label : labels){
            label.setText("");
            label.setStyle("");
            label.setOnMouseClicked(e -> {});
            label.setOnMouseEntered(e -> {});
            label.setOnMouseExited(e -> {});

        }
    }


    private void disableCells(List<Pane> cells){
        for(Pane cell : cells){
            cell.setOnMouseClicked(e -> {});
            cell.setOnMouseEntered(e -> {});
            cell.setOnMouseExited(e -> {});
            cell.setStyle("");

        }

    }


    private void disableCell(Pane cell){
        cell.setOnMouseClicked(e -> {});
        cell.setOnMouseEntered(e -> {});
        cell.setOnMouseExited(e -> {});

    }
}
