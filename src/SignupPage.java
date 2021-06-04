import javax.swing.*;
import javax.swing.border.EmptyBorder;
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
                JOptionPane.showMessageDialog(this, "The username " + potentialUsername + " is already taken!\nYou will have to pick another one", "", JOptionPane.ERROR_MESSAGE);
                // IDEA: autogenerate valid usernames for signup like Reddit
            } else if (!validPassword(potentialPassword)) {
                JOptionPane.showMessageDialog(this, "Please choose a valid password", "", JOptionPane.WARNING_MESSAGE);
            } else {
                createNewAccount(potentialUsername, potentialPassword);
            }
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
        // this only works if there is an account in there already, it wouldn't work for the first one
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
        JOptionPane.showMessageDialog(this, "Welcome, " + username + ", your account has been created!\nMake sure to save your login info!");
        new RedirectScreen();
        this.dispose();
        new LoginPage();
    }

    public boolean validPassword(String potentialPassword) {
        // TODO: make this work
        // use regex here?

        // how long should a password be? At least 8 chars
        // what should a valid password have?
        // - upper and lower case letters
        // - letters and numbers
        // - at least one special character

        if (potentialPassword.length() < 8) {
            System.out.println("Your password should contain at least 8 characters");
            return false;
        } else if (potentialPassword.toUpperCase().equals(potentialPassword)
                || potentialPassword.toLowerCase().equals(potentialPassword)) {
            System.out.println("Your password should contain both uppercase and lowercase letters");
            return false;
        } else {
            return true;
        }

    }
}

// why is this not working when called from SignupPage?
class RedirectScreen extends JFrame {
    JProgressBar bar;

    RedirectScreen() {
        bar = new JProgressBar(0, 100);
        bar.setValue(0);
        bar.setStringPainted(true);
        bar.setOpaque(true);

        JLabel label = new JLabel("Redirecting you to login...");

        JPanel panel = new JPanel(new GridLayout(2, 1));
        panel.setBorder(new EmptyBorder(15, 25, 15, 25));
        panel.add(label);
        panel.add(bar);

        this.add(panel);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        fill();
    }

    // used code from: https://www.youtube.com/watch?v=JEI-fcfnFkc and https://www.geeksforgeeks.org/java-swing-jprogressbar/
    public void fill() {
        int counter = 0;
        while (counter <= 100) {
            bar.setValue(counter);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            counter += 10;
        }
        this.dispose();
    }
}
