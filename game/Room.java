package game;
/**
 * Classe Room - un lieu du jeu d'aventure Zuul.
 *
 * @author Christophe TARATIBU
 */
public class Room
{
    private String aDescription;
    private Room aNorthExit;
    private Room aEastExit;
    private Room aSouthExit;
    private Room aWestExit;

    public Room(final String pDescription){
        this.aDescription = pDescription;  
    }
    
    public String getDescription(){
        return this.aDescription;
    }

    public Room getExit(String vDirection){
        switch (vDirection) {
            case "north":
                return this.aNorthExit;                   
            case "south":
                return this.aSouthExit;
            case "east":
                return this.aEastExit;
            case "west":
                return this.aWestExit;
            default:
                return null;
        }
    }

    public String getExitString(){
        String vStrExits = "";
        if(this.getExit("south") != null){
            vStrExits += "South ";
        }
        if(this.getExit("north") != null){
            vStrExits += "North";
        }
        if(this.getExit("east") != null){
            vStrExits += "East";
        }
        if(this.getExit("west")!= null){
            vStrExits += "West";
        }
        return vStrExits;
    }

    public void setExits(final Room pNorthExit,final Room pEastExit, final Room pSouthExit, final Room pWestExit){
        this.aNorthExit = pNorthExit;
        this.aEastExit = pEastExit;
        this.aSouthExit = pSouthExit;
        this.aWestExit = pWestExit;
    }
} // Room
