import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class LoginPage extends JFrame {
    private static String currentUser;
    private static int userID;

    LoginPage() {
        this.setTitle("login-page");
        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);

        JLabel l1 = new JLabel("username: ");
        JLabel l2 = new JLabel("password: ");
        // TODO: add option to show password
        JPasswordField passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(100, 20));
        JTextField textField = new JTextField();

        JButton loginButton = new JButton("login");
        this.getRootPane().setDefaultButton(loginButton);
        loginButton.addActionListener(e -> {
            // TODO: change so that username and password aren't hardcoded
            String inputUsername = textField.getText();
            String inputPassword = passwordField.getText();
            if (takenUsername(inputUsername) && matchingPassword(inputPassword)) {
                this.dispose();
                new Page();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password");
            }
        });

        JButton exitButton = new JButton("exit");
        exitButton.addActionListener(e -> {
            this.dispose();
            System.exit(0);
        });

        JButton signUpButton = new JButton("Need an account? Sign up!");
        signUpButton.addActionListener(e -> {
            this.dispose();
            new SignupPage();
        });

        JPanel topPanel = new JPanel();
        topPanel.setBorder(BorderFactory.createEmptyBorder(15, 25, 0, 25));
        topPanel.setLayout(new GridLayout(3, 2));
        topPanel.add(l1);
        topPanel.add(textField);
        topPanel.add(l2);
        topPanel.add(passwordField);
        topPanel.add(exitButton);
        topPanel.add(loginButton);
        this.add(topPanel);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBorder((BorderFactory.createEmptyBorder(5, 25, 15, 25)));
        bottomPanel.add(signUpButton);

        c.gridy = 1;
        this.add(bottomPanel, c);
        this.pack();

        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public static boolean takenUsername(String username) {
        File usernames = new File("usernames.txt");
        boolean taken = false;
        try (Scanner in = new Scanner(usernames)) {
            while (in.hasNextLine()) {
                if (in.nextLine().equals(username)) {
                    taken = true;
                    currentUser = username;
                    userID++;
                    break;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return taken;
    }

    public boolean matchingPassword(String password) {
        // TODO: check if username matches password
        File passwords = new File("passwords.txt");
        String userPassword = "";
        try (Scanner in = new Scanner(passwords)) {
            for (int i = 0; i < userID; i++) {
                in.nextLine();
            }
            userPassword = in.nextLine();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return password.equals(userPassword);
    }
}
