/**
 * This class is part of "Spongebob adventure" application. 
 * "Spongebob adventure" is a very simple, text based adventure game.  
 * 
 * This makes the game class more clear with cases.
 * Here you put in which command words there are to get a better view on it
 * 
 * @author  Sander Brands and Kevin de Graaf
 * @version 2020.01.26
 */
public enum CommandWord
{
    // Een waarde voor elk opdrachtwoord, samen met de
    // bijbehorende string voor de gebruikersinterface.
    GO("go"), 

    QUIT("quit"), 

    HELP("help"), 

    UNKNOWN("?"),

    LOOK("look"), 

    BACK("back"), 

    INVENTORY("inventory"),

    GET("get"), 

    DROP("drop"),

    EAT("eat"),

    ABOUT("about"),

    MAKE("make");

    // De tekst van de opdracht.
    private String commandString;

    /**
     * Initialise with the corresponding command string.
     * @param commandString The command string.
     */
    CommandWord(String commandString)
    {
        this.commandString = commandString;
    }

    /**
     * @return The command word as a string.
     */
    public String toString()
    {
        return commandString;
    }
}

   

