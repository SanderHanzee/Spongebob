import java.util.Set;
import java.util.HashMap;
import java.util.ArrayList; 
import java.util.Iterator;  
/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "Spongebob adventure" application. 
 * "Spongebob adventure" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  The exits are labelled north, 
 * east, south, west.  For each direction, the room stores a reference
 * to the neighboring room, or null if there is no exit in that direction.
 * 
 * @author  Sander Brands and Kevin de Graaf
 * @version 2020-01-26
 */
public class Room 
{
    public String description;
    private HashMap<String, Room> exits;
    private HashMap<String, Item> items; 

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
        exits = new HashMap<>();
        items = new HashMap<>();

    }

    /**
     * Define the exits of this room.  Every direction either leads
     * to another room or is null (no exit there).
     * @param direction The direction of the exit
     * @param neighbor The room where the exit leads to
     */
    public void setExit(String direction, Room neighbor) 
    {
        exits.put(direction, neighbor);
    }

    /**
     * Retourneer de omschrijving van een kamer
     * @return The description of the room.
     */
    public String getShortDescription()
    {
        return description;
    }

    public String getLongDescription()
    /**
     * Retourneer een lange omschrijving van deze ruimte, van de vorm:
     *  You are in Spongebob's house.
     *  Exits: north east south west
     *  @return Een omschrijving van de ruimte en haar uitgangen.
     */
    {
        return  description + ".\n" + getExitString();
    }

    /**
     * Retourneer een string met daarin de uitgangen van de ruimte,
     * bijvoorbeeld " Exits: north west".
     * @return Een omschrijving van de aanwezige uitgangen in de 
     * ruimte.
     *
     */
    public String getExitString()
    {
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString += " " + exit;
        }
        returnString += "\nItems in room:\n";
        returnString += getRoomItems();
        return returnString;

    }

    /**
     * 
     */
    public Room getExit(String direction)
    {

        return exits.get(direction);

    }

    /**
     * Get the item name.
     * When there is found nothing return null.
     * 
     * @param itemNames The name of the items
     * @return The item which belongs to the name
     */
    public Item getItem(String name){
        return items.get(name); 
    }

    /**
     * Remove an item from the room
     * 
     * @param itemName The name of the item
     */
    public void removeItem(String itemName)
    {
        items.remove(itemName); 

    }

    /**
     * sets item to a room
     * 
     * @param itemName The name of the item
     */
    public void setItem (Item newItem)
    {
        items.put(newItem.getItemName(), newItem);       
    }

    /**
     * Get the weight of items
     * 
     * @param itemName The name of the item
     *
     */
    public Item getItemValue(String name)
    {
        return (Item)items.get(name);
    }

    /**
     * gets all the items from a room
     *  Items in the room; bread
     *  Or when there are no items:
     *      Items in this room: no items in this room for example
     * 
     * @return Een lijst van alle items in de ruimte
     */
    public String getRoomItems(){
        String output = "";
        for(String itemName : items.keySet()){
            output += items.get(itemName).getItemName() + " " ;
        }
        return output;
    }

}
