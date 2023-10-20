package ludo;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import ludo.ImgPath.Theme;


 // Represents a Ludo board.
 // This object contains the following information:
 
 // Whether the board contains special tiles such as stars and globes
 // The position of special tiles
 // The images of the board and special tiles to represent these objects in the GUI

 
public class Board {

    public static final List<Integer> START_TILES = Arrays.asList(0, 13, 26, 39);
    public static final List<Integer> GLOBE_TILES = Arrays.asList(8, 21, 34, 47);
    public static final List<Integer> STAR_TILES = Arrays.asList(5, 11, 18, 24, 31, 37, 44, 50);

    private boolean special;
    private final Map<Theme, BufferedImage> img = new HashMap<>();
    private final Map<Theme, BufferedImage> imgsp = new HashMap<>();

    
     // Initializes an instance of Board according to the parameter special
     // @param special true if the board should contain special tiles (stars and globes) or false if not
     
    public Board(boolean special) {
        this.special = special;
        for (Theme t : Theme.values()) {
            ImgPath.setBoardPath(t);
            try {
                img.put(t, ImageIO.read(new File(ImgPath.getBoardPath(ImgPath.Board.board))));
                imgsp.put(t, ImageIO.read(new File(ImgPath.getBoardPath(ImgPath.Board.specialboard))));
            } catch (IOException ex) {
                System.out.println("Image not found.");
            }
        }
    }

    
     // Gets the graphic representation of the board 
    
    public BufferedImage getImg(Theme theme) {
        return img.get(theme);
    }

    
     // Gets a mask for the board containing the graphics for the special tiles
    
    public BufferedImage getImgSp(Theme theme) {
        return imgsp.get(theme);
    }

    
     // Indicates whether the board includes special tiles (stars and globes)
    
    public boolean getSpecial() {
        return this.special;
    }

    
     // Sets the board layout to regular/special given the boolean special
    
    public void setSpecial(boolean special) {
        this.special = special;
    }

    
     // Sets the board layout to regular/special given the string special
     
    public void setSpecial(String special) {
        this.special = special.equalsIgnoreCase("special");
    }
}
