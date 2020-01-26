import java.util.ArrayList;
/**
 * class items - geef hier een beschrijving van deze class
 *
 * @author (jouw naam)
 * @version (versie nummer of datum)
 */
public class Item
{
    private String description;
    private int weight;
    private String name; 
    /**
     * constructor for creating item object. Items have 3 fields: name, description and weight.
     */
    public Item(String newName, String newDescription, int newWeight)
    {
        description = newDescription;
        weight      = newWeight;
        name        = newName;
    }

    /**
     * @return description of an item
     */
    public String getItemDescription()
    {
        return description;
    }

    /**
     * @return the weight of an item
     */
    public int getItemWeight()
    {
        return weight;
        
    }

    /**
     * @return the name of an item
     */
    public String getItemName()
    {
        return name;    
    }
}

