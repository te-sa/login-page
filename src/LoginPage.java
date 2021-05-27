import javax.swing.*;
import java.awt.*;

public class LoginPage extends JFrame {
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
            if (textField.getText().equals("te-sa") && passwordField.getText().equals("password")) {
                loginButton.setEnabled(false);
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
}
