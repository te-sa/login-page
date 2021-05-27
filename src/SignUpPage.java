import javax.swing.*;

public class SignUpPage extends JFrame {
    SignUpPage() {
        // TODO: finish signup page

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setBorder((BorderFactory.createEmptyBorder(15, 25, 15, 25)));
        JLabel text = new JLabel("Sign up here!");
        panel.add(text);

        this.add(panel);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
