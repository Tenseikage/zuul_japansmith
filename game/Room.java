package game;
import java.util.List;
import java.util.HashMap;
/**
 * Room class - a room in an adventure game.
 * @author Christophe TARATIBU
 * @version 2025/2026 (09.01.2026)
 */
public class Room
{
    /** Description of the room */
    private final String aDescription;
    /** Possible exists of the room */
    private final HashMap<String, Room> aExits; 
    /** Trap doors */
    private final HashMap<String, Room> aTrapDoors; 
    /** Name of the images (used to show rooms) */
    private final String aImageName;
    /** List of present items in the room */
    private ItemList aRoomListItems;
    /** Name of the room */
    private final String aRoomName;

    /**
     * Initialization of a room
     * @param pDescription Room's description
     * @param pImageName An image used to show the room
     * @param pRoomName The name of the room
     */
    public Room(final String pDescription, final String pImageName, final String pRoomName){
        this.aDescription = pDescription;  
        this.aExits = new HashMap<String, Room>();
        this.aTrapDoors = new HashMap<String, Room>();
        this.aRoomListItems = new ItemList();
        this.aImageName = pImageName;
        this.aRoomName = pRoomName;
    }
    
    /**
     * Gives the description of the room and possible exits.
     * @return The description of the room and exits.
     */
    public String getDescription(){
        return this.aDescription;
    }

    /**
     * Gives a long description of this room
     * @return Description of the room with exits
     */
    public String getLongDescription(){
        String vDescription = "You are " + this.aDescription + ".\n" + this.getExitString();
        vDescription += " " + this.aRoomListItems.showItem();
        return vDescription;
        
    }
    
    /**
     * Gives the name of the room
     * @return The name of the room
     */
    public String getRoomName(){
        return this.aRoomName;
    }
    /**
     * Gives the room's exit in the given direction.
     * @param pDirection The direction of the exit.
     * @return The room in the given direction.
     */
    public Room getExit(String pDirection){
        Room vExit = this.aExits.get(pDirection);
        if(vExit == null){
            vExit = this.aTrapDoors.get(pDirection);
        }
        return vExit;
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
        for (String vDirection : this.aTrapDoors.keySet() ) {
            vReturnString.append( " " + vDirection );
        }
        return vReturnString.toString();
    }

    
    /**
     * Add an item in the room
     * @param pName Name of the item
     * @param pItem Item
     */
    public void addItem(final String pName, final Item pItem){
        this.aRoomListItems.addItem(pName, pItem);
    }

    /**
     * Get the item in the room
     * @param pName Name of the item    
     * @return The item in the room
     */
    public Item getItem(final String pName){
        Item vItem = this.aRoomListItems.getItem(pName);
        //System.out.println(vItem.getItemName());
        return vItem;
    }


    /**
     * Define an exit for the room
     * @param pDirection Direction of the exit
     * @param pNeighbor Room in the given direction
     */
    public void setExit(String pDirection, Room pNeighbor){
        this.aExits.put(pDirection, pNeighbor);
    }

    /**
     * Define a one-way trap door exit for the room
     * @param pDirection Direction of the exit
     * @param pNeighbor Room in the given direction
     */
    public void setTrapDoorExit(String pDirection, Room pNeighbor){
        this.aTrapDoors.put(pDirection, pNeighbor);
    }

    /**
     * Remove an item from the room
     * @param pName Name of the item
     */
    public void removeItem(final String pName){
        this.aRoomListItems.removeItem(pName);
    }

    /**
     * Return a string describing the room's image name.
     * @return The image name of the room.
     */
    public String getImageName(){
       return this.aImageName;
    }

    /**
     * Get all item names present in this room
     * @return array of item names
     */
    public List<String> getItemNames(){
        return this.aRoomListItems.getItemNames();
    }

    /**
     * Whether the room currently has no items
     * @return true if no items
     */
    public boolean hasNoItems(){
        return this.aRoomListItems.isEmpty();
    }

    /**
     * Check if a room is an exit from this room
     * @param pRoom Room to check
     * @return true if the room is an exit
     */
    public boolean isExit(final Room pRoom){
        return this.aTrapDoors.containsValue(pRoom);
    }
}