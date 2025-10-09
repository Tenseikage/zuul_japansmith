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
        Room vOutside = new Room("outside the main entrance of the university");
        Room vTheatre = new Room("in a lecture theatre");
        Room vPub = new Room("in the campus pub");
        Room vLab = new Room("in a computing lab");
        Room vOffice = new Room("in the computing admin office");

        vOutside.setExits(null,vTheatre,vLab,vPub);
        vTheatre.setExits(null,null,null, vOutside);
        vPub.setExits(null,vOutside,null,null);
        vLab.setExits(vOutside,vOffice,null,null);
        vOffice.setExits(null,null,null,vLab);
        
        this.aCurrentRoom = vOutside;
    }

    private void printLocationInfo(){
        System.out.println(" You are " + this.aCurrentRoom.getDescription());
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
        System.out .println( "You are"+ this.aCurrentRoom.getDescription());
        this.printLocationInfo();

    }

    public void printHelp(){
        System.out.println("You are lost. You are alone.\r\n" + //
                        "You wander around at the university.\r\n" + //
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
