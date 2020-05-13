package it.polimi.ingsw.client.GUI.scenes;

import it.polimi.ingsw.client.GUI.GUI;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import javax.lang.model.AnnotatedConstruct;
import javax.print.DocFlavor;
import java.io.IOException;
import java.net.URL;

public class GameScene implements TheScene {

    private GUI gui;

    private Scene scene;

    private Stage stage;


    public GameScene(GUI gui, Stage stage) {

        this.gui = gui;
        this.stage = stage;


        Parent root = null;
        try {
            root = FXMLLoader.load( getClass().getResource("/FXML/new_game_layout.fxml") );
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert root != null;
        this.scene = new Scene(root);

    }



    @Override
    public Scene getScene() {
        return this.scene;
    }


    private GridPane buildBoardGrid( int row, int column) {

        GridPane gridPane = new GridPane();

        for( int i = 0; i < row; i++ ) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setPercentHeight( 100.0 / row );
            gridPane.getRowConstraints().add(rowConstraints);
        }

        for( int i = 0; i < column; i++ ) {
            ColumnConstraints columnConstraints = new ColumnConstraints();
            columnConstraints.setPercentWidth( 100.0 / column );
            gridPane.getColumnConstraints().add(columnConstraints);
        }

        gridPane.setGridLinesVisible(true);


        return gridPane;
    }




}
