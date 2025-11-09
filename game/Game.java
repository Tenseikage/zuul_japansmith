package game;
/**
 * Game class - the main class of the "World of Zuul" application.
 *
 * @author TARATIBU Christophe
 */
public class Game
{ 
    private Room aCurrentRoom;
    private Parser aParser;

    
    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms(){
        Room vShirago = new Room("in your village of birth, Shirakawa-go");
        Room vGujoHachi = new Room("In the Gujô Hachiman castle");
        Room vOsaka = new Room("in the Osaka city");
        Room vTsushi = new Room("in the Tsushima island");
        Room vSeki = new Room("in the Seki blacksmiths village, it's the end of your odyssey");
        Room vNara = new Room("in the Nara city known for its deers");
        Room vGinkaku = new Room("in the Ginkaku-ji temple");
        Room vKinkaku = new Room("in the Kinkaku-ji temple");
        Room vYoshino = new Room("in the Yoshino national park, the forest is big here");
        Room vNagoya  = new Room("in the Nagoya city");
        Room vMtFuji = new Room("at the foot of the Mt Fuji");
        Room vTokyo = new Room("in the Tokyo city, the capital of Japan");
        Room vSapporo = new Room("in the Sapporo city, in the north of Japan");
        Room vAogashi = new Room("in the island of Aogashima, in the middle of the ocean");
        
        
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
     * Print out the description of the current room and its exits.
     */
    private void printLocationInfo(){
        System.out.println(this.aCurrentRoom.getLongDescription());
    }

    /**
     * Print out the description of the current room.
     */
    private void look(){
        System.out.println(this.aCurrentRoom.getLongDescription()); 
    }

    /**
     * Print a sentence.
     */
    private void eat(){
        System.out.println("You have eaten now and you are not hungry any more.");
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
            this.printLocationInfo();
        }
    }

    /**
     * Print out the opening message for the player.
     */
    public void welcome(){
        System.out.println("Welcome to the World of Zuul : The return of Yamata no Orochi! \nWorld of Zuul is a new, incredibly boring adventure game." +
        "\nType 'help' if you need help");
        System.out.println();
        this.printLocationInfo();

    }

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
     * Quit the game. Return true if this command quits the game, false otherwise.
     * @param pCmd Command to process
     * @return boolean if the command quits the game
     */
    private boolean quit(final Command pCmd){
        if (pCmd.hasSecondWord()) {
            System.out.println("Quit what ?");
            return false;
        }
        return true;
    }
    
    /**
     * Process the command. Return true if the command ends the game, false otherwise.
     * @param pCmd The command to process.
     * @return true if the command make the game working, false otherwise.
     */
    private boolean processCommand(final Command pCmd) {
        if (pCmd.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }
        String vCommandWord = pCmd.getCommandWord();
        if ("help".equals(vCommandWord)) {
            printHelp();
        } else if ("go".equals(vCommandWord)) {
            goRooms(pCmd);
        } else if ("quit".equals(vCommandWord)) {
            return quit(pCmd);

        } else if("look".equals(vCommandWord)){
            this.look();
        
        } else if ("eat".equals(vCommandWord)){
            this.eat();
        } else {
            System.out.println("Erreur du programmeur : commande non reconnue !");
            return true;
        }
        return false;
    }

    /**
     * Play the game. The main loop.  Repeatedly get commands and
     * execute them until the game is over.
     */
    public void play() {
        this.welcome();
        boolean vFinished = false;
        while (!vFinished) {
            Command vCommand = this.aParser.getCommand();
            vFinished = this.processCommand(vCommand);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }
    /**
     * Main method to start the game.
     * @param args No arguments are taken into account.
     */
    public Game(){
        this.aParser = new Parser();
        this.createRooms();
        play();
        
    }
    /*public static void main(final String[] args){
        Game vGame = new Game();
        vGame.play();
    }*/
    
} // Game
