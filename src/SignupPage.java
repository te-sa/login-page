import javax.swing.*;
import java.awt.*;

public class SignupPage extends JFrame {
    SignupPage() {
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

        JLabel l1 = new JLabel("usernameField: ");
        JLabel l2 = new JLabel("passwordField: ");

        JTextField usernameField = new JTextField();
        usernameField.setPreferredSize(new Dimension(100, 20));
        JTextField passwordField = new JTextField();

        JButton accountButton = new JButton("Create new account");
        accountButton.addActionListener(e -> {
            System.out.println("Account created! Username: " + usernameField.getText() + " Password: " + passwordField.getText());
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
}
