package ludo;

import java.awt.Dimension;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import ludo.ImgPath.Color;
import ludo.ImgPath.Theme;

public class Main {

    private static GameState game;
    private static Selector selectWindow;

    public static void main(String[] args) {
        // Set the look and feel of the GUI to the system default
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Show instructions popup message
        String message =   "  ------------------------------------------------------- LUDO--------------------------------------------------------------------- \n"
        		+" - Project by Divya Shrivastava\n"
        		+" \n"
        		+"Ludo is a popular board game that is played by two to four players. The objective of the game is to move all your pieces from the starting point to the home base by rolling the dice and following the numbered squares on the board.\n"
        		+" \n"
        		+"Select the theme,Color and board in which you wanna play at the beginning next to it!\n "
        		+" \n"
+ "Here are some basic rules of Ludo:\n"
                + "-Each player starts with 4 pieces of the same color, which are placed in the starting area.\n"
                + "-Players take turns rolling a dice to determine how many squares they can move their pieces.\n"
                + "-Players can only move their pieces forward, and cannot move a piece that is already in the home base.\n"
                + "-If a player lands on a square that is already occupied by an opponent's piece, the opponent's piece is sent back to the starting area.\n"
                + "-Players can move one piece the full value of their roll or choose to move a different piece instead\n"
                + "-The first player to move all of their pieces into the home base wins the game.\n"
                + "\n"
                + "Keyboard Hacks:  // CTRL+D : allows the user to input the dice result instead of using a random value.\r\n"
                + "                 // CTRL+P:  shows a Selector dialog box to modify the number and colors of the players participating in the round of ludo.\r\n"
                + "                 // CTRL+B:  shows a Selector dialog box to modify the type of Board (regular or special).\r\n"
                + "                 // CTRL+T:  shows a Selector dialog box to modify the Theme."
                +" \n"
                +"*Also, If the dice stuck at the begining, Use the restart feature in the Game Menu.\n"
                + "Good luck and have fun!";

        JOptionPane.showMessageDialog(null, message, "Instructions", JOptionPane.INFORMATION_MESSAGE);

        // Set up the game
        new ImgPath();

        selectWindow = new Selector("theme");
        Theme theme = selectWindow.selectedTheme();

        selectWindow = new Selector("player", theme.name());
        List<Color> plColors = selectWindow.selectedPlayers();

        selectWindow = new Selector("board", theme.name());
        boolean special = selectWindow.selectedBoard();

        game = new GameState(theme, plColors, special);

        LudoGUI.drawGUI(game);
        System.exit(0);
    }
}
