import javax.swing.*;
import java.awt.*;

public class LoginPage extends JFrame {
    LoginPage() {
        this.setTitle("login-page");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);

        JLabel l1 = new JLabel("username: ");
        JLabel l2 = new JLabel("password: ");
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
                JOptionPane.showMessageDialog(this,"Invalid username or password");
            }
        });

        JButton exitButton = new JButton("exit");
        exitButton.addActionListener(e -> {
            this.dispose();
            System.exit(0);
        });

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        panel.setLayout(new GridLayout(3, 2));
        panel.add(l1);
        panel.add(textField);
        panel.add(l2);
        panel.add(passwordField);
        panel.add(exitButton);
        panel.add(loginButton);

        this.add(panel);
        this.pack();

        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
