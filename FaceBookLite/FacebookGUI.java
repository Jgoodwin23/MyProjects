
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.*;

import java.awt.event.*;
import java.util.Iterator;
import java.util.LinkedHashSet;

public class FacebookGUI extends JFrame {
    private static FacebookLite fbl = new FacebookLite();
    private static LinkedHashSet<Person> personList = new LinkedHashSet<>();
    JButton createPerson;
    JTextField textField1;
    JButton makeFriendsButton;
    JButton showFriendsButton;
    JButton queryFriends;
    JButton removeFriends;
    JButton clearText;

    JTextArea textArea1;


    /**
     * This method parses and processes different parts of an input command that are
     * separated by spaces.
     *
     * @param str The input given by the user.
     * @return A parsed String value from the input.
     */
    public String myParser(String str) {
        StringBuilder builder = new StringBuilder(str);
        boolean isLastSpace = true;
        for (int i = 0; i < builder.length(); i++) {
            char ch = builder.charAt(i);

            if (isLastSpace && ch >= 'a' && ch <= 'z') {
                builder.setCharAt(i, (char) (ch + ('A' - 'a')));
                isLastSpace = false;
            } else isLastSpace = (ch == ' ');
        }
        return builder.toString();
    }

    public static void main(String[] args) {

        new FacebookGUI();

    }

    public FacebookGUI() {

        // Define the size of the frame
        this.setSize(600, 600);
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension dim = tk.getScreenSize();

        // dim.width returns the width of the screen
        // this.getWidth returns the width of the frame you are making
        int xPos = (dim.width / 2) - (this.getWidth() / 2);
        int yPos = (dim.height / 2) - (this.getHeight() / 2);

        // defines the x, y position of the frame
        this.setLocation(xPos, yPos);

        // Allows the Java application to close when the JFrame window is closed.
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Title for the frame
        this.setTitle("Facebook Lite GUI");

        // The JPanel that contains all of the components for the frame
        JPanel thePanel = new JPanel();

        //The buttons that will be created and used.
        createPerson = new JButton("Create Person");
        makeFriendsButton = new JButton("Add Friends");
        showFriendsButton = new JButton("Show Friends List");
        queryFriends = new JButton("Query");
        removeFriends = new JButton("Remove Friends");
        clearText = new JButton("Clear Large Text Field");
        clearText.setAlignmentY(200);

        // Create an instance of ListenForEvents to handle events
        ListenForButton lForButton = new ListenForButton();

        // Tell Java that you want to be alerted when an event
        // occurs on the button
        createPerson.addActionListener(lForButton);
        makeFriendsButton.addActionListener(lForButton);
        showFriendsButton.addActionListener(lForButton);
        queryFriends.addActionListener(lForButton);
        removeFriends.addActionListener((lForButton));
        clearText.addActionListener(lForButton);

        thePanel.add(createPerson);
        thePanel.add(makeFriendsButton);
        thePanel.add(showFriendsButton);
        thePanel.add(queryFriends);
        thePanel.add(removeFriends);
        thePanel.add(clearText);

        textField1 = new JTextField("", 20);

        thePanel.add(textField1);

        // How to add a text area ----------------------

        textArea1 = new JTextArea(20, 25);

        // Sets the default text for the text area
        textArea1.setText("Welcome to the FaceBook Lite GUI.\n\nCreate Person: Enter one name in the text field to create a new person\n\n" +
                "Add Friends: Type two created users into the text field to make them friends with each other.\n\n" +
                "Show Friends List: Enter one created user into the text field to show who they are friends with.\n\n" +
                "Query: Enter two created users into the text field to check if they are friends with each other.\n\n" +
                "Remove Friends: Enter two created users into the field to remove them from each other's friends list.\n\n");

        // If text doesn't fit on a line, jump to the next
        textArea1.setLineWrap(true);

        // Makes sure that words stay intact if a line wrap occurs
        textArea1.setWrapStyleWord(true);

        // Adds scroll bars to the text area
        JScrollPane scrollbar1 = new JScrollPane(textArea1, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        thePanel.add(scrollbar1);

        this.add(thePanel);
        this.setVisible(true);

    }


    /**
     * The Action Handler for all of the buttons that will be used.
     * This method is called whenever a button is pressed.
     */
    private class ListenForButton implements ActionListener {

        public void actionPerformed(ActionEvent e) {

            // Check if the source of the event was the button

            //Action Handler for the Create Person button.
            if (e.getSource() == createPerson) {
                personList.add(new Person(textField1.getText()));

                textArea1.append(textField1.getText() + " has been added to personList\n");
                textArea1.append("There are: " + personList.size() + " people on the PersonList\n");
                textField1.setText("");

            }

            //Action Handler for the Add Friends button
            if (e.getSource() == makeFriendsButton) {

                String command = myParser(textField1.getText());
                String[] parts = command.split(" ");
                String secondWord = parts[0];
                String thirdWord = parts[1];
                Iterator<Person> it = personList.iterator();
                Person p;

                while (it.hasNext()) {
                    p = it.next();
                    if (p.getName().equals(secondWord)) {
                        p.addFriends(thirdWord);
                    }
                    if (p.getName().equals(thirdWord)) {
                        p.addFriends(secondWord);
                    }
                }
                textArea1.append(secondWord + " and " + thirdWord + " are now friends.\n");
                textField1.setText("");
            }

            //Action Handler for the Remove Friends button.
            if (e.getSource() == removeFriends) {
                String command = myParser(textField1.getText());
                String[] parts = command.split(" ");
                String secondWord = parts[0];
                String thirdWord = parts[1];
                Iterator<Person> it = personList.iterator();
                Person p1;

                while (it.hasNext()) {
                    p1 = it.next();
                    if (p1.getName().equals(secondWord))
                        p1.removeFriends(thirdWord);
                    if (p1.getName().equals(thirdWord))
                        p1.removeFriends(secondWord);
                }
                textArea1.append(secondWord + " and " + thirdWord + " are no longer friends.\n");
                textField1.setText("");

            }

            // Action Handler for the Show Friends button.
            if (e.getSource() == showFriendsButton) {
                String command = myParser(textField1.getText());
                String[] parts = command.split(" ");
                String secondWord = parts[0];
                Iterator<Person> it = personList.iterator();
                Person p;

                while (it.hasNext()) {
                    p = it.next();
                    if (p.getName().equals(secondWord)) {
                        textArea1.append(secondWord + "'s friends: " + p.getFriends() + "\n");
                    }
                }
                textField1.setText("");
            }

            //Action Handler for the Query Friends button.
            if (e.getSource() == queryFriends) {
                String command = myParser(textField1.getText());
                String[] parts = command.split(" ");
                String secondWord = parts[0];
                String thirdWord = parts[1];

                Iterator<Person> it = personList.iterator();
                Person p;

                while (it.hasNext()) {
                    p = it.next();
                    if (p.getName().equals(secondWord)) {
                        if (p.getFriends().contains(thirdWord))
                            textArea1.append(secondWord + " and " + thirdWord + " are friends.\n");
                        else
                            textArea1.append(secondWord + " and " + thirdWord + " are not friends.\n");
                    }
                }
                textField1.setText("");
            }

            if (e.getSource() == clearText) {
                textArea1.setText("");
            }

        }

    }
}

