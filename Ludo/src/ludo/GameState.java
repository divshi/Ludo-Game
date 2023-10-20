package ludo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import ludo.Autoplay.AutoplayMode;
import ludo.ImgPath.*;
import static ludo.Player.GOAL;
import static ludo.Player.OUT_OF_BOARD;

/**
   Represents the state of each of the elements present in a round of Ludo.
   This state information includes:
 //
 // The Board on which the game is played
 // The Dice
 // The set of Players
 // The controller for the computer player (Autoplay)
 // The Theme for the graphic representation
 // The index of the current player at any given moment
 // If a round of Ludo is being played
 // If the current player should roll the dice or move a token
 // List of indexes of the active players in the round
 // List of the possible moves at any given moment
 // List of players that completed the game (all tokens at the goal area)
 // The results of a round
 */

public class GameState {
    
    //default game settings:
    public static final Theme DEFAULT_THEME = Theme.plain;
    public static final boolean DEFAULT_BOARD = true;
    public static final boolean DEFAULT_AUTOPLAYER= false;
    public static final AutoplayMode DEFAULT_AUTOMODE= AutoplayMode.customAI;
    private static List<ImgPath.Color> DEFAULT_PLAYERS = Arrays.asList(Color.blue, Color.red, Color.yellow, Color.green);
    
    private Board board;
    private Dice dice;
    private Player[] players;
    private Autoplay computerPlayer;
    private Theme theme;
    private int currentPlayer, turn; //index of current player, count of turns since game 
    private boolean diceRoller, playing, debug; // check if click is dice roller or token selector
    private ArrayList<Integer> xPlayers, xTokens, winners; //indexes of active players
    private String gameResults;
     
    // Initializes an instance of GameState using default game settings. 
    
    public GameState(){
        this.gameResults = "";
        initVars();
        this.theme = DEFAULT_THEME;
        this.board = new Board(DEFAULT_BOARD);
        createSetOfPlayers(DEFAULT_PLAYERS, DEFAULT_AUTOPLAYER, DEFAULT_AUTOMODE);
    } 
    
    
    // Initializes an instance of GameState specifying theme, list of players, and type of board.
    // Uses default settings for auto/manual setting and AutoplayMode.
    
    public GameState(Theme theme, List<ImgPath.Color> plColors, boolean specialBoard){
        this.gameResults = "";
        initVars();
        this.theme = theme;
        this.board = new Board(specialBoard);
        createSetOfPlayers(plColors, DEFAULT_AUTOPLAYER, DEFAULT_AUTOMODE);
    } 
    
    
    // Initializes an instance of GameState specifying each player's auto/manual settings and AutoplayMode.
    // It also specifies theme, list of players, and type of board.
    
    public GameState(Theme theme, List<ImgPath.Color> plColors, List<Boolean> auto, List<AutoplayMode> autoMode, boolean specialBoard){
        initVars();
        this.theme = theme;
        this.board = new Board(specialBoard);
        createSetOfPlayers(plColors, auto, autoMode);
    } 
    
    
    // Initializes common variables to all GameState constructors.
    
    private void initVars(){
        this.dice = new Dice();
        this.playing = true;
        this.currentPlayer = 0;
        this.turn = 0;
        this.xPlayers=new ArrayList<>(); 
        this.players = new Player[4];
        this.diceRoller=true;
        this.xTokens=new ArrayList<>();
        this.winners=new ArrayList<>();
        this.computerPlayer= new Autoplay();
        this.gameResults = "";
        this.debug = false;
    }

    
    // Get the current state of the object representing a ludo board.
    // The Board class contains information about the type of board (regular or special),
    
    public Board getBoard() {
        return this.board;
    }

    
     // Sets the local attribute board to the value of the parameter board.
    
    public void setBoard(Board board) {
        this.board = board;
    }

     
     // Sets the attribute special from the object board to regular (false) or special (true).
    
    public void setSpecial(String special) {
        if(special.equals("special"))
            this.board.setSpecial(true);
    }

    
     // Get the current state of the object representing a dice
     // The Dice class contains information about the result after casting the dice,
    
    public Dice getDice() {
        return this.dice;
    }

    
     // Sets the local attribute dice to the value of the parameter dice
     
    public void setDice(Dice dice) {
        this.dice = dice;
    }

    
     // Gets an array with objects of the class Player, representing each of the players for this round
     
    public Player[] getPlayers() {
        return this.players;
    }

    
     // Sets the attribute players to reference the parameter array players
     
    public void setPlayers(Player[] players) {
        this.players = players;
    }
    
    
     // Returns an object of class Player given its index i in the attribute players array
   
    public Player getPlayer(int i) {
        return this.players[i];
    }
    
    
     // Returns an object of class Player given its color
    
    public Player getPlayer(String color) {
        for (Player player: this.players)
            if(player.getColor().equalsIgnoreCase(color))
                return player;
        return null;
    }
    
   
     // Gets the list of indexes of active players 
     
    public ArrayList<Integer> getXPlayers() {
        return this.xPlayers;
    }

 
     // Adds a player index to the list of active players
     // After adding a player to the list, sorts the list in ascending order to keep the order of each player's turn
     
    public void addXPlayers(int playerIndex) {
        this.xPlayers.add(playerIndex);
        Collections.sort(this.xPlayers);
    }
    
    
     // Removes a player index from the list of active players
     // @param playerIndex integer number [0-3] representing the index of one of the 4 players to be removed
     
    public void removeXPlayers(int playerIndex) {
        this.xPlayers.remove(this.xPlayers.indexOf(playerIndex));
    }
    
    
     // Gets the list of indexes of the active tokens of the current player 
     // Tokens that can be moved at any given moment.
     
    public ArrayList<Integer> getXTokens() {
        return this.xTokens;
    }

    
     // Gets the list of players that completed the game (have all their tokens at the goal area)
     // @return list of winners
     
    public ArrayList<Integer> getWinners() {
        return this.winners;
    }

    
     // Adds a player to the list of winners
     
    public void addWinners(int playerIndex) {
        this.winners.add(playerIndex);
    }

     
     // Gets the current theme/style used for the GUI
     
    public Theme getTheme() {
        return this.theme;
    }

    
     // Sets the game graphics style to the given theme
   
    public void setTheme(Theme theme) {
        this.theme = theme;
    }
    
    
     // Sets the game graphics style to the given theme name
     
    public void setTheme(String theme) {
        for(Theme t: Theme.values())
            if(theme.equalsIgnoreCase(t.name()))
                this.theme = t;
    }

    
     // Indicates whether it is time for the current player to roll the dice (true) or to select and move a token (false).
     // @return true if the current player should roll the dice, false if the player should move a token
     
    public boolean getDiceRoller() {
        return this.diceRoller;
    }
    
    
     // Indicates whether a round of ludo is being played (true) or not (false).
   
    public boolean getPlaying() {
        return this.playing;
    }
    
    
     // Sets the value of the attribute playing to the given parameter playing
     
    public void setPlaying(boolean playing) {
        this.playing=playing;
    }
    
    public boolean getDebug() {
        return this.debug;
    }
    
    
     // Sets the value of the attribute debug to the given parameter debug
    
    public void setDebug(boolean debug) {
        this.debug=debug;
    }

    
     // Gets the index of the current player in the array players
     
    public int getCurrentPlayer(){
        return this.currentPlayer;
    }
    
     // Sets the current player to a given index in the range 0-3
     
    public void setCurrentPlayer(int index){
        this.currentPlayer=index%4;
    }
    
    
     // Gets the number of played turns at any given moment
     // A turn starts when a player gets the dice to roll and ends when the dice is passed to the next player
     
    public int getTurnCount(){
        return this.turn;
    }
    
    
     // Gets a string with the results of a round of ludo
     // This is a list of winners by first to last to have finished the game
     
    public String getGameResults(){
    	return this.gameResults;
    }

     // Initializes the attributes of each of the active players for this round.
    
    private void createSetOfPlayers(List<ImgPath.Color> colors, List<Boolean> auto, List<AutoplayMode> autoMode) {
        int i = 0;
        for (ImgPath.Color c : ImgPath.Color.values()) {
            this.players[i] = new Player(c, auto.get(i), autoMode.get(i));
            this.players[i].setPIndex(i);
            //System.out.println("Player "+c.name()+" created.");
            for(int j=0; j<4; j++){
                this.players[i].setXY(j);
            }
            if (colors.contains(c)) {
                this.players[i].setActive(true);
                this.xPlayers.add(i);
            }
            i++;
        }
    }
   
     // Initializes the attributes of each of the active players for this round.
     
    private void createSetOfPlayers(List<ImgPath.Color> colors, boolean auto, AutoplayMode autoMode) {
        int i = 0;
        for (ImgPath.Color c : ImgPath.Color.values()) {
            this.players[i] = new Player(c, auto, autoMode);
            this.players[i].setPIndex(i);
            //System.out.println("Player "+c.name()+" created.");
            for(int j=0; j<4; j++){
                this.players[i].setXY(j);
            }
            if (colors.contains(c)) {
                this.players[i].setActive(true);
                this.xPlayers.add(i);
            }
            i++;
        }
    }
    
     // Sends all the tokens of each active player to its starting position
     
    public void restart(){
        for(Player p: players)
            if(p.getActive())
                p.reset();
        turn = 0;
        currentPlayer = 0;
        diceRoller=true;
    }
    
     // Initializes a player that was previously considered not-active in the game
     
    public void addPlayer(String color){
        if(!this.getPlayer(color).getActive()){
            this.getPlayer(color).reset();
            this.getPlayer(color).setActive(true);
            addXPlayers(this.getPlayer(color).getPIndex());
            turn = this.xPlayers.indexOf(currentPlayer);}
    }
    
     // Removes an active player from the game given its color
     
    public void removePlayer(String color){
        this.getPlayer(color).setActive(false);
        removeXPlayers(this.getPlayer(color).getPIndex());
        turn = this.xPlayers.indexOf(currentPlayer);
    }    

     // Compares the position of a given token with other players' tokens and if equal, sends the opponent's token to its home area
   
    private void checkOtherTokens(int pIndex, int tIndex) {
        int tokenPosition = this.players[pIndex].getToken(tIndex).getPosition();
        for (int i = 0; i < this.xPlayers.size(); i++) {
            if (!this.players[pIndex].getColor().equals(players[xPlayers.get(i)].getColor())) {
                for (int j = 0; j < 4; j++) {
                    if (players[xPlayers.get(i)].getToken(j).getPosition() == tokenPosition && players[xPlayers.get(i)].getToken(j).getPosition() != OUT_OF_BOARD && !players[xPlayers.get(i)].getToken(j).getSafe()) {
                        players[xPlayers.get(i)].outOfBoard(j);
                    }
                }
            }
        }
    }

     // The current player rolls the dice and updates the list of active tokens
     
    public void rollAndCheckActiveTokens() {
        this.currentPlayer = this.xPlayers.get(this.turn % this.xPlayers.size());
        this.dice.rollDice(this.currentPlayer);
        this.xTokens.clear();

        if (this.dice.getIsSix()) {
            this.players[currentPlayer].setTurn(true);// flag for throwing the dice again if a token is moved
            for (Token token : this.players[currentPlayer].getTokens()) {
                if (!(token.getFinalTrack() && token.getPosition() == GOAL)) {
                    this.xTokens.add(token.getIndex());
                }
            }
        } else {
            this.players[currentPlayer].setTurn(false);
            for (int index : this.players[currentPlayer].getTokensOut()) {
                this.xTokens.add(index);
            }
        }
    }
    
    
     // If there are no possible moves, the current player passes the dice to the next player 
     
    public void checkMoveOrPass(){
        if (this.xTokens.size() > 0) {
            this.diceRoller = false;} 
        else { 
        	//if no tokens to move, pass and let player roll dice
            this.turn++;
        }
        this.currentPlayer = this.xPlayers.get(this.turn % this.xPlayers.size());
    }
    
    
     // Moves the selected token to a new position, checks for other tokens and special tiles (in case of special board), 
     // checks if the player has finished the game in this turn, and if so, checks if the game is over
    
    public void selectAndMove(int tokenIndex) {
        Token thisToken = this.players[currentPlayer].getTokens()[tokenIndex];
        if(!(thisToken.getFinalTrack()&&!thisToken.getOut())){
        this.players[currentPlayer].moveSelectedToken(tokenIndex,this.dice.getResult());
        if (!thisToken.getFinalTrack()) {
            this.checkOtherTokens(this.players[currentPlayer].getPIndex(), tokenIndex);
            if (this.board.getSpecial()) {
                this.players[currentPlayer].checkSpecial(tokenIndex, this.board);
            }
        }
        if (this.players[currentPlayer].getGoal() == 4) {
            this.addWinners(this.players[currentPlayer].getPIndex());
            this.removeXPlayers(this.players[currentPlayer].getPIndex());
            if (this.getXPlayers().isEmpty()) {
                this.playing = false;
                this.gameResults = "\nResults:\n\n";
                for (int i = 0; i < this.getWinners().size(); i++) {
                    this.gameResults += (i + 1)+" place - "+this.getPlayers()[this.getWinners().get(i)].getColor()+" player\n";
                }
            }
        }
        if (!this.players[currentPlayer].getTurn()) {
            this.turn++;
        }
        this.diceRoller = true;
        if (playing)
            this.currentPlayer = this.xPlayers.get(this.turn % this.xPlayers.size());
        }
    }
     
    
     // Calls the method SelectAndMove with the token selected by the computer player as the argument
     
    public void autoMove() {
        selectAndMove(computerPlayer.selectToken(this));
    }
}
