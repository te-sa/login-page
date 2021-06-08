import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Files;

public class Page extends JFrame implements ActionListener {
    private final JMenuItem openFile;
    private final JMenuItem saveFile;
    private final JMenuItem exitFile;
    private final JMenuItem findInFile;
    private final JMenuItem findAndReplace;
    private final JMenuItem changeFontStyle;
    private final JMenuItem changeFontSize;
    private final JMenuItem changeFont;
    private final JMenuItem changeFontColor;
    private final JMenuItem changeBackgroundColor;
    private final JMenuItem changePassword;
    private final JMenuItem backToLogin;
    private final JMenuItem quitProgram;

    static JTextPane textPane;

    Page() {
        this.setTitle("page");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setResizable(false);

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
        exitFile = new JMenuItem("Exit");
        exitFile.addActionListener(this);
        findInFile = new JMenuItem("Find...");
        findInFile.addActionListener(this);
        findAndReplace = new JMenuItem("Find and replace");
        findAndReplace.addActionListener(this);
        changeFontStyle = new JMenuItem("Font style");
        changeFontStyle.addActionListener(this);
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
        quitProgram = new JMenuItem("Quit");
        quitProgram.addActionListener(this);

        fileMenu.add(openFile);
        fileMenu.add(saveFile);
        fileMenu.add(exitFile);
        editMenu.add(findAndReplace);
        formatMenu.add(changeFontStyle);
        formatMenu.add(changeFontSize);
        formatMenu.add(changeFont);
        formatMenu.add(changeFontColor);
        formatMenu.add(changeBackgroundColor);
        helpMenu.add(changePassword);
        helpMenu.add(backToLogin);
        helpMenu.add(quitProgram);
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(formatMenu);
        menuBar.add(helpMenu);

        textPane = new JTextPane();
        textPane.setEditable(true);
        // make textPane grow if frame is resized
        textPane.setPreferredSize(new Dimension(500, 500));
        textPane.setForeground(Color.BLACK);
        // TODO: fix: if words are too long, they go off the page

        JScrollPane scrollPane = new JScrollPane(textPane, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        JPanel panel = new JPanel();
        // work on finding right border ratio
//        panel.setBorder(new EmptyBorder(20,20,20,20));
        panel.add(scrollPane);

        this.add(panel);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (openFile.equals(source)) openFile();
        else if (saveFile.equals(source)) saveFile();
        else if (exitFile.equals(source)) exitFile();
        else if (findInFile.equals(source)) System.out.println("Finding...");
        else if (findAndReplace.equals(source)) System.out.println("Finding and replacing");
        else if (changeFontStyle.equals(source)) new FontStyler();
        else if (changeFontSize.equals(source)) new FontSizer();
        else if (changeFont.equals(source)) new FontSelector();
        else if (changeFontColor.equals(source)) changeFontColor();
        else if (changeBackgroundColor.equals(source)) changeBackgroundColor();
        else if (changePassword.equals(source)) new PasswordChanger();
        else if (backToLogin.equals(source)) backToLogin();
        else if (quitProgram.equals(source)) quitProgram();
    }


    // ** METHODS CALLED FROM ACTION PERFORMED **

    private void openFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("."));
        fileChooser.addChoosableFileFilter(new TextFileFilter());
        fileChooser.setAcceptAllFileFilterUsed(false);
        int response = fileChooser.showOpenDialog(this);
        if (response == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            // is there a faster way to write files to the text pane?
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
            this.setTitle(file.getName());
        }
    }

    private void saveFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.addChoosableFileFilter(new TextFileFilter());
        fileChooser.setAcceptAllFileFilterUsed(false);
        // https://stackoverflow.com/questions/356671/jfilechooser-showsavedialog-how-to-set-suggested-file-name
        fileChooser.setSelectedFile(new File(this.getTitle()));
        fileChooser.setCurrentDirectory(new File("."));
        int response = fileChooser.showSaveDialog(this);
        boolean validExtension = TextFileFilter.validExtension(TextFileFilter.getExtension(fileChooser.getSelectedFile()));
        if (response == JFileChooser.APPROVE_OPTION) {
            if (validExtension) {
                // TODO: find out how to save and recall changeFont and background color info
                File file = fileChooser.getSelectedFile();
                try {
                    textPane.write(new BufferedWriter(new FileWriter(file)));
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
                JOptionPane.showMessageDialog(this, "File saved successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Extension invalid, file could not be saved.\nValid extensions: .txt, .java, .md", "Warning", JOptionPane.WARNING_MESSAGE);
                saveFile();
            }
        }
    }

    private void exitFile() {
        if (!fileSaved()) {
            if (answer("Are you sure you want to exit the current file without saving?") == JOptionPane.OK_OPTION)
                exit("file");
        } else exit("file");
    }

    private void changeFontColor() {
        // TODO: find out how to color only specific text
        // TODO: fix so color can be changed before text is input
        Color changeFontColor = JColorChooser.showDialog(this, "Color picker", null);
        textPane.setForeground(changeFontColor);
    }

    private void changeBackgroundColor() {
        Color color = JColorChooser.showDialog(this, "Color picker", Color.BLACK);
        textPane.setBackground(color);
    }

    private void backToLogin() {
        if (!fileSaved()) {
            if (answer("Are you sure you want to quit the program and return to login without saving?") == JOptionPane.OK_OPTION)
                exit("to login");
        } else exit("to login");
    }

    private void quitProgram() {
        if (!fileSaved()) {
            if (answer("Are you sure you want to quit the program without saving?") == JOptionPane.OK_OPTION)
                System.exit(0);
        } else System.exit(0);
    }


    // ** HELPER METHODS **

    private boolean fileSaved() {
        if (this.getTitle().equals("page")) return false;
        File f = new File(this.getTitle());
        try {
            return textPane.getText().equals(Files.readString(f.toPath()));
        } catch (IOException exception) {
            exception.printStackTrace();
            return false;
        }
    }

    private int answer(String message) {
        return JOptionPane.showConfirmDialog(
                this,
                message,
                "Warning",
                JOptionPane.OK_CANCEL_OPTION);
    }

    private void exit(String s) {
        this.dispose();
        switch (s) {
            case "to login" -> new LoginPage();
            case "file" -> new Page();
        }
    }
}
