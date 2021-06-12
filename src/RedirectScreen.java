import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class RedirectScreen extends JFrame {
    private final JProgressBar bar;
    private final Timer timer;
    private int counter;

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
        // timer is much easier than meddling with threads
        // still wondering why window would not update when called from another class than Main when using sleep()
        timer = new Timer(500, e -> fill());
        timer.start();
    }

    public void fill() {
        if (counter <= 100) {
            bar.setValue(counter);
            counter += 10;
        } else {
            timer.stop();
            new LoginPage();
            this.dispose();
        }
    }
}
