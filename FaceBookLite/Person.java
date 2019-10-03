import java.util.*;

/**
 * Person class holds the values for their name and list of friends.
 */
public class Person {

    String name;
    TreeSet<String> friendsList;

    public Person() {
        //Default constructor;
        name = "(No name)";
        friendsList = new TreeSet<>();
    }

    /**
     * 1-Arg Constructor for Person
     * @param yourName Holds all the friend names of the Person
     */
    public Person (String yourName) {
        this.name = yourName;
        friendsList = new TreeSet<>();
    }

    /**
     * Set the name value
     * @param name String of the person's name
     */
    public void setName (String name) {
        this.name = name;
    }

    /**
     * Get the name value
     * @return String of the person's name
     */
    public String getName () {
        return name;
    }

    /**
     * Add a friend's name to the friend list
     * @param friendName String of the friend's name
     */
    public void addFriends (String friendName) {
        friendsList.add(friendName);
    }

    /**
     * Remove a friend's name from the friend list
     * @param friendName String of the friend's name
     */
    public void removeFriends (String friendName) {
        friendsList.remove(friendName);
    }

    public TreeSet<String> getFriends () {
        return friendsList;
    }


}
