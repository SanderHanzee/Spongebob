import java.util.*;

/**
 *  This class is the main class of "Spongebob adventure" application. 
 *  "Spongebob adventure" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Sander Brands and Kevin de Graaf
 * @version 2020-01-26
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private Room finalRoom;
    private Stack<Room> roomHistory;
    private HashMap<String , Item> inventory;
    private int playerWeight;
    private int weightLimit;
    private int timer;
    private int maxSteps; 
    private boolean winwin;
    /**
     * Create the game and initialise its internal map.
     */

    public static void main(String[] args) {
        Game game = new Game();
        game.play();
    }

    public Game() 
    {
        createRooms();
        parser = new Parser();
        roomHistory = new Stack<Room>();
        inventory = new HashMap<String, Item>(); 
        playerWeight = 0;
        weightLimit = 10; 
        timer = 0;
        maxSteps = 6; 
    }

    /**
     * Create all the rooms and link their exits together.
     */
    public void createRooms()
    {
        Room spongebob, patrick, octo, chumbucket, sorbetpartybar, krokantekrab, keukenkrokantekrab;

        // create the rooms
        spongebob = new Room("Spongebob's house is the central place of the map");
        patrick = new Room("Patrick's house");
        octo = new Room("Octo's house; there seems to be a secret door upstairs ");
        chumbucket = new Room("the Chum bucket");
        sorbetpartybar = new Room("the Sorbetpartybar");
        krokantekrab = new Room("the Krusty Krab");
        keukenkrokantekrab = new Room("the kitchen of Krusty Krab");

        // geformuleerd op Noord - Oost - Zuid - West 
        // initialise room exits

        spongebob.setExit("north", chumbucket);
        spongebob.setExit("east" , octo);
        spongebob.setExit("south", patrick);
        spongebob.setExit("west", sorbetpartybar);

        patrick.setExit("north", spongebob);

        octo.setExit("east", krokantekrab);
        octo.setExit("west", spongebob);

        octo.setExit("up", chumbucket);

        chumbucket.setExit("south", spongebob);

        sorbetpartybar.setExit("east", spongebob);

        krokantekrab.setExit("west", octo);
        krokantekrab.setExit("east", keukenkrokantekrab);

        keukenkrokantekrab.setExit("west", krokantekrab);

        spongebob.setItem(new Item("knife", "A big knife", 6));

        patrick.setItem(new Item("bread", "Big bread for the huge hamburger, 1 of the 3 ingredients", 3));
        krokantekrab.setItem(new Item("burger", "Big fat huge hamburger, 1 of the 3 ingredients", 4));
        chumbucket.setItem(new Item("cookie","Eat to gain extra moves", 3)); 
        sorbetpartybar.setItem(new Item("secretingredient", "The secret ingredient for the hamburger 1 of the 3 ingredients", 3));

        currentRoom = spongebob;  // start game in spongebobs house

        finalRoom = keukenkrokantekrab;
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.

        boolean finished = false;

        while (! finished) {

            if(timer == maxSteps) {
                System.out.println("You have taken to many steps");
                System.out.println("GAME OVER");
                System.out.println("Thank you for playing");
                finished = true;

            }

            else if(winwin == true) {
                System.out.println("congratulations you have made a krabby patty and therefore won the game");
                finished = true;
            }

            else {

                Command command = parser.getCommand();
                finished = processCommand(command);

            }

        }
    }

    public void about()
    {
        System.out.println();
        System.out.println("Hi there! I heard you want to know more about this game!");
        System.out.println("You are in Spongebob adventure game and you got only ... steps.");
        System.out.println("Try to find the 3 ingredients to make the Krusty hamburger");
        System.out.println("You started at Spongebob's house which is the center of the map");

        System.out.println("Created by: Kevin and Sander");
        System.out.println();
    }

    /**
     * Print out the opening message for the player.
     */
    public void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Spongebob!");
        System.out.println("Type 'help'  if you need help to find all commands.");
        System.out.println("Type 'about' if you want more information.");
        System.out.println();
        System.out.println(currentRoom.getShortDescription());

    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    public boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        switch (commandWord) {
            case UNKNOWN:
            System.out.println("I don't know what you mean...");
            break;

            case HELP:
            printHelp();
            break;

            case GO:
            goRoom(command);
            break;

            case QUIT:
            wantToQuit = quit(command);
            break;

            case LOOK:
            look();
            break;

            case BACK:
            goBack();
            break;

            case INVENTORY:
            printInventory();
            break;

            case GET:
            getItem(command);
            break;

            case DROP:
            dropItem(command);
            break;

            case EAT:  
            eat();
            break;

            case ABOUT:
            about();
            break;

            case MAKE:
            make();
            break;
        }
        return wantToQuit;
    }

    /**
     * drop(item) is entered, the player can drop an item
     * Item will be removed out the inventory
     * 
     * @param command The item the players wants to remove
     */
    public void dropItem(Command command) 
    {

        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know what to drop...
            System.out.println("Wait a minute drop what?");
            return;
        }

        String itemName = command.getSecondWord();
        Item newItem = inventory.get(itemName);
        if(newItem == null){
            System.out.println("You can't drop something you don't have");
        }
        else{
            playerWeight = playerWeight - newItem.getItemWeight(); 
            inventory.remove(itemName);
            currentRoom.setItem(newItem); 
            System.out.println("Dropped: " + itemName); 
        }
    }

    /**
     * get(item)  is entered, the player can pick an item
     * Item will be placed in players inventory
     * 
     * @param command The item the players wants to get
     */
    public void getItem(Command command) 
    {

        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know what to pickup..
            System.out.println("Get what?");
            return;
        }

        String itemName = command.getSecondWord();
        Item newItem = currentRoom.getItem(itemName);

        if(newItem == null){
            System.out.println("There is no item in this room");
        }
       // else{
            if(playerWeight + newItem.getItemWeight() > weightLimit) 
            {
                System.out.println("You are to heavy to carry this item");
                System.out.println("Maybe you need to drop something or eat something?");
            }
            else{
                playerWeight = playerWeight + newItem.getItemWeight();  
                inventory.put(itemName, newItem);
                currentRoom.removeItem(itemName);
                System.out.println("Picked up: " + itemName); 
            }
      //  }
    }

    /**
     * printinventory is entered, the player see their inventory
     * 
     * 
     * @param Prints inventory
     */

    public void printInventory(){

        String output = "";
        for(String itemName : inventory.keySet()){
            output += inventory.get(itemName).getItemName() + " Weight: " + inventory.get(itemName).getItemWeight() + "\nDescription:" + inventory.get(itemName).getItemDescription() + "\n"  ; 
        }
        System.out.println("You are carrying:");
        System.out.println(output); 
    }

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    public void printHelp() {
        System.out.println("You are lost. You are alone.");
        System.out.println("around at Spongebob adventure.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    /** 
     * Try to go in one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    public void goRoom(Command command) 
    {
        timer = timer +1 ; 

        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if(command.getSecondWord().equals("up")) {

            System.out.println("You fell into my teleportation trap");
            System.out.println("There are 3 steps removed");

            timer = timer+3;

        }

        if (nextRoom == null) {
            System.out.println("There is no door!");

        }

        else {
            roomHistory.push(currentRoom);
            currentRoom = nextRoom;
            System.out.println("You are in " + currentRoom.getShortDescription());
            System.out.println("You have " + (maxSteps - timer) + " steps left" );
        }
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    public boolean quit(Command command) 
    {

        return true;  // signal that we want to quit

    }

    /**
     * 'look' was entered. Room information will be descripted
     * 
     * @param Room information
     * 
     */
    public void look()
    {
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * 'eat' was entered. eat the cookie to gain steps
     * 
     * @param Cookie to gain steps
     */
    public void eat(){
        String eatOutput = "You dont have any food in your inventory";

        for(String itemName : inventory.keySet()){
            if(itemName.equals("cookie")){
                maxSteps = maxSteps + 5;
                eatOutput = "You ate the cookie and gained 5 steps";

            }
        }

        System.out.println(eatOutput);
        playerWeight = playerWeight - 3;
        inventory.remove("cookie");
    }

    /**
     * 'back' was entered. Try to go to the previous room.
     * If there is no previous room print error.
     * Keep history of all the player movements.
     */
    public void goBack()
    {
        if (roomHistory.empty())
        { System.out.println("You already went back to the first place.");
        }
        else {
            currentRoom = roomHistory.pop();
            System.out.println(currentRoom.getLongDescription());
            timer = timer +1 ;
            System.out.println("You have " + (maxSteps - timer) + " steps left" );
        }
    }

    /**
     * 'make' was entered. Make the burger.
     * 
     * 
     */
    public void make()
    {

        boolean win = false; 
        int itemsNeeded = 0; 
        for(String itemName : inventory.keySet()){ 
            if(itemName.equals("burger")){
                itemsNeeded = itemsNeeded +1;
            }
            if(itemName.equals("bread")){
                itemsNeeded = itemsNeeded +1;
            }
            if(itemName.equals("secretingredient")){
                itemsNeeded = itemsNeeded +1;
            }
        }
        if (itemsNeeded == 3){ 
            win = true;
        }

        if(currentRoom.equals(finalRoom) && win){

            winwin = true;

        }
        else{
            System.out.println("You are not in the right room or you dont have all the item");
        }
    }
}
