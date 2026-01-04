package game;
import java.util.StringTokenizer;

/**
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 * 
 * This parser reads user input and tries to interpret it as an "Adventure"
 * command. Every time it is called it reads a line from the terminal and
 * tries to interpret the line as a two word command. It returns the command
 * as an object of class Command.
 *
 * The parser has a set of known command words. It checks user input against
 * the known commands, and if the input is not one of the known commands, it
 * returns a command object that is marked as an unknown command.
 * 
 * @author  Michael Kolling and David J. Barnes + D.Bureau
 * @version 2008.03.30 + 2013.09.15
 */
public class Parser 
{
    private final CommandWords aCommandWords;  // (voir la classe CommandWords)
    /**
     * Constructeur par defaut qui cree les 2 objets prevus pour les attributs
     */
    public Parser() 
    {
        this.aCommandWords = new CommandWords();
        // System.in designe le clavier, comme System.out designe l'ecran
    } // Parser()

    /**
     *  Retrieve the command
     * @param pInputLine Line written by the user
     * @return The command created
     */
    public Command getCommand( final String pInputLine ) 
    {
        String vWord1;
        String vWord2;

        StringTokenizer tokenizer = new StringTokenizer( pInputLine );

        if ( tokenizer.hasMoreTokens() )
            vWord1 = tokenizer.nextToken();      // get first word
        else
            vWord1 = null;

        if ( tokenizer.hasMoreTokens() ) {
            StringBuilder vRest = new StringBuilder( tokenizer.nextToken() );
            while ( tokenizer.hasMoreTokens() ) {
                vRest.append( " " ).append( tokenizer.nextToken() );
            }
            vWord2 = vRest.toString();      // capture the full remaining input
        }
        else
            vWord2 = null;

        // note: we just ignore the rest of the input line.

        // Now check whether this word is known. If so, create a command
        // with it. If not, create a "null" command (for unknown command).

        if ( this.aCommandWords.isCommand( vWord1 ) ) {
            return new Command( vWord1, vWord2 );
        } else {
            return new Command( null, vWord2 );
        }
    } // getCommand(.)

    /**
     * Returns a String with valid command words.
     * @return A string with valid command words.
     */
    public String showCommands(){
        return this.aCommandWords.getCommandList();
    } // was showCommands()
} // Parser
