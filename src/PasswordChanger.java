import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

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
        // TODO: make println statements into JOptionPanes
        // I don't think matching like this is enough, any user can change any other users password rn
        // seems like that isn't an issue
        if (LoginPage.matchingPassword(passwordField.getText())) {
            if (Arrays.equals(newPasswordField.getPassword(), confirmNewPasswordField.getPassword())) {
                // how to actually change password?
                // reference: https://stackoverflow.com/questions/20039980/java-replace-line-in-text-file
                File passwords = new File("passwords.txt");
                StringBuilder buffer = new StringBuilder();
                try (Scanner in = new Scanner(passwords)) {
                    int lineCounter = 0;
                    while (in.hasNextLine()) {
                        if (lineCounter == LoginPage.userID) {
                            buffer.append(newPasswordField.getText()).append(System.lineSeparator());
                            in.nextLine(); // need to skip original password
                        } else buffer.append(in.nextLine()).append(System.lineSeparator());
                        lineCounter++;
                    }
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
                try (BufferedWriter out = new BufferedWriter(new FileWriter(passwords))) {
                    out.write(String.valueOf(buffer));
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
                System.out.println("Password change successful!");
                this.dispose();
            } else {
                System.out.println("Passwords don't match");
            }
        } else System.out.println("Invalid password, please retry");
    }
}
