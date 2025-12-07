package game;
/**
 * Command class - a command issued by the user.
 *
 * @author Christophe TARATIBU
 */
public class Command
{
    private String aCommandWord;
    private String aSecondWord;
    /**
     * Create a command object. First and second word must be supplied, but
     * either one (or both) can be null. The command word should be null to
     * indicate that this was a command that is not recognised by this game.
     * @param pCommandWord The first word of the command. Null if the command
     *                     was not recognised.
     * @param pSecondWord  The second word of the command.
     */
    public Command(final String pCommandWord,final String pSecondWord){
        this.aCommandWord = pCommandWord;
        this.aSecondWord = pSecondWord;

    }

    /**
     * Returns the command word of this command.
     * @return The command word (the first word) of this command. If the
     *         command was not understood, the result is null.
     */
    public String getCommandWord(){
        return this.aCommandWord;
    }

    /**
     * Returns the second word of this command.
     * @return The second word of this command. Returns null if there was no
     *         second word.
     */
    public String getSecondWord(){
        return this.aSecondWord;
    }

    /**
     * Verifies is command has a second word
     * @return true if the command has a second word.
     */
    public boolean hasSecondWord(){
       
        return this.aSecondWord != null;
    }

    /**
     * Verifies if the command word is unknown
     * @return true if the command word is unknown
     */
    public boolean isUnknown(){
        //System.out.println(this.aCommandWord);
        return this.aCommandWord == null;
    }
} // Command
