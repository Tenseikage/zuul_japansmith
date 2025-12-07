package game;

import java.util.HashMap;


/**
 *  Items class which manages a collection of items.
 *  @author Christophe TARATIBU
 */
public class Items {
    private HashMap<String,Item> aListItems;

    /**
     *  Initialization of the item list
     */
    public Items(){
        this.aListItems = new HashMap<>();
    }

    /**
     * Get an item by its name
     * @param pName Name of the item
     * @return An item
     */
    public Item getItem(final String pName){
        return this.aListItems.get(pName);
    }

    /**
     * Remove an item by its name
     * @param pName Name of the item
     */
    public void removeItem(final String pName){
        this.aListItems.remove(pName);
    }

    /**
     * Add an item in the item list
     * @param pName Name of the item
     * @param pItem Item
     */
    public void addItem(final String pName, final Item pItem){
        this.aListItems.put(pName, pItem);
    }

    /**
     * Show all items in the item list
     * @return A string listing all items
     */
    public String showItem(){
        if(this.aListItems.isEmpty()){
            //System.out.println("yoss!");
            return "\nThere are no items in this room";
        }
        return "\nItems : " + String.join(", ", this.aListItems.keySet());

    }
}
