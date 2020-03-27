package it.polimi.ingsw.model;

import it.polimi.ingsw.model.BoardPack.Board;
import it.polimi.ingsw.model.BoardPack.Building;
import it.polimi.ingsw.model.Player.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Map;

public class Game {


    private Board gameBoard;


    private ArrayList<Player> players;


    //private ArrayList<Object> objM;


    private Player currentPlayer;


    private ArrayList<Building> buildings;


    private ArrayList<Card> cards;


    private int indexCurrentPlayer;


    // ======================================================================================


    public Game(ArrayList<String> playersNickname, ArrayList<Color> colors, ArrayList<String> godsName, ArrayList<Card> godsCards, Map<String, Player> effectClassMap) {

        this.gameBoard = new Board();
        this.players = new ArrayList<>();
        this.currentPlayer = null;
        this.indexCurrentPlayer = 0;


        for (int i = 0; i < playersNickname.size(); i++) {

            /*
            switch (godsName.get(i)) {
                case "Atlas":
                    players.add(new DomeBuildPlayer(new BasicPlayer(playersNickname.get(i), colors.get(i), godsName.get(i))));
                    break;
                default:
                    players.add(new BasicPlayer(playersNickname.get(i), colors.get(i), godsName.get(i)));
                    break;
            }

             */


            players.get(i).initPawn(gameBoard, Sex.MALE, gameBoard.getCell(i, i));
            players.get(i).initPawn(gameBoard, Sex.FEMALE, gameBoard.getCell(i, i+1));

        }

        currentPlayer = players.get(indexCurrentPlayer);


    }


    //TODO : finish the standard Game constructor
    public Game(){
        this.gameBoard = new Board();

    }


    // ======================================================================================








}
