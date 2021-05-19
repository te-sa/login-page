import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Page extends JFrame implements ActionListener {
    JMenuItem openFile;
    JMenuItem saveFile;
    JMenuItem findAndReplace;
    JMenuItem changeFontSize;
    JMenuItem changeFont;
    JMenuItem changeFontColor;
    JMenuItem changeBackgroundColor;
    JMenuItem exitFile;
    JTextPane textPane;

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

        textPane = new JTextPane();
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
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("."));
            int response = fileChooser.showOpenDialog(this);
            if (response == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                this.setTitle(file.getName());
                try (BufferedReader in = new BufferedReader(new FileReader(file))) {
                    textPane.setText(in.readLine());
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
        } else if (saveFile.equals(source)) {
            System.out.println("Saving file");
        } else if (findAndReplace.equals(source)) {
            System.out.println("Finding and replacing");
        } else if (changeFontSize.equals(source)) {
            System.out.println("Changing font size");
        } else if (changeFont.equals(source)) {
            System.out.println("Changing font");
        } else if (changeFontColor.equals(source)) {
            // TODO: find out how to color only specific text
            // TODO: fix so color can be changed before text is input
            Color fontColor = JColorChooser.showDialog(this, "Color picker", Color.BLACK);
            textPane.setForeground(fontColor);
        } else if (changeBackgroundColor.equals(source)) {
            Color color = JColorChooser.showDialog(this, "Color picker", Color.BLACK);
            textPane.setBackground(color);
        } else if (exitFile.equals(source)) {
            // how to know if the file has recently been saved?
            int answer = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to exit without saving?",
                    "Warning",
                    JOptionPane.OK_CANCEL_OPTION);
            if (answer == JOptionPane.OK_OPTION) System.exit(0);
        }
    }
}
