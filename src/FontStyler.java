import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FontStyler extends JFrame implements ActionListener {
    private final JRadioButton[] styles;
    private final JButton submitButton;

    FontStyler() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        JLabel label = new JLabel("Choose a style from the list below: ");
        panel.add(label);

        submitButton = new JButton("submit");

        String[] styleNames = {"Plain", "Bold", "Italic"};
        styles = new JRadioButton[styleNames.length];

        ButtonGroup radioButtonGroup = new ButtonGroup();

        for (int i = 0; i < styles.length; i++) {
            styles[i] = new JRadioButton(styleNames[i]);
            styles[i].setName(styleNames[i]);
            styles[i].addActionListener(this);
            radioButtonGroup.add(styles[i]);
            panel.add(styles[i]);
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
        boolean styleSelected = false;
        if (e.getSource() == submitButton) {
            for (JRadioButton style : styles) {
                if (style.isSelected()) {
                    System.out.println(style.getName() + " selected");
                    // TODO: allow for a mix of styles, e.g. Bold + Italic
                    int fontStyle = switch (style.getName()) {
                        case "Bold" -> Font.BOLD;
                        case "Italic" -> Font.ITALIC;
                        default -> Font.PLAIN;
                    };
                    Page.textPane.setFont(new Font(Page.textPane.getFont().getFontName(), fontStyle, Page.textPane.getFont().getSize()));
                    System.out.println("Font: "+Page.textPane.getFont().getFontName()+" Style: "+fontStyle+" Size: "+Page.textPane.getFont().getSize());
                    styleSelected = true;
                    this.dispose();
                }
            }
            if (!styleSelected) System.out.println("No style selected");
        }
    }
}
