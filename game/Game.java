package game;
import javax.swing.JOptionPane;
/**
 * Game class - the main class of the "World of Zuul" application.
 *
 * @author TARATIBU Christophe
 * @version 2025/2026 (09.01.2026)
 */
public class Game
{ 
    /** User's  graphical interface */
    private final UserInterface aGui;
    /** Game engine : the logic of the game */
	private final GameEngine aEngine;

    /**
     * Create the game and initialise its internal map. Create the inerface and link to it.
     */
    public Game() 
    {
        
        String vName = JOptionPane.showInputDialog(null, "Welcome, what's your name, future blacksmith ?", "Name of the player", JOptionPane.QUESTION_MESSAGE);
        if (vName == null || vName.isBlank()){
            vName = "Tetsuma";
        }
        this.aEngine = new GameEngine(vName.trim());
        this.aGui = new UserInterface( this.aEngine );
        this.aEngine.setGUI( this.aGui );
    }
    public static void main( String[] pArgs )
    {
        new Game();
        
    }
    
    
} // Game
