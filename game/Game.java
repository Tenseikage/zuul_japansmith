package game;
/**
 * Classe Game - le moteur du jeu d'aventure Zuul.
 *
 * @author TARATIBU Christophe
 */
public class Game
{ 
    private Room aCurrentRoom;
    private Parser aParser;

    

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
        vShirago.setExits(null,null,vGujoHachi,null);
        vGujoHachi.setExits(null,vMtFuji,null,vOsaka);
        vOsaka.setExits(vGinkaku,vGujoHachi,vNara,vTsushi);
        vGinkaku.setExits(null,vKinkaku,vOsaka,null);
        vKinkaku.setExits(null,null,vGinkaku,null);
        vTsushi.setExits(vOsaka,vSeki,null,null);
        vSeki.setExits(null,null,null,vTsushi);
        vNara.setExits(null,vYoshino,null,null);
        vYoshino.setExits(null,vNagoya,null,null);
        vNagoya.setExits(vMtFuji,null,null,vYoshino);
        vMtFuji.setExits(vSapporo,vTokyo,vNagoya,vGujoHachi);
        vTokyo.setExits(null,null,null,vNagoya);
        vSapporo.setExits(null,null,vAogashi,null);
        vAogashi.setExits(vSapporo,null,vTokyo,null);
    }
    private void printLocationInfo(){
        System.out.println("You are " + this.aCurrentRoom.getDescription());
        System.out.println("Possible exits");
        if (this.aCurrentRoom.getExit("east") != null) {
            System.out.println("- East");
        }
        if (this.aCurrentRoom.getExit("west") != null) {
            System.out.println("- West");
        }
        if (this.aCurrentRoom.getExit("north") != null) {
            System.out.println("- North");
        }
        if (this.aCurrentRoom.getExit("south") != null) {
            System.out.println("- South");
        }

    }
    private void goRooms(Command pCmd){
        if(!pCmd.hasSecondWord()){
            System.out.println("Go where ?");
        }
        Room vNextRoom = null;
        String vDirection = pCmd.getSecondWord();
        System.out.println(vDirection);
        switch (vDirection) {
            case "north":
                vNextRoom = this.aCurrentRoom.getExit(vDirection);
                break;
        
            case "south":
                vNextRoom = this.aCurrentRoom.getExit(vDirection);
                break;

            case "east":
                vNextRoom = this.aCurrentRoom.getExit(vDirection);
                break;
            case "west":
                vNextRoom = this.aCurrentRoom.getExit(vDirection);
                break;
            default:
                System.out.println("Unknown direction");
                break;
        }
        if(vNextRoom == null){
            System.out.println("There's no door");
        } else {
            this.aCurrentRoom = vNextRoom;
            this.printLocationInfo();
        }
    }

    public void welcome(){
        System.out.println("Welcome to the World of Zuul! \nWorld of Zuul is a new, incredibly boring adventure game." +
        "\nType 'help' if you need help");
        System.out.println();
        this.printLocationInfo();

    }

    public void printHelp(){
        System.out.println("You are lost. You are alone.\r\n" + //
                        "You wander around Japan.\r\n" + //
                        "\r\n" + //
                        "Your command words are:\r\n" + //
                        "  go quit help");
    }

    private boolean quit(final Command pCmd){
        if (pCmd.hasSecondWord()) {
            System.out.println("Quit what ?");
            return false;
        }
        return true;
    }
    
    private boolean processCommand(final Command pCmd) {
        if (pCmd.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String vCommandWord = pCmd.getCommandWord();
        if ("help".equals(vCommandWord )) {
            printHelp();
            return false;
        } else if ("go".equals(vCommandWord)) {
            goRooms(pCmd);
            return false;
        } else if ("quit".equals(vCommandWord)) {
            return quit(pCmd);
        } else {
            System.out.println("Erreur du programmeur : commande non reconnue !");
            return true;
        }
    }

    public void play() {
        this.welcome();
        boolean vFinished = false;
        while (!vFinished) {
            Command vCommand = this.aParser.getCommand();
            vFinished = this.processCommand(vCommand);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }
    public Game(){
        this.aParser = new Parser();
        this.createRooms();
        play();
    }
} // Game
