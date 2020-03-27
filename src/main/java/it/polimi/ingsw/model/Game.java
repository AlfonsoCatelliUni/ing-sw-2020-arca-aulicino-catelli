package it.polimi.ingsw.model;

import it.polimi.ingsw.model.BoardPack.Board;
import it.polimi.ingsw.model.BoardPack.Building;
import it.polimi.ingsw.model.Player.*;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;

public class Game {


    private Board gameBoard;


    private ArrayList<Player> players;


    //private ArrayList<Object> objM;


    private Player currentPlayer;


    private ArrayList<Building> buildings;


    private ArrayList<Card> cards;


    private int indexCurrentPlayer;

    private HashMap<String, PlayerDecorator> hash = new HashMap<>();


    // ======================================================================================


    public Game(ArrayList<String> playersNickname, ArrayList<Color> colors, ArrayList<String> godsName, ArrayList<Card> godsCards ) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

        this.gameBoard = new Board();
        this.players = new ArrayList<>();
        this.currentPlayer = null;
        this.indexCurrentPlayer = 0;


        for (int i = 0; i < playersNickname.size(); i++) {


            players.get(i).initPawn(gameBoard, colors.get(i), Sex.MALE, gameBoard.getCell(i, i));
            players.get(i).initPawn(gameBoard, colors.get(i), Sex.FEMALE, gameBoard.getCell(i, i+1));

        }

        currentPlayer = players.get(indexCurrentPlayer);


    }


    //TODO : finish the standard Game contructor
    public Game(){
        this.gameBoard = new Board();

    }


    // ======================================================================================








}
