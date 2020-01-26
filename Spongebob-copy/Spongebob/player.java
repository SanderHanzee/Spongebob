import java.util.HashMap; 
/**
 * This class is part of "Spongebob adventure" application. 
 * "Spongebob adventure" is a very simple, text based adventure game.  
 * 
 * 
 * @author  Sander Brands and Kevin de Graaf
 * @version 2020.01.26
 */
public class player
{

    private int weightLimit;
    HashMap<String,Item> inventory;
    private int playerWeight;
    private Room currentRoom;

    /**
     * Makes the player
     * 
     */

    public player()
    {
        weightLimit = 10;
        playerWeight = 0;
        inventory = new HashMap<String, Item>(); 
        currentRoom = null; 
    }

    /**
     * 
     * @return Currentroom Get the currentroom.
     */
    public Room getCurrentRoom()
    {
        return currentRoom; 
    }
}

