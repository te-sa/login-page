import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class PasswordChanger extends JFrame implements ActionListener {
    private final JPasswordField passwordField;
    private final JPasswordField newPasswordField;
    private final JPasswordField confirmNewPasswordField;

    PasswordChanger() {
        JPanel panel = new JPanel(new GridLayout(4, 2));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 25));

        JLabel l1 = new JLabel("enter current password: ");
        JLabel l2 = new JLabel("enter new password: ");
        JLabel l3 = new JLabel("confirm new password: ");

        passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(100, 20));
        newPasswordField = new JPasswordField();
        confirmNewPasswordField = new JPasswordField();

        JButton cancelButton = new JButton("cancel");
        cancelButton.addActionListener(e -> this.dispose());
        JButton confirmButton = new JButton("confirm");
        this.getRootPane().setDefaultButton(confirmButton);
        confirmButton.addActionListener(this);

        // need to see if input in password field matches current user password

        // need to check if content in newPasswordField and confirmNewPasswordField match

        // then: change password + success message

        panel.add(l1);
        panel.add(passwordField);
        panel.add(l2);
        panel.add(newPasswordField);
        panel.add(l3);
        panel.add(confirmNewPasswordField);
        panel.add(cancelButton);
        panel.add(confirmButton);

        this.add(panel);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // I don't think matching like this is enough, any user can change any other users password rn
        if (LoginPage.matchingPassword(passwordField.getText())) {
            if (Arrays.equals(newPasswordField.getPassword(), confirmNewPasswordField.getPassword())) {
                System.out.println("Password change successful!");
            } else {
                System.out.println("Passwords don't match");
            }
        } else System.out.println("Invalid password, please retry");
    }
}
