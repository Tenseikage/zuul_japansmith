package game;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.Timer;

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
    // Delay between test commands (ms) to let UI refresh images
    private static final int TEST_DELAY_MS = 2000;
    // Global game duration (ms) before automatic loss
    private static final int GAME_DURATION_MS = 6000; // 10 minutes

    private final Parser        aParser;
    //private Room          aCurrentRoom;
    //private Stack<Room> aPreviouRooms;
    //private LinkedList<Room> aLastRooms;
    private UserInterface aGui;
    private boolean aTestMode;
    private Player aPlayer;
    private Timer aGameTimer;
    private Timer aCountdownTimer;
    private long aGameStartMillis;

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
        this.startGameTimer();
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
        vOsaka.addItem("Anko mochi", new Item("A mochi with anko flavour of Osaka", 2));

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
    private void eat(final Command pCmd){
        if(!pCmd.hasSecondWord()){
            this.aGui.println("Eat what ?");
            return;
        }
        String vItemName = pCmd.getSecondWord();
        Item vItem = this.aPlayer.getItem(vItemName);
        if(vItem == null){
            this.aGui.println("You don't have this item !");
        } 
        if (!vItemName.equals("Anko Mochi")){
            this.aGui.println("You can't eat this item !");
            return;

        }
        this.aPlayer.superPower();
        this.aPlayer.removeItem(vItemName);
        this.aPlayer.setWeight(this.aPlayer.getWeight() - vItem.getItemWeight());
        this.aGui.println("You have eaten the Anko Mochi ! Your maximum weight has been doubled !");
        return;
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

    /**
     * Start (or restart) the global game timer. When it fires, the player loses.
     */
    private void startGameTimer(){
        if (this.aGameTimer != null){
            this.aGameTimer.stop();
        }
        if (this.aCountdownTimer != null){
            this.aCountdownTimer.stop();
        }
        this.aGameStartMillis = System.currentTimeMillis();

        // countdown display each second
        this.aCountdownTimer = new Timer(1000, e -> {
            long vRemaining = this.remainingMillis();
            if (vRemaining <= 0){
                this.aCountdownTimer.stop();
                // Let the main timer handle loss, just show 00:00 now
                this.aGui.updateTimerLabel("00:00");
            } else {
                this.aGui.updateTimerLabel(this.formatMillis(vRemaining));
            }
        });
        this.aCountdownTimer.setRepeats(true);
        this.aCountdownTimer.start();

        this.aGameTimer = new Timer(GAME_DURATION_MS, e -> {
            this.aGui.println("Time is up! You lose.");
            this.endGame();
        });
        this.aGameTimer.setRepeats(false);
        this.aGameTimer.start();

        // initialise display immediately
        this.aGui.updateTimerLabel(this.formatMillis(GAME_DURATION_MS));
    }

    /**
     * Calculate remaining milliseconds before time is up.
     * @return Remaining milliseconds
     */
    private long remainingMillis(){
        long vElapsed = System.currentTimeMillis() - this.aGameStartMillis;
        return Math.max(0, GAME_DURATION_MS - vElapsed);
    }

    /**
     * Format milliseconds as mm:ss string.
     * @param pMillis Milliseconds to format
     * @return Formatted string
     */
    private String formatMillis(final long pMillis){
        long vTotalSeconds = pMillis / 1000;
        long vMinutes = vTotalSeconds / 60;
        long vSeconds = vTotalSeconds % 60;
        return String.format("%02d:%02d", vMinutes, vSeconds);
    }

    /**
     * Take an item from the room
     * @param pCmd The command to process
     */
    public void take(final Command pCmd){
        if(!pCmd.hasSecondWord()){
            this.aGui.println("Take what ?");
            return;
        }
        String vItemName = pCmd.getSecondWord();
        Item vItem = this.aPlayer.getCurrentRoom().getItem(vItemName);
        if(vItem == null){
            this.aGui.println("This item is not here !");
        } else{
            int vNewWeight = this.aPlayer.getWeight() + vItem.getItemWeight();
            if(vNewWeight > this.aPlayer.getMaxWeight()){
                this.aGui.println("You can't take this item, it's too heavy !");
            } else{
                this.aPlayer.addItem(vItemName, vItem);
                this.aPlayer.setWeight(vNewWeight);
                this.aPlayer.getCurrentRoom().removeItem(vItemName);
                this.aGui.println("You have taken the " + vItemName);
            }
        }
    }

    /**
     * Drop an item in the room
     * @param pCmd The command to process
     */
    public void drop(final Command pCmd){
        if(!pCmd.hasSecondWord()){
            this.aGui.println("Drop what ?");
            return;
        }
        String vItemName = pCmd.getSecondWord();
        Item vItem = this.aPlayer.getItem(vItemName);
        if(vItem == null){
            this.aGui.println("You don't have this item !");
        } else{
            this.aPlayer.removeItem(vItemName);
            int vNewWeight = this.aPlayer.getWeight() - vItem.getItemWeight();
            this.aPlayer.setWeight(vNewWeight);
            this.aPlayer.getCurrentRoom().addItem(vItemName, vItem);
            this.aGui.println("You have dropped the " + vItemName);
        }
    }
    /**
     * Show player's inventory
     */
    private void playerInventory(){
        this.aGui.println("You have : \n" + this.aPlayer.getItems());
        this.aGui.println("Current weight : " + this.aPlayer.getWeight() + " / " + this.aPlayer.getMaxWeight());
       
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
            this.eat(pCmd);
        } else if ("back".equals(vCommandWord)){
            this.back();
        } else if("test".equals(vCommandWord)){
            this.testFile(pCmd);
        } else if("take".equals(vCommandWord)){
            this.take(pCmd);
        } else if("drop".equals(vCommandWord)){
            this.drop(pCmd);
        } else if("items".equals(vCommandWord)){
            this.playerInventory();
        }
        else {
            System.out.println("Erreur du programmeur : commande non reconnue !");
            return;
        }
    }

    
    /**
     * Launch a test file
     * @param pCommand The command to process
     */
    private void testFile (final Command pCommand){
        if (this.aTestMode) {
            this.aGui.println("A test is already running, please wait.");
            return;
        }
        if(pCommand.hasSecondWord() == false){
            this.aGui.println("Error : you must specify a file name !");
            return;
        }
      
        String vFileName = pCommand.getSecondWord();
        System.out.println("Test file : " + vFileName);
        vFileName+=".txt";
        System.out.println("Full file name : " + vFileName);
        InputStream vInputStream = this.getClass().getClassLoader().getResourceAsStream(vFileName);
        if (vInputStream == null){
            this.aGui.println("Error : file not found !");
            return;
        }

        List<String> vCommands = new ArrayList<>();
        try (Scanner vScanner = new Scanner(vInputStream)){
            while (vScanner.hasNextLine()){
                String vLine = vScanner.nextLine().trim();
                // Ignore empty lines and comments (starting with // or #)
                if (!vLine.isEmpty() && !vLine.startsWith("//") && !vLine.startsWith("#")){
                    vCommands.add(vLine);
                }
            }
        }

        if (vCommands.isEmpty()){
            this.aGui.println("The test file is empty or only contains comments.");
            return;
        }

        this.aTestMode = true;
        final int[] vIndex = {0};
        final Timer vTimer = new Timer(TEST_DELAY_MS, null);
        vTimer.addActionListener(ev -> {
            if (vIndex[0] < vCommands.size()){
                String vNext = vCommands.get(vIndex[0]++);
                this.interpretCommand(vNext);
            } else {
                vTimer.stop();
                this.aTestMode = false;
                this.aGui.println("Test finished.");
            }
        });
        vTimer.setRepeats(true);
        vTimer.start();
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
            if (this.aPlayer.getCurrentRoom().getImageName() != null ){
                this.aGui.showImage( this.aPlayer.getCurrentRoom().getImageName() );
                System.out.println("Image affichée");
            } else{
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
        if (this.aGameTimer != null){
            this.aGameTimer.stop();
        }
        if (this.aCountdownTimer != null){
            this.aCountdownTimer.stop();
        }
    }

    /**
     * Get item names present in the current room (for UI selection)
     * @return array of item names; empty array if none
     */
    public String[] getCurrentRoomItemNames(){
        return this.aPlayer.getCurrentRoom().getItemNames();
    }

    /**
     * Get item names present in player's inventory (for UI selection)
     * @return array of item names; empty array if none
     */
    public String[] getPlayerItemNames(){
        return this.aPlayer.getInventoryItemNames();
    }

}

