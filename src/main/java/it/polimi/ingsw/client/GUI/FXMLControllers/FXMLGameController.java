package it.polimi.ingsw.client.GUI.FXMLControllers;

import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.polimi.ingsw.JsonHandler;
import it.polimi.ingsw.client.*;
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


    private List<Label> playersNicknames;

    private List<Label> playersColors;

    private List<Label> playersEffects;

    private List<Label> actionsLabel;

    private List<Pane> actionsPane;


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


    private List<ImageView> blockImageList;
    // 0 ROW ==================================================================================
    @FXML
    private ImageView blocks0;
    @FXML
    private ImageView blocks1;
    @FXML
    private ImageView blocks2;
    @FXML
    private ImageView blocks3;
    @FXML
    private ImageView blocks4;
    // 1 ROW ==================================================================================
    @FXML
    private ImageView blocks5;
    @FXML
    private ImageView blocks6;
    @FXML
    private ImageView blocks7;
    @FXML
    private ImageView blocks8;
    @FXML
    private ImageView blocks9;
    // 2 ROW ==================================================================================
    @FXML
    private ImageView blocks10;
    @FXML
    private ImageView blocks11;
    @FXML
    private ImageView blocks12;
    @FXML
    private ImageView blocks13;
    @FXML
    private ImageView blocks14;
    // 3 ROW ==================================================================================
    @FXML
    private ImageView blocks15;
    @FXML
    private ImageView blocks16;
    @FXML
    private ImageView blocks17;
    @FXML
    private ImageView blocks18;
    @FXML
    private ImageView blocks19;
    // 4 ROW ==================================================================================
    @FXML
    private ImageView blocks20;
    @FXML
    private ImageView blocks21;
    @FXML
    private ImageView blocks22;
    @FXML
    private ImageView blocks23;
    @FXML
    private ImageView blocks24;


    private List<ImageView> domeImageList;
    // 0 ROW ==================================================================================
    @FXML
    private ImageView dome0;
    @FXML
    private ImageView dome1;
    @FXML
    private ImageView dome2;
    @FXML
    private ImageView dome3;
    @FXML
    private ImageView dome4;
    // 1 ROW ==================================================================================
    @FXML
    private ImageView dome5;
    @FXML
    private ImageView dome6;
    @FXML
    private ImageView dome7;
    @FXML
    private ImageView dome8;
    @FXML
    private ImageView dome9;
    // 2 ROW ==================================================================================
    @FXML
    private ImageView dome10;
    @FXML
    private ImageView dome11;
    @FXML
    private ImageView dome12;
    @FXML
    private ImageView dome13;
    @FXML
    private ImageView dome14;
    // 3 ROW ==================================================================================
    @FXML
    private ImageView dome15;
    @FXML
    private ImageView dome16;
    @FXML
    private ImageView dome17;
    @FXML
    private ImageView dome18;
    @FXML
    private ImageView dome19;
    // 4 ROW ==================================================================================
    @FXML
    private ImageView dome20;
    @FXML
    private ImageView dome21;
    @FXML
    private ImageView dome22;
    @FXML
    private ImageView dome23;
    @FXML
    private ImageView dome24;


    private List<ImageView> pawnImageList;
    // 0 ROW ==================================================================================
    @FXML
    private ImageView pawn0;
    @FXML
    private ImageView pawn1;
    @FXML
    private ImageView pawn2;
    @FXML
    private ImageView pawn3;
    @FXML
    private ImageView pawn4;
    // 1 ROW ==================================================================================
    @FXML
    private ImageView pawn5;
    @FXML
    private ImageView pawn6;
    @FXML
    private ImageView pawn7;
    @FXML
    private ImageView pawn8;
    @FXML
    private ImageView pawn9;
    // 2 ROW ==================================================================================
    @FXML
    private ImageView pawn10;
    @FXML
    private ImageView pawn11;
    @FXML
    private ImageView pawn12;
    @FXML
    private ImageView pawn13;
    @FXML
    private ImageView pawn14;
    // 3 ROW ==================================================================================
    @FXML
    private ImageView pawn15;
    @FXML
    private ImageView pawn16;
    @FXML
    private ImageView pawn17;
    @FXML
    private ImageView pawn18;
    @FXML
    private ImageView pawn19;
    // 4 ROW ==================================================================================
    @FXML
    private ImageView pawn20;
    @FXML
    private ImageView pawn21;
    @FXML
    private ImageView pawn22;
    @FXML
    private ImageView pawn23;
    @FXML
    private ImageView pawn24;


    private Image blockLevel1;
    private Image blockLevel2;
    private Image blockLevel3;
    private Image blockDome;

    private Image blueMale;
    private Image blueFemale;
    private Image greyMale;
    private Image greyFemale;
    private Image whiteMale;
    private Image whiteFemale;



    @FXML
    private HBox ControlsBoxSection;

    @FXML
    private ImageView gameBoardImage;

    @FXML
    private GridPane gameBoardGrid;

    @FXML
    private GridPane infoGrid;

    @FXML
    private AnchorPane rightPanel;

    @FXML
    private AnchorPane infoAnchor;


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

        this.blockImageList = new ArrayList<>();
        this.domeImageList = new ArrayList<>();
        this.pawnImageList = new ArrayList<>();

        this.blockLevel1 = new Image(getClass().getResourceAsStream("/Graphics/GameScene/Buildings/BlockLevel1.png"));
        this.blockLevel2 = new Image(getClass().getResourceAsStream("/Graphics/GameScene/Buildings/BlockLevel2.png"));
        this.blockLevel3 = new Image(getClass().getResourceAsStream("/Graphics/GameScene/Buildings/BlockLevel3.png"));
        this.blockDome = new Image(getClass().getResourceAsStream("/Graphics/GameScene/Buildings/Dome.png"));

        this.blueMale = new Image(getClass().getResourceAsStream("/Graphics/GameScene/Pawns/blueMalePawn.png"));
        this.blueFemale = new Image(getClass().getResourceAsStream("/Graphics/GameScene/Pawns/blueFemalePawn.png"));
        this.greyMale = new Image(getClass().getResourceAsStream("/Graphics/GameScene/Pawns/greyMalePawn.png"));
        this.greyFemale = new Image(getClass().getResourceAsStream("/Graphics/GameScene/Pawns/greyFemalePawn.png"));
        this.whiteMale = new Image(getClass().getResourceAsStream("/Graphics/GameScene/Pawns/whiteMalePawn.png"));
        this.whiteFemale = new Image(getClass().getResourceAsStream("/Graphics/GameScene/Pawns/whiteFemalePawn.png"));

        this.maleCellSelected = null;

        initializeCells();
        initializeImage();
        initializeInfoGrid();
        initializeActionsGrid();


        Image imageGrid = new Image(getClass().getResourceAsStream("/Graphics/GameScene/backgroundPanel.png"));

        Background backgroundGrid = new Background(
                new BackgroundImage(imageGrid, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                        new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO,
                                true, true, false, false)));

        infoAnchor.setBackground(backgroundGrid);

        Image imagePanel = new Image(getClass().getResourceAsStream("/Graphics/GameScene/BackgroundRIghtPanel.png"));

        Background backgroundPanel = new Background(
                new BackgroundImage(imagePanel, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                        new BackgroundSize(rightPanel.getWidth(), rightPanel.getHeight(),
                                true, true, true, true)));

        rightPanel.setBackground(backgroundPanel);



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


    private void initializeImage() {

        blockImageList.add(blocks0);
        blockImageList.add(blocks1);
        blockImageList.add(blocks2);
        blockImageList.add(blocks3);
        blockImageList.add(blocks4);
        blockImageList.add(blocks5);
        blockImageList.add(blocks6);
        blockImageList.add(blocks7);
        blockImageList.add(blocks8);
        blockImageList.add(blocks9);
        blockImageList.add(blocks10);
        blockImageList.add(blocks11);
        blockImageList.add(blocks12);
        blockImageList.add(blocks13);
        blockImageList.add(blocks14);
        blockImageList.add(blocks15);
        blockImageList.add(blocks16);
        blockImageList.add(blocks17);
        blockImageList.add(blocks18);
        blockImageList.add(blocks19);
        blockImageList.add(blocks20);
        blockImageList.add(blocks21);
        blockImageList.add(blocks22);
        blockImageList.add(blocks23);
        blockImageList.add(blocks24);

        int index = 0;
        for(int row = 0; row < 5; row++) {
            for(int column = 0; column < 5; column++) {
                index = (row * 5) + column;
                blockImageList.get(index).setUserData( new FormattedSimpleCell(row, column) );
            }
        }

        domeImageList.add(dome0);
        domeImageList.add(dome1);
        domeImageList.add(dome2);
        domeImageList.add(dome3);
        domeImageList.add(dome4);
        domeImageList.add(dome5);
        domeImageList.add(dome6);
        domeImageList.add(dome7);
        domeImageList.add(dome8);
        domeImageList.add(dome9);
        domeImageList.add(dome10);
        domeImageList.add(dome11);
        domeImageList.add(dome12);
        domeImageList.add(dome13);
        domeImageList.add(dome14);
        domeImageList.add(dome15);
        domeImageList.add(dome16);
        domeImageList.add(dome17);
        domeImageList.add(dome18);
        domeImageList.add(dome19);
        domeImageList.add(dome20);
        domeImageList.add(dome21);
        domeImageList.add(dome22);
        domeImageList.add(dome23);
        domeImageList.add(dome24);

        index = 0;
        for(int row = 0; row < 5; row++) {
            for(int column = 0; column < 5; column++) {
                index = (row * 5) + column;
                domeImageList.get(index).setUserData( new FormattedSimpleCell(row, column) );
                //domeImageList.get(index).setImage( new Image(getClass().getResourceAsStream("/Graphics/GameScene/Buildings/Dome.png")) );
                //domeImageList.get(index).setVisible(false);
            }
        }

        pawnImageList.add(pawn0);
        pawnImageList.add(pawn1);
        pawnImageList.add(pawn2);
        pawnImageList.add(pawn3);
        pawnImageList.add(pawn4);
        pawnImageList.add(pawn5);
        pawnImageList.add(pawn6);
        pawnImageList.add(pawn7);
        pawnImageList.add(pawn8);
        pawnImageList.add(pawn9);
        pawnImageList.add(pawn10);
        pawnImageList.add(pawn11);
        pawnImageList.add(pawn12);
        pawnImageList.add(pawn13);
        pawnImageList.add(pawn14);
        pawnImageList.add(pawn15);
        pawnImageList.add(pawn16);
        pawnImageList.add(pawn17);
        pawnImageList.add(pawn18);
        pawnImageList.add(pawn19);
        pawnImageList.add(pawn20);
        pawnImageList.add(pawn21);
        pawnImageList.add(pawn22);
        pawnImageList.add(pawn23);
        pawnImageList.add(pawn24);

        index = 0;
        for(int row = 0; row < 5; row++) {
            for(int column = 0; column < 5; column++) {
                index = (row * 5) + column;
                pawnImageList.get(index).setUserData( new FormattedSimpleCell(row, column) );
            }
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


    public void deleteOpponentPlayer(String nickname) {

        for (int i = 0; i < playersNicknames.size(); i++) {

            if(playersNicknames.get(i).getText().equals(nickname)) {

                //10
                playersNicknames.get(i).setText("----------");

                //5
                playersColors.get(i).setText("-----");

                //30
                playersEffects.get(i).setText("------------------------------");
                break;

            }

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


    public void updateCells(String jsonInfo) {

        List<FormattedCellInfo> cellsInfo = JsonHandler.generateCellsList(jsonInfo);

        int index;

        for( FormattedCellInfo cell : cellsInfo ) {
            index = cell.getCellNumber();

            //update dome and blocks
            if( cell.getRoofInfo().getSecond() ) {
                updateHeight(index, cell.getHeight()-1);
                domeImageList.get(index).setImage(blockDome);
                domeImageList.get(index).setVisible(true);
                //blockImageList.get(index).setVisible(false);

            }
            else {
                domeImageList.get(index).setImage(null);
                updateHeight(index, cell.getHeight() );
            }

            //update pawn
            if( cell.getPawnHere() ) {
                updatePawn(index, cell.getPawnInfo());
            }
            else {
                pawnImageList.get(index).setImage(null);
            }

        }


    }


    private void updateHeight(int index, int height) {

        switch (height) {

            case 1:
                blockImageList.get(index).setImage(blockLevel1);
                 break;

            case 2:
                blockImageList.get(index).setImage(blockLevel2);
                break;

            case 3:
                blockImageList.get(index).setImage(blockLevel3);
                break;

            default:
                blockImageList.get(index).setImage(null);
                break;

        }

    }


    private void updatePawn(int index, Couple<String, String> pawnInfo) {

        String color = pawnInfo.getFirst();
        String sex = pawnInfo.getSecond();

        switch (color) {

            case "BLUE" :
                if(sex.equals("FEMALE")) {
                    pawnImageList.get(index).setImage(blueFemale);
                }
                else {
                    pawnImageList.get(index).setImage(blueMale);
                }
                break;

            case "WHITE" :
                if(sex.equals("FEMALE")) {
                    pawnImageList.get(index).setImage(whiteFemale);
                }
                else {
                    pawnImageList.get(index).setImage(whiteMale);
                }
                break;

            case "GREY" :
                if(sex.equals("FEMALE")) {
                    pawnImageList.get(index).setImage(greyFemale);
                }
                else {
                    pawnImageList.get(index).setImage(greyMale);
                }
                break;

            default:
                pawnImageList.get(index).setImage(null);
                break;

        }

    }


}
