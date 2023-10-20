package ludo;

import java.util.HashMap;
import java.util.Map;

public class ImgPath {
    
    
     // The four possible player colors
     
    public enum Color {yellow, red, green, blue};

    
     // The three available player themes
     
    public enum Theme {plain, solid, fruits};

    
     // The two available kinds of board (with or without special tiles)
     
    public enum Board {board, specialboard};

    
     // The two possible states of the dice: showing a result or rolling (being animated)
     
    public enum DiceImg {result, animateddice};
    
    public static final String GEN_PATH ="images";
    public static final String DIR ="\\";
    public static final String FILE_EXTENSION =".png";
    public static final String DICE_PATH =GEN_PATH+DIR+"dice\\reddice\\";
    
    
    private static Map<Theme, String> themePath = new HashMap<>();
    private static Map<Color, String> tokenPath = new HashMap<>();
    private static Map<Board, String> boardPath = new HashMap<>();
    private static String dicePath;
    
    
     // Creates a hash table with a path for each of the possible themes
     
    public ImgPath(){
        for (Theme t: Theme.values()){
            themePath.put(t, GEN_PATH+DIR+t.name()+DIR);}
    }
    
    
     // Creates a hash table with the path for the tokens of each color considering a given theme.
     
    public static void setTokenPath(Theme t){
        for(Color c: Color.values()){
            tokenPath.put(c, themePath.get(t)+c.name()+FILE_EXTENSION);}
    }
    
    
     // Gets the path for the image of a token given its color.
     // The path was previously generated considering a specific theme.
    
    public static String getTokenPath(Color c){
        return tokenPath.get(c);
    }
    
    
     // Creates a hash table with the path for the board of each type considering a given theme.
     
    public static void setBoardPath(Theme t){
        for(Board b: Board.values()){
            boardPath.put(b, themePath.get(t)+b.name()+FILE_EXTENSION);}
    }
    
    
     // Gets the path for the image of the board its type (regular or special)
     // The path was previously generated considering a specific theme.
     
    public static String getBoardPath(Board b){
        return boardPath.get(b);}
}
