import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Page extends JFrame implements ActionListener {
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

        JMenuItem openFile = new JMenuItem("Open");
        openFile.addActionListener(this);
        JMenuItem saveFile = new JMenuItem("Save");
        saveFile.addActionListener(this);
        JMenuItem findAndReplace = new JMenuItem("Find and replace");
        findAndReplace.addActionListener(this);
        JMenuItem changeSize = new JMenuItem("Size");
        changeSize.addActionListener(this);
        JMenuItem changeFont = new JMenuItem("Font");
        changeFont.addActionListener(this);
        JMenuItem changeFontColor = new JMenuItem("Font color");
        changeFontColor.addActionListener(this);
        JMenuItem changeBackgroundColor = new JMenuItem("Background color");
        changeBackgroundColor.addActionListener(this);
        JMenuItem exitFile = new JMenuItem("Exit"); // maybe add pop-up reminding to save first
        exitFile.addActionListener(this);

        fileMenu.add(openFile);
        fileMenu.add(saveFile);
        editMenu.add(findAndReplace);
        formatMenu.add(changeSize);
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

    }
}
