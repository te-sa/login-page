import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class Page extends JFrame implements ActionListener {
    private final JMenuItem openFile;
    private final JMenuItem changeFileName;
    private final JMenuItem saveFile;
    private final JMenuItem saveAndExitFile;
    private final JMenuItem exitFile;
    private final JMenuItem findAndReplace;
    private final JMenuItem fontStyle;
    private final JMenuItem changeFontSize;
    private final JMenuItem changeFont;
    private final JMenuItem changeFontColor;
    private final JMenuItem changeBackgroundColor;
    private final JMenuItem changePassword;
    private final JMenuItem backToLogin;
    private final JMenuItem exitProgram;

    static final JTextPane textPane = new JTextPane();

    Page() {
        this.setTitle("page");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//        this.setResizable(false);

        JMenuBar menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);

        JMenu fileMenu = new JMenu("File");
        JMenu editMenu = new JMenu("Edit");
        JMenu formatMenu = new JMenu("Format");
        JMenu helpMenu = new JMenu("Help");

        openFile = new JMenuItem("Open");
        openFile.addActionListener(this);
        changeFileName = new JMenuItem("Change file name");
        changeFileName.addActionListener(this);
        saveFile = new JMenuItem("Save");
        saveFile.addActionListener(this);
        saveAndExitFile = new JMenuItem("Save and exit");
        saveAndExitFile.addActionListener(this);
        exitFile = new JMenuItem("Exit");
        exitFile.addActionListener(this);
        findAndReplace = new JMenuItem("Find and replace");
        findAndReplace.addActionListener(this);
        fontStyle = new JMenuItem("Font style");
        fontStyle.addActionListener(this);
        changeFontSize = new JMenuItem("Font size");
        changeFontSize.addActionListener(this);
        changeFont = new JMenuItem("Change font");
        changeFont.addActionListener(this);
        changeFontColor = new JMenuItem("Font color");
        changeFontColor.addActionListener(this);
        changeBackgroundColor = new JMenuItem("Background color");
        changeBackgroundColor.addActionListener(this);
        changePassword = new JMenuItem("Change password");
        changePassword.addActionListener(this);
        backToLogin = new JMenuItem("Log out");
        backToLogin.addActionListener(this);
        exitProgram = new JMenuItem("Log out and exit"); // maybe add pop-up reminding to save first
        exitProgram.addActionListener(this);

        fileMenu.add(openFile);
        fileMenu.add(changeFileName);
        fileMenu.add(saveFile);
        fileMenu.add(saveAndExitFile);
        fileMenu.add(exitFile);
        editMenu.add(findAndReplace);
        formatMenu.add(fontStyle);
        formatMenu.add(changeFontSize);
        formatMenu.add(changeFont);
        formatMenu.add(changeFontColor);
        formatMenu.add(changeBackgroundColor);
        helpMenu.add(changePassword);
        helpMenu.add(backToLogin);
        helpMenu.add(exitProgram);
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(formatMenu);
        menuBar.add(helpMenu);

        textPane.setEditable(true);
        textPane.setPreferredSize(new Dimension(500, 500));
        // TODO: fix: if words are too long, they go off the page

        JScrollPane scrollPane = new JScrollPane(textPane);

        this.add(scrollPane);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (openFile.equals(source)) openFile();
        else if (changeFileName.equals(source)) changeFileName();
        else if (saveFile.equals(source)) saveFile();
        else if (exitFile.equals(source)) exitFile();
            // TODO: add warning: Are you sure you want to exit the current file without saving?
        else if (saveAndExitFile.equals(source)) System.out.println("Saving and exiting file");
        else if (findAndReplace.equals(source)) System.out.println("Finding and replacing");
        else if (fontStyle.equals(source)) new FontStyler();
        else if (changeFontSize.equals(source)) new FontSizer();
        else if (changeFont.equals(source)) new FontSelector();
        else if (changeFontColor.equals(source)) changeFontColor();
        else if (changeBackgroundColor.equals(source)) changeBackgroundColor();
        else if (changePassword.equals(source)) new PasswordChanger();
        else if (backToLogin.equals(source)) backToLogin();
        else if (exitProgram.equals(source)) exitProgram();
    }

    private void openFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("."));
        int response = fileChooser.showOpenDialog(this);
        if (response == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            this.setTitle(file.getName());
            try (BufferedReader in = new BufferedReader(new FileReader(file))) {
                // used code from https://www.techiedelight.com/how-to-read-a-file-using-bufferedreader-in-java/
                StringBuilder content = new StringBuilder();
                String line;
                while ((line = in.readLine()) != null) {
                    content.append(line).append(System.lineSeparator());
                }
                textPane.setText(new String(content));
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }

    private void changeFileName() {
        // TODO: work on this
        // how to know which file is currently being edited?
        File file = new File(this.getTitle());
        // TODO: make JOptionPane to get user input, check userInput for validity, can't change file type (?)
        String answer = JOptionPane.showInputDialog("Enter new file name here: ");
//        file.renameTo();
        File renamed = new File(answer);
        System.out.println("Changing file name to " + answer);
//        file.renameTo(renamed);
//        this.setTitle(renamed.getName());
    }

    private void saveFile() {
        JFileChooser fileChooser = new JFileChooser();
        // https://stackoverflow.com/questions/356671/jfilechooser-showsavedialog-how-to-set-suggested-file-name
        fileChooser.setSelectedFile(new File(this.getTitle()));
        fileChooser.setCurrentDirectory(new File("."));
        int response = fileChooser.showSaveDialog(this);
        if (response == JFileChooser.APPROVE_OPTION) {
            // TODO: find out how to save and recall font and background color info
            File file = fileChooser.getSelectedFile();
            try (BufferedWriter out = new BufferedWriter(new FileWriter(file))) {
                out.write(textPane.getText());
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    private void exitFile() {
        // How to know if file has recently been saved?
        System.out.println("Are you sure you want to exit the current file without saving?");
        this.dispose();
        // not working yet
//        textPane.setText("");
        new Page();
    }

    private void changeFontColor() {
        // TODO: find out how to color only specific text
        // TODO: fix so color can be changed before text is input
        Color fontColor = JColorChooser.showDialog(this, "Color picker", null);
        textPane.setForeground(fontColor);
    }

    private void changeBackgroundColor() {
        Color color = JColorChooser.showDialog(this, "Color picker", Color.BLACK);
        textPane.setBackground(color);
    }

    private void backToLogin() {
        this.dispose();
        new LoginPage();
    }

    private void exitProgram() {
        // how to know if the file has recently been saved?
        int answer = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to exit without saving?",
                "Warning",
                JOptionPane.OK_CANCEL_OPTION);
        if (answer == JOptionPane.OK_OPTION) System.exit(0);
    }
}
