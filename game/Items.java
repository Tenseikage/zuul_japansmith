package game;

import java.util.HashMap;


/**
 *  Items class which manages a collection of items.
 *  @author Christophe TARATIBU
 */
public class Items {
    private HashMap<String,Item> aListItems;

    public Items(){
        this.aListItems = new HashMap<>();
    }

    public Item getItem(final String pName){
        return this.aListItems.get(pName);
    }

    public void removeItem(final String pName){
        this.aListItems.remove(pName);
    }

    public void addItem(final String pName, final Item pItem){
        this.aListItems.put(pName, pItem);
    }

    public String showItem(){
        if(this.aListItems.isEmpty()){
            //System.out.println("yoss!");
            return "\nThere are no items in this room";
        }
        return "\nItems : " + String.join(", ", this.aListItems.keySet());

    }
}
