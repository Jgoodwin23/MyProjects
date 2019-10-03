
import java.util.*;
import java.io.*;
import java.lang.*;

/**
 * Author: Jarred Goodwin
 * Class: CSCI 242
 * Assignment 5 - Facebook Lite
 *
 * Description:
 * This Java program will simulate a Facebook like design. The user will
 * be able to create new people, friend, unfriend, list friends, and ask if people are friends by entering
 * commands into the terminal. If the user needs to know what these commands are, they may enter '?' into the
 * terminal at any time and they will be given a list of commands
 *
 * Notes:
 * Make sure that the text file directory matches the directory of the location you are running this program from.
 * If a FileNotFound error occurs, you will need to make a slight change to the file name on line 97.
 *
 */
public class FacebookLite {
    private static FacebookLite facebookLite = new FacebookLite();
    private static LinkedHashSet<Person> personList = new LinkedHashSet<>(); // Holds a list of every person created by the program.

    /**
     * This method parses and processes different parts of an input command that are
     * separated by spaces.
     * @param str The input given by the user.
     * @return A parsed String value from the input.
     */
    private String commandParse(String str) {
        StringBuilder builder = new StringBuilder(str);
        boolean isLastSpace = true;
        for (int i = 0; i < builder.length(); i++) {
            char ch = builder.charAt(i);

            if (isLastSpace && ch >= 'a' && ch <= 'z') {
                builder.setCharAt(i, (char) (ch + ('A' - 'a')));
                isLastSpace = false;
            }
            else isLastSpace = (ch == ' ');
        }
        return builder.toString();
    }

    public static void main(String[] args) throws ArrayIndexOutOfBoundsException, StringIndexOutOfBoundsException, FileNotFoundException {
        Scanner input = new Scanner(System.in);
        boolean isRunning=false; //Tells the program whether or not it is still taking user input.
        System.out.println("<--| Facebook Lite |-->");
        System.out.println();
        int num = 0;
        String commandInput = "";

        System.out.println("(1) Interactive or (2) File?");
        num = input.nextInt();
        //-----------INPUT PORTION -----------
        if(num==1) {
            isRunning = true;
            Scanner key = new Scanner(System.in);
            commandInput = input.nextLine(); // prevents duplicate print statements.
        }

        while (isRunning) {
            try {
                System.out.println("Please Enter a command: ");
                commandInput = input.nextLine();

                if (commandInput.charAt(0) == '?') {
                    printCommands();
                }
                if (commandInput.charAt(0) == 'p' || commandInput.charAt(0) == 'P') {
                    addPerson(commandInput);
                }
                if (commandInput.charAt(0) == 'u' || commandInput.charAt(0) == 'U') {
                    removeFriends(commandInput);
                }
                if (commandInput.charAt(0) == 'F' || commandInput.charAt(0) == 'f')
                    newFriends(commandInput);
                if (commandInput.charAt(0) == 'L' || commandInput.charAt(0) == 'l')
                    checkList(commandInput);
                if (commandInput.charAt(0) == 'Q' || commandInput.charAt(0) == 'q')
                    queryFriends(commandInput);
                if (commandInput.charAt(0) == 'x' || commandInput.charAt(0) == 'X')
                    isRunning = false;
            } catch(Exception ex) {
                System.out.println("Invalid input. For a list of commands, enter '?' into the terminal.");
            }

        }
        //-----------INPUT PORTION -----------



        //-----------FILE PORTION -----------
        if(num == 2) {
            File file = new File("/edu/uwp/cs/csci242/assignments/a05/facebooklite/fbl_commands.txt");
            Scanner fileInput = new Scanner(file);
            System.out.println("Reading File Input: ");

            while (fileInput.hasNextLine()) {
                try {
                    commandInput = fileInput.nextLine();

                    if (commandInput.charAt(0) == '?') {
                        printCommands();
                    }
                    if (commandInput.charAt(0) == 'p' || commandInput.charAt(0) == 'P') {
                        addPerson(commandInput);
                    }
                    if (commandInput.charAt(0) == 'u' || commandInput.charAt(0) == 'U') {
                        removeFriends(commandInput);
                    }
                    if (commandInput.charAt(0) == 'F' || commandInput.charAt(0) == 'f')
                        newFriends(commandInput);
                    if (commandInput.charAt(0) == 'L' || commandInput.charAt(0) == 'l')
                        checkList(commandInput);
                    if (commandInput.charAt(0) == 'Q' || commandInput.charAt(0) == 'q')
                        queryFriends(commandInput);
                    if (commandInput.charAt(0) == 'x' || commandInput.charAt(0) == 'X')
                        isRunning = false;
                } catch(Exception ex) {
                    System.out.println("Invalid input. For a list of commands, enter '?' into the terminal.");
                }

            }


        }
        //-----------FILE PORTION -----------
    }

    /**
     * The new Person command. Create a person record of the specified name.
     * @param commandInput The series of inputs given by the user
     */
    private static void addPerson(String commandInput) {
        String commandParsed = facebookLite.commandParse(commandInput);
        String[] commandHandler = commandParsed.split(" ");
        String firstLetter = commandHandler[0].toLowerCase(); //letter 1A
        String secondWord = commandHandler[1];

        personList.add(new Person(secondWord));
        System.out.println(" 'Command: " + firstLetter + " " + secondWord + "' > Added " + secondWord + ".");
    }

    /**
     * The Friend command. Takes two people from the personList and adds them to
     * each other's friends list.
     * @param commandInput The series of inputs given by the user.
     */
    private static void newFriends(String commandInput) {
        String command = facebookLite.commandParse(commandInput);
        String[] parts = command.split(" ");
        String letter = parts[0].toLowerCase();
        String secondWord = parts[1];
        String thirdWord = parts[2];
        boolean x = false;
        Iterator<Person> it = personList.iterator();
        Person p;

        while(it.hasNext()) {
            p = it.next();
            if (p.getName().equals(secondWord)) {
                p.addFriends(thirdWord);
                x = true;
            }
            if (p.getName().equals(thirdWord)) {
                p.addFriends(secondWord);
                x = true;
            }
        }

        if(x)
            System.out.println(" 'Command: " + letter + " " + secondWord + " " + thirdWord + "' > " + secondWord + " and " + thirdWord + " are now friends.");
    }

    /**
     * The Unfriend Command. Reads two people and removes them from each other's friends list.
     * @param commandInput The series of inputs given by the user.
     */
    private static void removeFriends(String commandInput) {

        String commandParse = facebookLite.commandParse(commandInput);
        String[] parts = commandParse.split(" ");
        String letter3 = parts[0].toLowerCase();
        String secondWord = parts[1];
        String thirdWord = parts[2];

        Iterator<Person> it = personList.iterator();
        Person p1;

        while(it.hasNext()) {
            p1 = it.next();
            if (p1.getName().equals(secondWord))
                p1.removeFriends(thirdWord);
            if (p1.getName().equals(thirdWord))
                p1.removeFriends(secondWord);
        }
        System.out.println(" 'Command: " + letter3 + " " + secondWord + " " + thirdWord + "' > " + secondWord + " and " + thirdWord + " are no longer friends.");

    }

    /**
     * Prints a list of valid commands when the user enters '?"' into the terminal.
     */
    private static void printCommands() {
        System.out.println("List of Commands: ");
        System.out.println("P | p âŸ¨nameâŸ©â€“The new Person command.  " +
                "Create a person record of the specified name.");
        System.out.println();
        System.out.println("F | f âŸ¨name1âŸ©âŸ¨name2âŸ©â€“The Friend command. Record that the two specified people are\nfriends");
        System.out.println();
        System.out.println("U | u âŸ¨name1âŸ©âŸ¨name2âŸ©â€“The Unfriend command.  Record that the two specified people are no\nlonger friends.");
        System.out.println();
        System.out.println("L | l âŸ¨nameâŸ©â€“The List friends command.  Print out the friends of the specified person.");
        System.out.println();
        System.out.println("Q | q âŸ¨name1âŸ©âŸ¨name2âŸ©â€“The Query friends command.  Check whether the two people are\n" +
                "friends. If so, print a â€œYes message; if not, print a â€œNoâ€ message.");
        System.out.println();
        System.out.println("X â€“ The exit command. Terminate the program.");
        System.out.println();
    }

    /**
     * The Query command. Reads two people and checks whether or not they exist on each other's
     * friends list.
     * @param commandInput The series of inputs given by the user.
     */
    private static void checkList(String commandInput) {
        String command = facebookLite.commandParse(commandInput);
        String[] parts = command.split(" ");
        String letter = parts[0].toLowerCase();
        String secondWord = parts[1];

        Iterator<Person> it = personList.iterator();
        Person p;
        while(it.hasNext()) {
            p = it.next();
            if (p.getName().equals(secondWord)) {
                System.out.println(" 'Command: " + letter + " " + secondWord + "' > " + secondWord + "'s friends: " + p.getFriends() + " ");
                return;
            }
        }
    }

    /**
     * Displays the names on a specified person's friends list.
     * @param commandInput The series of inputs given by the user.
     */
    private static void queryFriends(String commandInput) {
        String command = facebookLite.commandParse(commandInput);
        String[] parts = command.split(" ");
        String letter = parts[0].toLowerCase();
        String secondWord = parts[1];
        String thirdWord = parts[2];

        Iterator<Person> it = personList.iterator();
        Person p;
        while(it.hasNext()) {
            p = it.next();
            if (p.getName().equals(secondWord)) {
                if(p.getFriends().contains(thirdWord)) {
                    System.out.println(" 'Command: " + letter + " " + secondWord + " " + thirdWord + "' > " + secondWord + " and " + thirdWord + " are friends.");
                    return;
                }
            }
        }
        System.out.println(" 'Command: " + letter + " " + secondWord + " " + thirdWord + "' > " + secondWord + " and " + thirdWord + " are not friends.");
    }
}
