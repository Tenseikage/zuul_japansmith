package game;
import java.io.InputStream;
import java.util.Scanner;
import java.util.Stack;

/**
 *  This class is part of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.
 * 
 *  This class creates all rooms, creates the parser and starts
 *  the game.  It also evaluates and executes the commands that 
 *  the parser returns.
 * 
 * @author  Michael Kolling and David J. Barnes
 * @version 1.0 (Jan 2003) DB edited (2019) Christophe TARATIBU modified (2025)
 */
public class GameEngine
{
    private Parser        aParser;
    //private Room          aCurrentRoom;
    //private Stack<Room> aPreviouRooms;
    //private LinkedList<Room> aLastRooms;
    private UserInterface aGui;
    private boolean aTestMode;
    Player aPlayer;

    /**
     * Constructor for objects of class GameEngine
     */
    public GameEngine()
    {
        this.aParser = new Parser();
        this.createRooms();
        //this.aPreviouRooms = new Stack<>();
        this.aTestMode = false;
       
    }

    /**
     * Sets the GUI
     * @param pUserInterface User Interface 
     */
    public void setGUI( final UserInterface pUserInterface )
    {
        this.aGui = pUserInterface;
        this.printWelcome();
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        this.aGui.print( "\n" );
        this.aGui.println( "Welcome to game of The return of Yamata no Orochi !" );
        this.aGui.println( "The monster came back to life and Tetsuma must create a katana to save Japan !." );
        this.aGui.println("From now, you have to help him find materials to let him create the weapon !");
        this.aGui.println( "Type 'help' if you need help." );
        this.aGui.print( "\n" );
        this.aGui.println( this.aPlayer.getCurrentRoom().getLongDescription() );
        if ( this.aPlayer.getCurrentRoom().getImageName() != null )
            this.aGui.showImage( this.aPlayer.getCurrentRoom().getImageName() );
    }

   /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms(){
        Room vShirago = new Room("in your village of birth, Shirakawa-go.","./Images/shirakawa.jpg");
        Room vGujoHachi = new Room("In the Gujô Hachiman castle.","./Images/gujohachi.jpg");
        Room vOsaka = new Room("in the Osaka city.","./Images/Osaka.jpg");
        Room vTsushi = new Room("in the Tsushima island.","./Images/Tsushima.jpg");
        Room vSeki = new Room("in the Seki blacksmiths village, it's the end of your odyssey.","./Images/seki.jpg");
        Room vNara = new Room("in the Nara city known for its deers.","./Images/Nara_fire.jpg");
        Room vGinkaku = new Room("in the Ginkaku-ji temple.","./Images/Ginkakuji_fall_2.jpg");
        Room vKinkaku = new Room("in the Kinkaku-ji temple.","./Images/Kinkaku.jpg");
        Room vYoshino = new Room("in the Yoshino national park, the forest is big here.","./Images/Yoshino_park.jpg");
        Room vNagoya  = new Room("in the Nagoya city","./Images/Nagoya.jpg");
        Room vMtFuji = new Room("at the foot of the Mt Fuji.","./Images/MtFuji.jpg");
        Room vTokyo = new Room("in the Tokyo tower, the biggest tower of Japan.","./Images/tokyo_tower.jpg");
        Room vSapporo = new Room("in the Sapporo city, in the north of Japan.","./Images/sapporo_snow.jpg");
        Room vAogashi = new Room("in the island of Aogashima, in the middle of the ocean.","./Images/Aogashima.jpg");
        
        
        
        vShirago.setExit("south", vGujoHachi);
        vShirago.addItem("Mochi", new Item("A sweety mochi", 1));

        // GujoHachi : up -> MtFuji, west -> Osaka
        vGujoHachi.setExit("up", vMtFuji);
        vGujoHachi.setExit("west", vOsaka);

        // Osaka : north -> Ginkaku, east -> GujoHachi, south -> Nara, west -> Tsushi
        vOsaka.setExit("north", vGinkaku);
        vOsaka.setExit("east", vGujoHachi);
        vOsaka.setExit("south", vNara);
        vOsaka.setExit("west", vTsushi);

        // Ginkaku : east -> Kinkaku, south -> Osaka
        vGinkaku.setExit("east", vKinkaku);
        vGinkaku.setExit("south", vOsaka);
        vGinkaku.addItem("Silver Shigane", new Item("A silver metal used for blade crafting", 3));

        // Kinkaku : south -> Ginkaku
        vKinkaku.setExit("south", vGinkaku);
        vKinkaku.addItem("Gold Tamagane", new Item("A golden metal used for blade crafting", 3));

        // Tsushi : north -> Osaka, east -> Seki
        vTsushi.setExit("north", vOsaka);
        vTsushi.setExit("east", vSeki);
        vTsushi.addItem("Volcanic stone", new Item("A stone impregnated by the sea god's energy", 2));

        // Seki : west -> Tsushi
        vSeki.setExit("west", vTsushi);

        // Nara : east -> Yoshino
        vNara.setExit("east", vYoshino);
        vNara.addItem("Horn of the goddess", new Item("A horn given by the goddess of deers", 2));

        // Yoshino : east -> Nagoya
        vYoshino.setExit("east", vNagoya);
        vYoshino.addItem("Bamboo of holy forest", new Item("A rare type of bamboo which only exists here", 2));

        // Nagoya : up -> MtFuji, west -> Yoshino
        vNagoya.setExit("west", vYoshino);
        vNagoya.setExit("up", vMtFuji);

        // MtFuji : north -> Sapporo, east -> Tokyo, south -> Nagoya, west -> GujoHachi
        vMtFuji.setExit("down-north", vSapporo);
        vMtFuji.setExit("down-east", vTokyo);
        vMtFuji.setExit("down-south", vNagoya);
        vMtFuji.setExit("down-west", vGujoHachi);

        // Tokyo : west -> Nagoya
        vTokyo.setExit("west", vNagoya);

        // Sapporo : south -> Aogashi
        vSapporo.setExit("south", vAogashi);
        vSapporo.addItem("Ice'dragon's amulet",new Item("An amulet which freezes monsters and yokais", 5));


        // Aogashi : north -> Sapporo, south -> Tokyo
        vAogashi.setExit("north", vSapporo);
        vAogashi.setExit("south", vTokyo);
        vAogashi.addItem("Aogashima's salt", new Item("Holy salt used for purifying monsters", 1));
         this.aPlayer = new Player("Tetsuma");
        this.aPlayer.setCurrentRoom(vShirago);

    }

    /**
     * Print a sentence.
     */
    private void eat(){
        this.aGui.println("You have eaten now and you are not hungry any more.");
    }

    /**
     * Print out the description of the current room.
     */
    private void look(){
        this.aGui.println( this.aPlayer.getCurrentRoom().getLongDescription() );
        if ( this.aPlayer.getCurrentRoom().getImageName() != null )
            this.aGui.showImage( this.aPlayer.getCurrentRoom().getImageName() );
    }

    /**
     * Go back to the previous room
     */
    private void back(){
        Room vPreviousRoom = this.aPlayer.getPreviousRoom();
        if(vPreviousRoom == null){
            this.aGui.println("You can't go back any further !");
        }
        else{
            this.aPlayer.setCurrentRoom(vPreviousRoom);
            this.aGui.println( this.aPlayer.getCurrentRoom().getLongDescription() );
            if ( this.aPlayer.getCurrentRoom().getImageName() != null )
                this.aGui.showImage( this.aPlayer.getCurrentRoom().getImageName() );
        }
    }

    public void take(){

    }
    public void drop(){

    }


    /**
     * Process the command. Return true if the command ends the game, false otherwise.
     * @param pCmdLine The command to process.
     */
    public void interpretCommand(final String pCmdLine) {
        this.aGui.println( "> " + pCmdLine);
        Command pCmd = this.aParser.getCommand( pCmdLine );
        if (pCmd.isUnknown()) {
            this.aGui.println("I don't know what you mean...");
            return;
        }
        String vCommandWord = pCmd.getCommandWord();
        if ("help".equals(vCommandWord)) {
            this.printHelp();
        } else if ("go".equals(vCommandWord)) {
            this.goRooms(pCmd);
        } else if ("quit".equals(vCommandWord)) {
            if (pCmd.hasSecondWord()) {
                this.aGui.println("Quit what ?");
            } else {
                this.endGame();
            }

        } else if("look".equals(vCommandWord)){
            this.look();
        
        } else if ("eat".equals(vCommandWord)){
            this.eat();
        } else if ("back".equals(vCommandWord)){
            this.back();
        } else if("test".equals(vCommandWord)){
            this.testFile(pCmd);
        }
        else {
            System.out.println("Erreur du programmeur : commande non reconnue !");
            return;
        }
    }

    
    /**
     * Test text file reading
     */
    private void testFile (final Command pCommand){
        if(pCommand.hasSecondWord() == false){
            this.aGui.println("Error : you must specify a file name !");
            return;
        }
      
        String vFileName = pCommand.getSecondWord();
        vFileName+=".txt";
        //lire le fichier
        InputStream vInputStream = this.getClass().getClassLoader().getResourceAsStream(vFileName);
        Scanner vScanner = new Scanner(vInputStream);      
        if(vInputStream != null){
            this.aTestMode = true;
            while(vScanner.hasNextLine()){
                String vLigne = vScanner.nextLine();
                this.aGui.processCommand(vLigne);
            }
        }
        this.aTestMode = false;
        vScanner.close();
    }

   

    // implementations of user commands:

   /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the command words.
     */
    public void printHelp(){
        this.aGui.println("You are lost. You are alone.\r\n" + //
                        "You wander around Japan.\r\n" + //
                        "\r\n" + //
                        "Your command words are:\r\n" +  this.aParser.showCommands() //
                        );
       
    }
    /**
     * Go to the room in the given direction. If there is no room in that
     * direction, print an error message.
     * @param pCmd the command to process (should contain "go" and a direction).
     */
    private void goRooms(Command pCmd){
        if(!pCmd.hasSecondWord()){
            System.out.println("Go where ?");
            return;
        };
        String vDirection = pCmd.getSecondWord();
        Room vNextRoom = this.aPlayer.getCurrentRoom().getExit(vDirection);
        if(vNextRoom == null){
            this.aGui.println("There's no door");
        } else {
            this.aPlayer.addPreviousRoom(this.aPlayer.getCurrentRoom());
            this.aPlayer.setCurrentRoom(vNextRoom);
            this.aGui.println( this.aPlayer.getCurrentRoom().getLongDescription() );
            if (this.aPlayer.getCurrentRoom().getImageName() != null )
                this.aGui.showImage( this.aPlayer.getCurrentRoom().getImageName() );
            else{
                System.out.println("Image non trouvée");
            }
        }
    }


    /**
     * End the game
     */
    public void endGame(){
        this.aGui.println( "Thank you for playing.  Good bye." );
        this.aGui.enable( false );
    }

}

