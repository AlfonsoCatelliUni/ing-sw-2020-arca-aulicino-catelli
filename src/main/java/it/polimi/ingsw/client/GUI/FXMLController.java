package it.polimi.ingsw.client.GUI;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.polimi.ingsw.client.FormattedCellInfo;
import it.polimi.ingsw.client.FormattedSimpleCell;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class FXMLController {


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


    public FXMLController() {
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

        setVisibilityAllCells(false);

        //at each cell in cellsList assign a FormattedSimpleCell
        int index = 0;
        for(int row = 0; row < 5; row++) {
            for(int column = 0; column < 5; column++) {

                index = (row * 5) + column;
                cellsList.get(index).setUserData( new FormattedSimpleCell(row, column) );
            }
        }

        //at each cell in cellsList assign a setOnMouseClicked action
        for (Pane cell : cellsList){

//           cell.setOnMouseMoved(event ->
//                   cell.setStyle("-fx-border-color: blue"));

            cell.setOnMouseClicked(event -> {
                Pane selectedCell = (Pane) event.getSource();
                cellSelectionHandler(selectedCell);
            });
        }

    }


    //MARK : Main Methods =================================================================================


    public void showAvailableCells( List<FormattedSimpleCell> availableCells ) {
        setVisibilityAllCells(false);

        for(FormattedSimpleCell cell : availableCells) {
            cellsList.get(cell.getNumber()).setVisible(true);
        }

    }


    private void cellSelectionHandler(Pane cell) {
        FormattedSimpleCell cellInfo = (FormattedSimpleCell) cell.getUserData();

        System.out.println("row " + cellInfo.getRow() + " column " + cellInfo.getColumn());

    }


    //MARK : Support Methods =================================================================================


    public void setVisibilityAllCells(Boolean visibilityAllCells) {
        for (Pane cell : cellsList){
            cell.setVisible(visibilityAllCells);
        }
    }



}
