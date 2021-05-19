import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FontSelector extends JFrame implements ActionListener {
    private final JRadioButton[] fonts;
    private final JButton submitButton;

    FontSelector() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        JLabel label = new JLabel("Choose a font from the list below: ");
        panel.add(label);

        submitButton = new JButton("submit");

        fonts = new JRadioButton[5];

        String[] fontNames = {"American Typewriter", "Arial Black", "Comic Sans MS", "Helvetica", "Papyrus"};

        ButtonGroup radioButtonGroup = new ButtonGroup();

        for (int i = 0; i < fonts.length; i++) {
            fonts[i] = new JRadioButton(fontNames[i]);
            fonts[i].setName(fontNames[i]);
            fonts[i].addActionListener(this);
            radioButtonGroup.add(fonts[i]);
            panel.add(fonts[i]);
        }

        panel.add(submitButton);

        submitButton.addActionListener(this);

        this.getRootPane().setDefaultButton(submitButton);
        this.add(panel);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        boolean fontSelected = false;
        if (e.getSource() == submitButton) {
            for (JRadioButton font : fonts) {
                if (font.isSelected()) {
                    System.out.println(font.getName()+" selected");
                    // using code from https://stackoverflow.com/questions/9336798/java-how-to-get-the-default-font-size-of-a-jtextpane
                    Page.textPane.setFont(new Font(font.getName(), Font.PLAIN, Page.textPane.getFont().getSize()));
                    fontSelected = true;
                    this.dispose();
                }
            }
            if (!fontSelected) System.out.println("No font selected");
        }
    }
}
