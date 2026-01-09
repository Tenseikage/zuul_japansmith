package game;

/**
 * This class creates all items used in the world of Zuul
 * @author Christophe TARATIBU (2025/2026)
 * @version 2025/2026 (09.01.2026)
 */
public class Item {
    /** Description of the item */
    private final String aItemDescription;
    /** Weight of the item */
    private final int aItemWeight;

    /**
     * Constructor for objects of class Item
     * @param pItemDescription Description of the item
     * @param pItemPoids Weight of the item             
     */
    public Item(final String pItemDescription, final int pItemPoids){
        this.aItemDescription = pItemDescription;
        this.aItemWeight = pItemPoids;
       
    }
    
    /**
     * Get the item weight
     * @return Weight of the item
     */
    public int getItemWeight(){
        return this.aItemWeight;
    }

    /**
     * Get the item description
     * @return Name of the item
     */
    public String getItemDescription(){
        return this.aItemDescription;
    }
    
}
