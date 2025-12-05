package game;
import java.util.HashMap;
/**
 * Room class - a room in an adventure game.
 *
 * @author Christophe TARATIBU
 */
public class Room
{
    private String aDescription;
    private HashMap<String, Room> aExits; // les sorties de cette piece
    private String aImageName;
    private Items aRoomListItems;

    public Room(final String pDescription, final String pImageName){
        this.aDescription = pDescription;  
        this.aExits = new HashMap<String, Room>();
        this.aRoomListItems = new Items();
        this.aImageName = pImageName;
    }
    
    /**
     * Gives the description of the room and possible exits.
     * @return The description of the room and exits.
     */
    public String getDescription(){
        return this.aDescription;
    }

    public String getLongDescription(){
        String vDescription = "You are " + this.aDescription + ".\n" + this.getExitString();
        vDescription += " " + this.aRoomListItems.showItem();
        return vDescription;
        /*if(this.aItem != null){
            vDescription += "\n" + this.aItem.getItemDescription();
        } else {
            vDescription += "\nThere's no item in this room.";
        }
        return vDescription;*/
        
    }
    /**
     * Gives the room's exit in the given direction.
     * @param pDirection The direction of the exit.
     * @return The room in the given direction.
     */
    public Room getExit(String vDirection){
        return this.aExits.get(vDirection);
    }

    /**
     * Gives a string of all exits of the room.
     * @return A string of all exits of the room.
     */
    public String getExitString(){
        StringBuilder vReturnString = new StringBuilder(" Exits:");
        for (String vDirection : this.aExits.keySet() ) {
            vReturnString.append( " " + vDirection );
        }
        return vReturnString.toString();
    }

    
    /**
     * Set an item in the room
     * @param pItemDescription
     * @param pItemPoids
     */
    public void addItem(final String pName, final Item pItem){
        this.aRoomListItems.addItem(pName, pItem);
    }

    /**
     * Get the item in the room
     * @return The item in the room
     */
    public Item getItem(final String pName){
        Item vItem = this.aRoomListItems.getItem(pName);
        return vItem;
    }


    /**
     * Defines an exit of the room.
     * @param pDirection The direction of the exit.
     * @param pNeighbor The room in the given direction.
     */
    public void setExit(String pDirection, Room pNeighbor){
        this.aExits.put(pDirection, pNeighbor);
    }

    

    /**
     * Return a string describing the room's image name.
     * @return The image name of the room.
     */
    public String getImageName(){
       return this.aImageName;
    }
}