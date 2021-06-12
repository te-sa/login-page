import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

// why is this not working when called from SignupPage?
public class RedirectScreen extends JFrame {
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
                // can I even do this here?
                Thread.currentThread().wait(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            counter += 10;
        }
        this.dispose();
    }
}
