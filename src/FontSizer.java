import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FontSizer extends JFrame implements ActionListener {
    private final JRadioButton[] sizes;
    private final JButton submitButton;

    FontSizer() {
        this.setResizable(false);

        JPanel panel = new JPanel();
        // could add scroll bar?
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        panel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        JLabel label = new JLabel("Select font size: ");
        panel.add(label);

        submitButton = new JButton("submit");

        String[] availableSizes = {"8", "9", "10", "11", "12", "14", "18", "24", "30", "36", "48", "60", "72", "96"};
        sizes = new JRadioButton[availableSizes.length];

        ButtonGroup buttonGroup = new ButtonGroup();

        // TODO: figure out how to center buttons
        for (int i = 0; i < sizes.length; i++) {
            sizes[i] = new JRadioButton(availableSizes[i]);
            sizes[i].setName(availableSizes[i]);
            sizes[i].addActionListener(this);
            buttonGroup.add(sizes[i]);
            panel.add(sizes[i]);
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
        boolean sizesSelected = false;
        if (e.getSource() == submitButton) {
            for (JRadioButton sizes : sizes) {
                if (sizes.isSelected()) {
                    System.out.println(sizes.getName() + " selected");
                    Page.textPane.setFont(new Font(Page.textPane.getFont().getFontName(), Page.textPane.getFont().getStyle(), Integer.parseInt(sizes.getName())));
                    // how to print style instead of int representing style?
                    System.out.println("Font: " + Page.textPane.getFont().getFontName() + " Style: " + Page.textPane.getFont().getStyle() + " Size: " + Page.textPane.getFont().getSize());
                    sizesSelected = true;
                    this.dispose();
                }
            }
            if (!sizesSelected)
                JOptionPane.showMessageDialog(this, "No size selected", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
