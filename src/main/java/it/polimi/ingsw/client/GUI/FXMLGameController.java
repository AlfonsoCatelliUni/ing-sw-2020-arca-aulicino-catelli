package it.polimi.ingsw.client.GUI;

import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.polimi.ingsw.client.FormattedCellInfo;
import it.polimi.ingsw.client.FormattedSimpleCell;
import it.polimi.ingsw.events.CTSEvents.ChosenCellToMoveEvent;
import it.polimi.ingsw.events.ClientToServerEvent;
import it.polimi.ingsw.events.STCEvents.GivePossibleCellsToMoveEvent;
import it.polimi.ingsw.events.ServerToClientEvent;
import it.polimi.ingsw.view.client.ClientView;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class FXMLGameController {

    private ClientView clientView;

    //private ClientToServerEvent event;

    private List<Pane> cellsList;

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
    private URL location;

    @FXML
    private ResourceBundle resources;


    //MARK : Constructor =================================================================================


    public FXMLGameController() {
    }


    //MARK : Initialization Methods =================================================================================


    @FXML
    private void initialize() {
        this.cellsList = new ArrayList<>();

        initializeCells();
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


    //MARK : Main Methods =================================================================================


    public void manageEvent(GivePossibleCellsToMoveEvent event) {

        List<FormattedSimpleCell> points = new ArrayList<>();

        for ( Point p : event.cellsAvailableToMove ) {
            points.add( new FormattedSimpleCell(p.x, p.y) );
        }

        List<Pane> visibleCells = showAvailableCells(points);

        for(Pane cell : visibleCells) {

            cell.setOnMouseClicked(e -> {
                Pane selectedCell = (Pane) e.getSource();
                FormattedSimpleCell info = (FormattedSimpleCell) selectedCell.getUserData();
                //clientView.sendCTSEvent(new ChosenCellToMoveEvent("nickname", info.getRow(), info.getColumn()));
                clearMouseClick(visibleCells);
            });

        }

    }


    private void clearMouseClick(List<Pane> visibleCells) {
        for (Pane p : visibleCells) {
            p.setOnMouseClicked(e -> {});
        }
    }


    public List<Pane> showAvailableCells(List<FormattedSimpleCell> availableCells) {
        setVisibilityAllCells(false);

        List<Pane> visiblePanes = new ArrayList<>();

        for(FormattedSimpleCell cell : availableCells) {
            cellsList.get(cell.getNumber()).setVisible(true);
            visiblePanes.add( cellsList.get(cell.getNumber()) );
        }

        return visiblePanes;
    }


    private void cellSelectionHandler(Pane cell) {

        FormattedSimpleCell cellInfo = (FormattedSimpleCell) cell.getUserData();

        //sender.receiveEvent(event );
        // in base all'evento salvato fa cose con la cella
        /*
        setti parametri

        mandi evento

        * clientView.sendCTSEvent(evento); */

        //svuoti evento

        System.out.println("row " + cellInfo.getRow() + " column " + cellInfo.getColumn());

    }


    //MARK : Support Methods =================================================================================


    public void setVisibilityAllCells(Boolean visibilityAllCells) {
        for (Pane cell : cellsList){
            cell.setVisible(visibilityAllCells);
        }
    }



}
