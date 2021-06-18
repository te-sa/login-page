import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FontSelector extends JFrame implements ActionListener {
    private final JRadioButton[] fonts;
    private final JButton submitButton;

    FontSelector() { // to be deleted
        this.setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        JLabel label = new JLabel("Choose a font from the list below: ");
        panel.add(label);

        submitButton = new JButton("submit");

        String[] fontNames = {"LucidaGrande", "American Typewriter", "Arial Black", "Comic Sans MS", "Helvetica", "Papyrus"};
        fonts = new JRadioButton[fontNames.length];

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
                    System.out.println(font.getName() + " selected");
                    // using code from https://stackoverflow.com/questions/9336798/java-how-to-get-the-default-font-size-of-a-jtextpane
                    Page.textArea.setFont(new Font(font.getName(), Page.textArea.getFont().getStyle(), Page.textArea.getFont().getSize()));
                    // how to print style instead of int representing style?
                    System.out.println("Font: " + Page.textArea.getFont().getFontName() + " Style: " + Page.textArea.getFont().getStyle() + " Size: " + Page.textArea.getFont().getSize());
                    fontSelected = true;
                    this.dispose();
                }
            }
            if (!fontSelected)
                JOptionPane.showMessageDialog(this, "No font selected", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
