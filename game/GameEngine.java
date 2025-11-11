package game;

/**
 *  This class is part of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.
 * 
 *  This class creates all rooms, creates the parser and starts
 *  the game.  It also evaluates and executes the commands that 
 *  the parser returns.
 * 
 * @author  Michael Kolling and David J. Barnes
 * @version 1.0 (Jan 2003) DB edited (2019)
 */
public class GameEngine
{
    private Parser        aParser;
    private Room          aCurrentRoom;
    private UserInterface aGui;

    /**
     * Constructor for objects of class GameEngine
     */
    public GameEngine()
    {
        this.aParser = new Parser();
        this.createRooms();
    }

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
        this.aGui.println( "Welcome to the World of Zuul!" );
        this.aGui.println( "World of Zuul is a new, incredibly boring adventure game." );
        this.aGui.println( "Type 'help' if you need help." );
        this.aGui.print( "\n" );
        this.aGui.println( this.aCurrentRoom.getLongDescription() );
        if ( this.aCurrentRoom.getImageName() != null )
            this.aGui.showImage( this.aCurrentRoom.getImageName() );
    }

   /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms(){
        Room vShirago = new Room("in your village of birth, Shirakawa-go","./images/shirakawa.jpg");
        Room vGujoHachi = new Room("In the Gujô Hachiman castle","./images/gujohachi.jpg");
        Room vOsaka = new Room("in the Osaka city","./images/Osaka.jpg");
        Room vTsushi = new Room("in the Tsushima island","./images/Tsushima.jpg");
        Room vSeki = new Room("in the Seki blacksmiths village, it's the end of your odyssey","./images/seki.jpg");
        Room vNara = new Room("in the Nara city known for its deers,","./images/Nara_fire.jpg");
        Room vGinkaku = new Room("in the Ginkaku-ji temple","./images/Ginkakuji_fall_2.jpg");
        Room vKinkaku = new Room("in the Kinkaku-ji temple","./images/Kinkaku.jpg");
        Room vYoshino = new Room("in the Yoshino national park, the forest is big here","./images/Yoshino_park.jpg");
        Room vNagoya  = new Room("in the Nagoya city","./images/Nagoya.jpg");
        Room vMtFuji = new Room("at the foot of the Mt Fuji","./images/MtFuji.jpg");
        Room vTokyo = new Room("in the Tokyo tower, the biggest tower of Japan","./images/tokyo_tower.jpg");
        Room vSapporo = new Room("in the Sapporo city, in the north of Japan","./images/sapporo_snow.jpg");
        Room vAogashi = new Room("in the island of Aogashima, in the middle of the ocean","./images/Aogashima.jpg");
        
        
        this.aCurrentRoom = vShirago;  // la partie commence a Shirakawa-go
        vShirago.setExit("south", vGujoHachi);

        // GujoHachi : east -> MtFuji, west -> Osaka
        vGujoHachi.setExit("east", vMtFuji);
        vGujoHachi.setExit("west", vOsaka);

        // Osaka : north -> Ginkaku, east -> GujoHachi, south -> Nara, west -> Tsushi
        vOsaka.setExit("north", vGinkaku);
        vOsaka.setExit("east", vGujoHachi);
        vOsaka.setExit("south", vNara);
        vOsaka.setExit("west", vTsushi);

        // Ginkaku : east -> Kinkaku, south -> Osaka
        vGinkaku.setExit("east", vKinkaku);
        vGinkaku.setExit("south", vOsaka);

        // Kinkaku : south -> Ginkaku
        vKinkaku.setExit("south", vGinkaku);

        // Tsushi : north -> Osaka, east -> Seki
        vTsushi.setExit("north", vOsaka);
        vTsushi.setExit("east", vSeki);

        // Seki : west -> Tsushi
        vSeki.setExit("west", vTsushi);

        // Nara : east -> Yoshino
        vNara.setExit("east", vYoshino);

        // Yoshino : east -> Nagoya
        vYoshino.setExit("east", vNagoya);

        // Nagoya : north -> MtFuji, west -> Yoshino
        vNagoya.setExit("north", vMtFuji);
        vNagoya.setExit("west", vYoshino);

        // MtFuji : north -> Sapporo, east -> Tokyo, south -> Nagoya, west -> GujoHachi
        vMtFuji.setExit("north", vSapporo);
        vMtFuji.setExit("east", vTokyo);
        vMtFuji.setExit("south", vNagoya);
        vMtFuji.setExit("west", vGujoHachi);

        // Tokyo : west -> Nagoya
        vTokyo.setExit("west", vNagoya);

        // Sapporo : south -> Aogashi
        vSapporo.setExit("south", vAogashi);

        // Aogashi : north -> Sapporo, south -> Tokyo
        vAogashi.setExit("north", vSapporo);
        vAogashi.setExit("south", vTokyo);
    }

    /**
     * Print a sentence.
     */
    private void eat(){
        System.out.println("You have eaten now and you are not hungry any more.");
    }

    /**
     * Print out the description of the current room.
     */
    private void look(){
        System.out.println(this.aCurrentRoom.getLongDescription()); 
    }

    /**
     * Process the command. Return true if the command ends the game, false otherwise.
     * @param pCmd The command to process.
     * @return true if the command make the game working, false otherwise.
     */
    public void processCommand(final String pCmdLine) {
        this.aGui.println( "> " + pCmdLine);
        Command pCmd = this.aParser.getCommand( pCmdLine );
        if (pCmd.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return;
        }
        String vCommandWord = pCmd.getCommandWord();
        if ("help".equals(vCommandWord)) {
            this.printHelp();
        } else if ("go".equals(vCommandWord)) {
            this.goRooms(pCmd);
        } else if ("quit".equals(vCommandWord)) {
            if (pCmd.hasSecondWord()) {
                System.out.println("Quit what ?");
            } else {
                this.endGame();
            }

        } else if("look".equals(vCommandWord)){
            this.look();
        
        } else if ("eat".equals(vCommandWord)){
            this.eat();
        } else {
            System.out.println("Erreur du programmeur : commande non reconnue !");
            return;
        }
    }

    // implementations of user commands:

   /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the command words.
     */
    public void printHelp(){
        System.out.println("You are lost. You are alone.\r\n" + //
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
        Room vNextRoom = this.aCurrentRoom.getExit(vDirection);
        if(vNextRoom == null){
            System.out.println("There's no door");
        } else {
            this.aCurrentRoom = vNextRoom;
            //this.printLocationInfo();
            this.aGui.println( this.aCurrentRoom.getLongDescription() );
            if ( this.aCurrentRoom.getImageName() != null )
                this.aGui.showImage( this.aCurrentRoom.getImageName() );
            else{
                System.out.println("Image non trouvée");
            }
        }
    }


    private void endGame(){
        this.aGui.println( "Thank you for playing.  Good bye." );
        this.aGui.enable( false );
    }

}

