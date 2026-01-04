package game;
/**
 * Game class - the main class of the "World of Zuul" application.
 *
 * @author TARATIBU Christophe
 */
public class Game
{ 
    private final UserInterface aGui;
	private final GameEngine aEngine;

        /**
     * Create the game and initialise its internal map. Create the inerface and link to it.
     */
    public Game() 
    {
        this.aEngine = new GameEngine();
        this.aGui = new UserInterface( this.aEngine );
        this.aEngine.setGUI( this.aGui );
    }
    public static void main( String[] pArgs )
    {
        new Game();
        
    }
    
    
} // Game
