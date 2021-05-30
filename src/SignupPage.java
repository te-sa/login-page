import javax.swing.*;
import java.awt.*;
import java.io.*;

public class SignupPage extends JFrame {
    SignupPage() {
        // TODO: add a user-counter and display message: You are the nth user!
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("signup-page");
        this.setResizable(false);

        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JPanel topPanel = new JPanel();
        topPanel.setBorder((BorderFactory.createEmptyBorder(15, 25, 15, 25)));
        topPanel.setLayout(new GridLayout(2, 2));

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBorder((BorderFactory.createEmptyBorder(0, 10, 20, 10)));
        bottomPanel.setLayout(new GridLayout(2, 1));

        JLabel l1 = new JLabel("username: ");
        JLabel l2 = new JLabel("password: ");

        JTextField usernameField = new JTextField();
        usernameField.setPreferredSize(new Dimension(100, 20));
        JTextField passwordField = new JTextField();

        JButton accountButton = new JButton("Create new account");
        accountButton.addActionListener(e -> {
            String potentialUsername = usernameField.getText();
            String potentialPassword = passwordField.getText();
            if (LoginPage.takenUsername(potentialUsername)) {
                System.out.println("The username " + potentialUsername + " is already taken! You will have to pick another one");
                // IDEA: autogenerate valid usernames for signup like Reddit
            } else if (!validPassword(potentialPassword)) {
                System.out.println("Please choose a valid password");
            } else {
                createNewAccount(potentialUsername, potentialPassword);
            }
            /* TODO: make this functional
            - check if username is already taken
            - if it is, pop up
            - else create new account
            - only create account if valid info is input, check password
            - where to save account info?
            - how to encrypt it?
             */
        });
        this.getRootPane().setDefaultButton(accountButton);

        JButton backButton = new JButton("Already have an account? Sign in");
        backButton.addActionListener(e -> {
            this.dispose();
            new LoginPage();
        });

        topPanel.add(l1);
        topPanel.add(usernameField);
        topPanel.add(l2);
        topPanel.add(passwordField);

        bottomPanel.add(accountButton);
        bottomPanel.add(backButton);

        this.add(topPanel);
        c.gridy = 1;
        this.add(bottomPanel, c);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void createNewAccount(String username, String password) {
        // TODO: add security question?
        File usernames = new File("usernames.txt");
        File passwords = new File("passwords.txt");
        try (BufferedWriter out = new BufferedWriter(new FileWriter(usernames, true))) {
            out.write("\n" + username);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        try (BufferedWriter out = new BufferedWriter(new FileWriter(passwords, true))) {
            out.write("\n" + password);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        JOptionPane.showMessageDialog(this, "Welcome, " + username + ", your account has been created! Make sure to save your login info!");
        this.dispose();
        new LoginPage();
    }

    public boolean validPassword(String potentialPassword) {
        // what should a valid password have?
        return !potentialPassword.isBlank();
    }
}
