package ludo;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import ludo.Autoplay.AutoplayMode;
import static ludo.Board.GLOBE_TILES;
//import static ludo.Board.START_TILES;
import static ludo.Board.STAR_TILES;
import static ludo.GameState.DEFAULT_AUTOMODE;
import static ludo.GameState.DEFAULT_AUTOPLAYER;
import ludo.ImgPath.Color;
import ludo.ImgPath.Theme;
import static ludo.LudoGUI.IGNORE;
import static ludo.LudoGUI.TILE_SIZE;

/**
 * Represents one of the 4 possible players in a ludo round.
 * This object contains the following information:
 
 * The player's color
 * The player's index in the array of players from the GameState object
 * Whether the player is active in the current round
 * Whether the player is computer-operated or human
 * The player's set of tokens
 * The list of tokens that are currently out of the home area
 * Whether it is the player's turn to play at any given moment
 * The image to represent each of the player's tokens in the GUI
*/

public class Player {
    
    public static final int OUT_OF_BOARD = 60; //position of token out of the track
    public static final int START_DISTANCE = 13;
    public static final int GOAL = 5;

    private int goal, pIndex; //goal: how many tokens have reached the goal, pNumber: index of the player when array of players is created
    private ArrayList<Integer> tokensOut; //position of each token currently in the board
    private Color color;
    private Token[] tokens; //stores the position of each token in the board
    private boolean active, turn, auto;
    private AutoplayMode autoMode;
    private Map<Theme, BufferedImage> img = new HashMap<>();
    
    
     // Initializes an instance of Player given its color.
     
    public Player (Color color) {
        initVars();
        this.color = color;
        for (Theme t: Theme.values()){
            ImgPath.setTokenPath(t);
            try {
                img.put(t,ImageIO.read(new File(ImgPath.getTokenPath(color))));} 
            catch (IOException ex) {
                System.out.println("Image not found.");}
        }
        this.auto = DEFAULT_AUTOPLAYER;
        this.autoMode = DEFAULT_AUTOMODE;
    }
    
    
     // Initializes an instance of Player given its color, whether it is a human or computer player, and the type of auto controller.
     
    public Player(Color color, boolean auto, AutoplayMode autoMode) {
        initVars();
        this.color = color;
        for (Theme t: Theme.values()){
            ImgPath.setTokenPath(t);
            try {
                img.put(t,ImageIO.read(new File(ImgPath.getTokenPath(color))));} 
            catch (IOException ex) {
                System.out.println("Image not found.");}
        }
        this.auto = auto;
        this.autoMode = autoMode;
    }
    
    
    // Initializes common variables to all Player constructors.
    
    private void initVars(){
        this.goal = 0;
        this.turn = false;
        this.active = false;
        this.tokensOut = new ArrayList<>();
        this.tokens = new Token[4];
        for (int i = 0; i < 4; i++) {
            this.tokens[i]= new Token(i);
        }
        this.pIndex = 0;
    }
    
    
     // Restarts the attributes of each of the player's tokens and sets the controller for the computer player to the default setting.
     
    public void reset(){
        this.tokensOut.clear();
        for (int i = 0; i < 4; i++) {
            this.tokens[i]= new Token(i);
            this.setXY(i);
        }
        this.autoMode = DEFAULT_AUTOMODE;
    }

    
    // Gets the player's color.
    
    public String getColor() {
        return this.color.name();
    }
        
     
     // Sets the player's color attribute to a given color.
     
    public void setColor(String color) {
        for (Color c : Color.values()){
            if(color.equalsIgnoreCase(c.name()))
            this.color = c;
        }
    }    
    
    
     // Gets the type of controller for the computer player.
     
    public AutoplayMode getAutoMode(){
        return this.autoMode;
    }

     // Gets the graphic representation of each of the player's tokens.
     
    public BufferedImage getImage(Theme theme) {
        return this.img.get(theme);
    }

     // Gets the number of tokens that the player has in its goal area.
     
    public int getGoal() {
        return this.goal;
    }

    
     // Simulates a given number of tokens in the player's goal area.
     // This method is meant to be used for testing/debugging purposes.
     
    public void setGoal(int goal) {
        this.goal = goal;
    }

    
     // Gets the index of the player in the players array from the GameState object.
     // @return int number [0-3] representing the index of the player in the players array
     
    public int getPIndex() {
        return this.pIndex;
    }

    
     // Sets the index number of the player in the players array from the GameState object.
     
    public void setPIndex(int index) {
        this.pIndex = index;
    }

     // Indicates whether it is the player's turn to play at any given moment.
     
    public boolean getTurn() {
        return this.turn;
    }

    
     // Sets the player as current player if the parameter turn is true.
     
    public void setTurn(boolean turn) {
        this.turn = turn;
    }

     // Indicates whether the player is computer-operated or human.
     
    public boolean getAuto() {
        return this.auto;
    }

     // Sets the player operation as automatic or manual.
     
    public void setAuto(boolean auto) {
        this.auto = auto;
    }
    
     // Indicates whether the player is participating in the current round of ludo.
     
    public boolean getActive() {
        return this.active;
    }

    
     // Sets the player to active or inactive in the game.
     
    public void setActive(boolean active) {
        this.active = active;
    }

     // Gets the list of the player's tokens that are currently out of the home area.
     
    public ArrayList<Integer> getTokensOut() {
        return this.tokensOut;
    }

     // Simulates a list of tokens out of its home area.
     
    public void setTokensOut(ArrayList<Integer> tokensOut) {
        this.tokensOut = tokensOut;
    }

     // Gets the token of index i in the array tokens.
     // @param i int number [0-3] representing the index of the token in the tokens array
     
    public Token getToken(int i) {
        return this.tokens[i];
    }
    
    // Gets the player's set of tokens.
    
    public Token[] getTokens() {
        return this.tokens;
    }

     // Places the given token at the i position of the tokens array
     
    public void setToken(Token token, int i) {
        this.tokens[i] = token;
    }

    private void checkGoal(int tIndex) {
        if (this.getToken(tIndex).getFinalTrack() && this.getToken(tIndex).getPosition() == GOAL) {
            this.goal++;
            this.getTokensOut().remove(this.tokensOut.indexOf(tIndex));
            this.tokens[tIndex].setOut(false);
        }
    }
    
     // Sends a given token to the player's start position.
    
    public void start(int tIndex) {
        this.tokens[tIndex].setPosition(this.pIndex * START_DISTANCE);
        this.tokens[tIndex].setOut(true);
        this.tokensOut.add(tIndex);
    }
    
     // Sends a given token to the player's home area.
     
    public void outOfBoard(int tIndex) {
        if (!this.tokens[tIndex].getFinalTrack()) {
            this.tokens[tIndex].sendHome();
            this.tokensOut.remove(this.tokensOut.indexOf(tIndex));
            this.setXY(tIndex);
        }
    }

    private void startFinalTrack(int pos, int tIndex) {
        this.tokens[tIndex].setPosition(pos % 51);
        this.tokens[tIndex].setFinalTrack(true);
        this.setXY(tIndex);
        this.checkGoal(tIndex);
    }

     // Checks whether a token is standing in a special tile in the board and acts accordingly:
     // If the token is standing in a star it is moved to the next star in the board,  
     // if it is standing in a globe, the player gets another chance to roll the dice, and
     // if it is standing in the player's start position, the token is safe and can not be sent to its home area by other players.
  
    public void checkSpecial(int tIndex, Board board) {
        this.tokens[tIndex].setSafe(false);
        if (!this.tokens[tIndex].getFinalTrack()&&this.tokens[tIndex].getPosition()!=OUT_OF_BOARD) {
            int tokenPosition = this.tokens[tIndex].getPosition();
            if (STAR_TILES.contains(tokenPosition)){
                do{this.tokens[tIndex].moveToken(1);}
                while (!STAR_TILES.contains(this.tokens[tIndex].getPosition()));
            }
            else{
                if (GLOBE_TILES.contains(tokenPosition))
                    this.turn = true;
                else{
                    if (tokenPosition==START_DISTANCE*this.pIndex)
                        this.tokens[tIndex].setSafe(true);
                }
            }
            this.setXY(tIndex);
        }
    }

     // Sets the coordinates x and y of a given token according to its position in the board.
     
    public void setXY(int tIndex) {
        Token token = this.getToken(tIndex);
        switch (token.getPosition()) {
            case OUT_OF_BOARD: {
                token.setCoordinateX(outOfBoardCoordinates(this.pIndex, tIndex)[0]);
                token.setCoordinateY(outOfBoardCoordinates(this.pIndex, tIndex)[1]);
                break;
            }
            default: {
                if (!token.getFinalTrack()) {
                    token.setCoordinateX(boardCoordinates(token.getPosition()));
                    token.setCoordinateY(boardCoordinates((token.getPosition() + 39) % 52));
                } else {
                    token.setCoordinateX(finalTrackCoordinates(token.getPosition(), this.pIndex, tIndex)[0]);
                    token.setCoordinateY(finalTrackCoordinates(token.getPosition(), this.pIndex, tIndex)[1]);
                }
                break;
            }
        }
    }
    
     // Gets the coordinates x and y of a given token
     
    private int[] getXY(Token token) {
        int[]coordinates= new int[2];
        switch (token.getPosition()) {
            case OUT_OF_BOARD: {
                coordinates=outOfBoardCoordinates(this.pIndex, token.getIndex());
                break;
            }
            default: {
                if (!token.getFinalTrack()) {
                    coordinates[0]=boardCoordinates(token.getPosition());
                    coordinates[1]=boardCoordinates((token.getPosition() + 39) % 52);
                } else {
                    coordinates=finalTrackCoordinates(token.getPosition(), this.pIndex, token.getPosition());
                }
                break;
            }
        }
        return coordinates;
    }
    
     // Gets the coordinates x and y for a token in its home area.
     
    private static int[] outOfBoardCoordinates(int pIndex, int tIndex) {
        int coordinates[] = new int[2];
        coordinates[0] = TILE_SIZE/2*(21 + 4 * (tIndex / 2) - 18 * (pIndex / 2));
        coordinates[1] = TILE_SIZE/2*(3 + 4 * (tIndex % 2) + 18 * ((pIndex % 3) > 0 ? 1 : 0));
        return coordinates;
    }
    
     // Gets the coordinate x for a token standing in any of the board tiles.
    
    private static int boardCoordinates(int pos) {
        int coordinate;
        int a = pos % 26;
        int b = 7 - Math.abs(a - 11);
        int c = pos > 24 & pos != 51 ? 2 : 0;
        coordinate = (pos != 24 & pos != 50) ? c + (((a > 4) & (a < 18)) ? (b - (b / 7)) : 0) : 1;
        coordinate = pos > 23 ? TILE_SIZE*(8 - coordinate) : TILE_SIZE*(8 + coordinate);
        return coordinate;
        }

    // Gets the coordinates x and y for a token in its final track.
         
    private static int[] finalTrackCoordinates(int pos, int pIndex, int tIndex) {
        int coordinates[] = new int[2];
        if (pos != GOAL) {
            coordinates[pIndex % 2] = TILE_SIZE*7;
            if (pIndex % 3 == 0) {
                coordinates[(pIndex + 1) % 2] = TILE_SIZE * (pos + 1);
            } else {
                coordinates[(pIndex + 1) % 2] = TILE_SIZE * (13 - pos);
            }
        } else {
            if (pIndex % 2 == 0) {
                coordinates[0] = TILE_SIZE*6 + TILE_SIZE/2*(1 + tIndex);
                coordinates[1] = TILE_SIZE*6 + TILE_SIZE*2 * (pIndex / 2);
            } else {
                coordinates[0] = TILE_SIZE*6 + TILE_SIZE*2*(1 - (pIndex / 2));
                coordinates[1] = TILE_SIZE*6 + TILE_SIZE/2*(1 + tIndex);
            }
        }
        return coordinates;
    }
    
     // Gets the index of a token given the coordinates x and y from the graphic frame 
   
    public int getTokenbyCoord(int[] clickXY) {
        int index = IGNORE;
        int coordinateX, coordinateY;
        for (Token token : this.tokens) {
            System.out.println("token index: "+token.getIndex());
            System.out.println("token position: "+token.getPosition());
            
            coordinateX=getXY(token)[0];
            coordinateY=getXY(token)[1];
            if (clickXY[0] - coordinateX >= 0 && clickXY[0] - coordinateX <= TILE_SIZE) {
                if (clickXY[1] - coordinateY >= 0 && clickXY[1] - coordinateY <= TILE_SIZE) {
                    System.out.println("coordX: "+coordinateX);
                    System.out.println("coordY: "+coordinateY);
                    index = token.getIndex();
                    return index;
                }
            }
        }
        return index;
    }
    
    
     // Move a player's token given its index and the dice result.
     
    public void moveSelectedToken(int tokenIndex, int diceResult) {
        int tokenPosition = this.tokens[tokenIndex].getPosition();
        switch (tokenPosition) {
            case OUT_OF_BOARD: {
                this.start(tokenIndex);
                this.setTurn(false);
                break;
            }
            default: {
                if ((52 + tokenPosition - this.getPIndex() * 13) % 52 + diceResult >= 51) {
                    this.startFinalTrack((52 + tokenPosition - this.getPIndex() * 13) % 52 + diceResult, tokenIndex);
                } else {
                    this.getToken(tokenIndex).moveToken(diceResult);
                    this.checkGoal(tokenIndex);
                }
            }
        }
        this.setXY(tokenIndex);
    }
}
