package game;

import java.util.Stack;

/**
 * Player class - a player in an adventure game.
 *
 * @author Christophe TARATIBU
 */
public class Player {
    private Room aCurrentRoom;
    private final ItemList aPlayerItems;
    private int aMaxWeight;
    private final Stack<Room> aPreviouRooms;
    private int aCurrentWeight;
    private final String aName;
    private Room aPlantedRoom;
    /**
    * Initialization of a player
    * @param pName The name of the player
    */
    public Player(final String pName){
        this.aName = pName;
        this.aPlayerItems = new ItemList();
        this.aPreviouRooms = new Stack<>();
        this.aCurrentWeight = 0;
        this.aMaxWeight = 8;
        //this.aNumberOfMoves = 0;
        this.aCurrentRoom = null;
        this.aPlantedRoom = null;
    }

    /**
     * Gives the name of the player
     * @return The name of the player
     */
    public String getName(){
        return this.aName;
    }
    /**
     * Adds a previous room to the stack of previous rooms
     * @param pRoom The previous room to add
     */
    public void addPreviousRoom(final Room pRoom){
        this.aPreviouRooms.push(pRoom);
    }
    /**
     * Sets the current room of the player
     * @param pRoom The current room of the player
     */
    public void setCurrentRoom(final Room pRoom){
        this.aCurrentRoom = pRoom;
    }   
    /**
     * Gives the current room of the player
     * @return The current room of the player
     */
    public Room getCurrentRoom(){
        return this.aCurrentRoom;
    }
    

    /**
     * Gives the previous room of the player
     * @return The previous room of the player
     */
    public Room getPreviousRoom(){
        if(!this.aPreviouRooms.isEmpty()){
            return this.aPreviouRooms.pop();
        }
        else{
            return null;
        }
    }

    /**
     * Sets the current weight of the player
     * @param pWeight The current weight of the player
     */
    public void setWeight(int pWeight){
        this.aCurrentWeight = pWeight;
    }

    /**
     * Gives the current weight of the player
     * @return The current weight of the player
     */
    public int getWeight(){
        return this.aCurrentWeight;
    }

    /**
     * Gives the maximum weight the player can carry
     * @return The maximum weight the player can carry
     */
    public int getMaxWeight(){
        return this.aMaxWeight;
    }

    /**
     * Get an item by its name
     * @param pName Name of the item
     * @return An item
     */
    public Item getItem(final String pName){
        return this.aPlayerItems.getItem(pName);
    }

    /**
     * Add an item in the player's item list
     * @param pName Name of the item
     * @param pItem Item
     */
    public void addItem(final String pName, final Item pItem){
        this.aPlayerItems.addItem(pName, pItem);
    }

    /**
     * Remove an item by its name
     * @param pName Name of the item
     */
    public void removeItem(final String pName){
        this.aPlayerItems.removeItem(pName);
    }

    /**
     * List item names in player's inventory
     * @return array of item names
     */
    public String[] getInventoryItemNames(){
        return this.aPlayerItems.getItemNames();
    }
    /**
     * Whether the player's inventory is empty
     * @return true if empty
     */
    public boolean isInventoryEmpty(){
        return this.aPlayerItems.isEmpty();
    }

    /**
     * Activate super power to double the max weight
     */
    public void superPower(){
        this.aMaxWeight *= 2;
    } 

    /**
     * Empty the player's inventory
     */
    public void emptyInventory(){
        this.aPlayerItems.getItemsMap().clear();
    }

    /**
     * Empty the previous rooms stack
     */
    public void emptyPreviousRooms(){
        this.aPreviouRooms.clear();
    }
    

    /**
     * Get all items in player's inventory
     * @return A string listing all items
     */
    public String getItems(){
        return "Items: "  + String.join(", ", this.aPlayerItems.getItemNames());
    }

    /**
     * Get the planted room
     * @return The planted room
     */
    public Room getPlantedRoom(){
        return this.aPlantedRoom;
    }

    /**
     * Set the planted room
     * @param pRoom The planted room
     */
    public void setPlantedRoom(final Room pRoom){
        this.aPlantedRoom = pRoom;
    }

    /**
     * reset the planted room to null
     */
    public void resetPlantedRoom(){
        this.aPlantedRoom = null;
    }
    
}
