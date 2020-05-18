package it.polimi.ingsw.client.GUI.FXMLControllers;

import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.polimi.ingsw.client.ClientJsonHandler;
import it.polimi.ingsw.client.FormattedPlayerInfo;
import it.polimi.ingsw.client.FormattedSimpleCell;
import it.polimi.ingsw.client.GUI.GUI;
import it.polimi.ingsw.events.CTSEvents.ChosenCellToMoveEvent;
import it.polimi.ingsw.events.CTSEvents.ChosenInitialPawnCellEvent;
import it.polimi.ingsw.events.CTSEvents.ChosenPawnToUseEvent;
import it.polimi.ingsw.events.STCEvents.AskInitPawnsEvent;
import it.polimi.ingsw.events.STCEvents.GivePossibleCellsToMoveEvent;
import it.polimi.ingsw.view.client.ClientView;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class FXMLGameController {



    private GUI gui;

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


    public void initGameController( GUI gui, String info ) {

        this.gui = gui;
        this.clientView = gui.getClientView();

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


        //at each cell in cellsList assign a setOnMouseClicked action
        for (Pane cell : cellsList) {

            //highlight the border
            cell.setOnMouseEntered(event -> {
                Pane selectedCell = (Pane) event.getSource();
                selectedCell.setStyle("-fx-opacity: 1; -fx-border-color: aquamarine; -fx-border-width: 5");
            });

            //clear the border
            cell.setOnMouseExited(event -> {
                Pane selectedCell = (Pane) event.getSource();
                selectedCell.setStyle("-fx-opacity: 0");
            });

            //selected cell
            cell.setOnMouseClicked(event -> {
                Pane selectedCell = (Pane) event.getSource();
                cellSelectionHandler(selectedCell);
            });
        }

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

        for (Label label : playersNicknames){
            label.setFont(Font.loadFont(
                    getClass().getResource("/Font/DisneyHeroic.ttf").toExternalForm(),
                    10
            ));
        }

        for (Label label : playersColors){
            label.setFont(Font.loadFont(
                    getClass().getResource("/Font/DisneyHeroic.ttf").toExternalForm(),
                    8
            ));
        }

        for (Label label : playersEffects){
            label.setFont(Font.loadFont(
                    getClass().getResource("/Font/DisneyHeroic.ttf").toExternalForm(),
                    6
            ));
        }

    }


    private void initializeActionsGrid(){

        this.actionsLabel = new ArrayList<>();
        this.actionsPane = new ArrayList<>();

        actionsLabel.add(titleLabel);
        actionsLabel.add(action1Label);
        actionsLabel.add(action2Label);
        actionsLabel.add(action3Label);
        actionsLabel.add(action4Label);

        actionsPane.add(titlePane);
        actionsPane.add(action1Pane);
        actionsPane.add(action2Pane);
        actionsPane.add(action3Pane);
        actionsPane.add(action4Pane);

        actionsLabel.get(0).setVisible(true);





    }


    //MARK : Main Methods =================================================================================


    public void manageEvent(AskInitPawnsEvent event) {

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

        for(Pane cell : visibleCells) {

            cell.setOnMouseClicked(e -> {

                Pane selectedCell = (Pane) e.getSource();
                selectedCell.setVisible(false);

                FormattedSimpleCell info = (FormattedSimpleCell) selectedCell.getUserData();

                titleLabel.setText("SELECT THE CELL FOR THE FEMALE PAWN");

                if(maleCellSelected == null) {
                    maleCellSelected = info;
                }
                else {
                    clientView.sendCTSEvent( new ChosenInitialPawnCellEvent(event.nickname, maleCellSelected.getRow(), maleCellSelected.getColumn(), info.getRow(), info.getColumn()) );
                    clearMouseClick(visibleCells);
                }

            });

        }

    }


    public void manageEvent(GivePossibleCellsToMoveEvent event, int pawnRow, int pawnColumn) {

        List<FormattedSimpleCell> cells = FormattedSimpleCell.generateFromPointList(event.cellsAvailableToMove);

        List<Pane> visibleCells = showAvailableCells(cells);

        for(Pane cell : visibleCells) {

            cell.setOnMouseClicked(e -> {
                Pane selectedCell = (Pane) e.getSource();
                FormattedSimpleCell info = (FormattedSimpleCell) selectedCell.getUserData();

                clientView.sendCTSEvent(new ChosenCellToMoveEvent(event.receiverNickname, pawnRow, pawnColumn, info.getRow(), info.getColumn()));
                clearMouseClick(visibleCells);

                gui.setRowUsedPawn(info.getRow());
                gui.setColumnUsedPawn(info.getColumn());
            });

        }

    }


    public void manageEvent() {

    }


    public void choosePawnToUse(String playerNickname, List<Point> points, ClientView clientView) {

        List<FormattedSimpleCell> cells = new ArrayList<>();

        for(Point point : points) {
            cells.add(new FormattedSimpleCell(point.x ,point.y));
        }

        List<Pane> visibleCells = showAvailableCells(cells);

        for(Pane cell : visibleCells) {

            cell.setOnMouseClicked(e -> {

                Pane selectedCell = (Pane) e.getSource();
                FormattedSimpleCell info = (FormattedSimpleCell) selectedCell.getUserData();
                clientView.sendCTSEvent((new ChosenPawnToUseEvent(playerNickname, info.getRow(), info.getColumn())));

            });

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


    public void setVisibilityAllCells(Boolean visibilityAllCells) {
        for (Pane cell : cellsList){
            cell.setVisible(visibilityAllCells);
        }
    }


    public void showAvailableActions(List<Label> actionsLabel){
    }


    private void clearMouseClick(List<Pane> visibleCells) {
        for (Pane p : visibleCells) {
            p.setOnMouseClicked(e -> {});
        }
    }


    public void setBoardDimensions() {

    }

    public double getControlBoxSectionH() {
        return this.ControlsBoxSection.getHeight();
    }
    public double getControlBoxSectionW() {
        return this.ControlsBoxSection.getWidth();
    }


}
