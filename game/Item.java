package game;

/**
 * This class creates all items used in the world of Zuul
 * @author TARATIBU Christophe
 */
public class Item {
    private String aItemDescription;
    private int aItemPoids;

    /**
     * Constructor for objects of class Item
     * @param pItemDescription
     * @param pItemPoids
     */
    public Item(final String pItemDescription, final int pItemPoids){
        this.aItemDescription = pItemDescription;
        this.aItemPoids = pItemPoids;
       
    }
    
    /**
     * Get the item weight
     * @return Weight of the item
     */
    public int getItemPoids(){
        return this.aItemPoids;
    }

    /**
     * Get the item name
     * @return Name of the item
     */
    public String getItemName(){
        return this.aItemDescription;
    }


    /**
     * Get the item description
     * @return Description of the item
     */
    public String getItemDescription(){
        return "There is " + this.aItemDescription + "Item weight : " + this.aItemPoids;
    }
}
