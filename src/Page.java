import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Page extends JFrame implements ActionListener {
    JMenuItem openFile;
    JMenuItem saveFile;
    JMenuItem findAndReplace;
    JMenuItem changeFontSize;
    JMenuItem changeFont;
    JMenuItem changeFontColor;
    JMenuItem changeBackgroundColor;
    JMenuItem exitFile;

    Page() {
        this.setTitle("page");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setResizable(false); // may change later

        JMenuBar menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);

        JMenu fileMenu = new JMenu("File");
        JMenu editMenu = new JMenu("Edit");
        JMenu formatMenu = new JMenu("Format");
        JMenu helpMenu = new JMenu("Help");

        openFile = new JMenuItem("Open");
        openFile.addActionListener(this);
        saveFile = new JMenuItem("Save");
        saveFile.addActionListener(this);
        findAndReplace = new JMenuItem("Find and replace");
        findAndReplace.addActionListener(this);
        changeFontSize = new JMenuItem("Font size");
        changeFontSize.addActionListener(this);
        changeFont = new JMenuItem("Font");
        changeFont.addActionListener(this);
        changeFontColor = new JMenuItem("Font color");
        changeFontColor.addActionListener(this);
        changeBackgroundColor = new JMenuItem("Background color");
        changeBackgroundColor.addActionListener(this);
        exitFile = new JMenuItem("Exit"); // maybe add pop-up reminding to save first
        exitFile.addActionListener(this);

        fileMenu.add(openFile);
        fileMenu.add(saveFile);
        editMenu.add(findAndReplace);
        formatMenu.add(changeFontSize);
        formatMenu.add(changeFont);
        formatMenu.add(changeFontColor);
        formatMenu.add(changeBackgroundColor);
        helpMenu.add(exitFile);
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(formatMenu);
        menuBar.add(helpMenu);

        JTextPane textPane = new JTextPane();
        textPane.setEditable(true);
        textPane.setPreferredSize(new Dimension(500, 500));

        JPanel panel = new JPanel();
        panel.add(textPane);

        this.add(panel);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (openFile.equals(source)) {
            System.out.println("Opening file");
        } else if (saveFile.equals(source)) {
            System.out.println("Saving file");
        } else if (findAndReplace.equals(source)) {
            System.out.println("Finding and replacing");
        } else if (changeFontSize.equals(source)) {
            System.out.println("Changing font size");
        } else if (changeFont.equals(source)) {
            System.out.println("Changing font");
        } else if (changeFontColor.equals(source)) {
            System.out.println("Changing font color");
        }
    }
}
