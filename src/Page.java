import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.Files;

public class Page extends JFrame implements ActionListener, DocumentListener {
    private final JMenuItem openFile;
    private final JMenuItem saveFile;
    private final JMenuItem exitFile;
    private final JMenuItem findInFile;
    private final JMenuItem findAndReplace;
    private final JMenuItem changeFontStyle;
    private final JMenuItem changeFontColor;
    private final JMenuItem changeBackgroundColor;
    private final JMenuItem changePassword;
    private final JMenuItem backToLogin;
    private final JMenuItem quitProgram;

    private final JLabel wordCounter = new JLabel(0 + " words");

    static JTextArea textArea;

    // TODO: figure out layout
    // TODO: add bottom section with word count
    // TODO: add warning when quitting using Quit Main (Command Q)

    Page() {
        this.setTitle("page");
        this.setMinimumSize(new Dimension(500, 500));
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        // using code from https://stackoverflow.com/questions/15449022/show-prompt-before-closing-jframe
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                quitProgram();
            }
        });

        JMenuBar menuBar = new JMenuBar();

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
        editMenu.add(findInFile);
        editMenu.add(findAndReplace);
        formatMenu.add(changeFontStyle);
        formatMenu.add(changeFontColor);
        formatMenu.add(changeBackgroundColor);
        helpMenu.add(changePassword);
        helpMenu.add(backToLogin);
        helpMenu.add(quitProgram);

        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(formatMenu);
        menuBar.add(helpMenu);

        Dimension a4 = new Dimension(595, 842);

        textArea = new JTextArea();
        // from https://www.youtube.com/watch?v=NKjqAQAtq-g
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        // TODO: work on making certain words a certain color (that would only work with a textPane)
        // maybe add basic spell checking that way?
        textArea.setSize(a4);
        textArea.setEditable(true);

        textArea.getDocument().addDocumentListener(this);

        JScrollPane scrollPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setMaximumSize(a4);

        JPanel panel = new JPanel();
        panel.setPreferredSize(a4);
        // work on finding right border ratio
//        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.add(scrollPane);

        // to make textPane grow if frame is resized
        // code from https://stackoverflow.com/questions/2303305/window-resize-event
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                panel.setPreferredSize(new Dimension(getWidth(), getHeight()));
                scrollPane.setPreferredSize(new Dimension(panel.getWidth(), panel.getHeight()));
            }
        });

        // inspired by: https://www.youtube.com/watch?v=NKjqAQAtq-g

        // ** FontSizer **

        SpinnerModel spinnerModel = new SpinnerNumberModel(textArea.getFont().getSize(), 0, 100, 1);
        JSpinner fontSizer = new JSpinner(spinnerModel);
        fontSizer.addChangeListener(e -> textArea.setFont(new Font(textArea.getFont().getFontName(), textArea.getFont().getStyle(), (Integer) fontSizer.getValue())));
        JLabel fontSizerLabel = new JLabel("Font size: ");

        // ** FontSelector **

        String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        JComboBox<String> fontSelector = new JComboBox<>(fonts);
        fontSelector.addActionListener(e -> textArea.setFont(new Font((String) fontSelector.getSelectedItem(), textArea.getFont().getStyle(), textArea.getFont().getSize())));
        fontSelector.getModel().setSelectedItem(textArea.getFont().getFontName());
        JLabel fontSelectorLabel = new JLabel("Font: ");

        JPanel topSection = new JPanel();
        JPanel sizerPanel = new JPanel();
        sizerPanel.add(fontSizerLabel);
        sizerPanel.add(fontSizer);
        JPanel selectorPanel = new JPanel();
        selectorPanel.add(fontSelectorLabel);
        selectorPanel.add(fontSelector);
        topSection.add(sizerPanel);
        topSection.add(selectorPanel);

        JPanel bottomSection = new JPanel();
        // from https://stackoverflow.com/questions/3680221/how-can-i-get-screen-resolution-in-java
        bottomSection.setMaximumSize(new Dimension((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth(), 100));
        bottomSection.setBackground(Color.GREEN);
        bottomSection.add(wordCounter);

        // helpful: https://www.logicbig.com/tutorials/java-swing/panel-menu-bar.html
        JRootPane menuBarSection = new JRootPane(); // needs to be RootPane not Panel to work properly
        menuBarSection.setJMenuBar(menuBar);

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(topSection)
                .addComponent(menuBarSection)
                .addComponent(panel)
                .addComponent(bottomSection)
        );
        layout.setHorizontalGroup(layout.createParallelGroup()
                .addComponent(topSection)
                .addComponent(menuBarSection)
                .addComponent(panel, GroupLayout.Alignment.CENTER) // want to have sheet of paper in the middle, even when resized
                .addComponent(bottomSection)
        );
        this.pack();
        this.setLocationRelativeTo(null);
//        this.setSize(610, 600); // textArea is not at full height this way
        this.setVisible(true);
        // to set cursor to textPane (not fontSizer) https://stackoverflow.com/questions/18908902/set-cursor-on-a-jtextfield
        textArea.requestFocusInWindow();
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
        else if (changeFontColor.equals(source)) changeFontColor();
        else if (changeBackgroundColor.equals(source)) changeBackgroundColor();
        else if (changePassword.equals(source)) new PasswordChanger();
        else if (backToLogin.equals(source)) backToLogin();
        else if (quitProgram.equals(source)) quitProgram();
    }


    // ** METHODS CALLED FROM ACTION PERFORMED **

    private void openFile() {
        // make prompt easier to understand?
        if (notSaved()) {
            if (answer("Do you want to save the changes made to this file before opening another file?") == JOptionPane.OK_OPTION) {
                saveFile();
            } else selectFileToOpen();
        } else selectFileToOpen();
    }

    private void selectFileToOpen() {
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
                textArea.setText(new String(content));
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
                    textArea.write(new BufferedWriter(new FileWriter(file)));
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
        if (notSaved()) {
            if (answer("Are you sure you want to exit the current file without saving?") == JOptionPane.OK_OPTION)
                exit("file");
        } else exit("file");
    }

    private void changeFontColor() {
        // TODO: find out how to color only specific text
        // TODO: fix so color can be changed before text is input
        Color changeFontColor = JColorChooser.showDialog(this, "Color picker", null);
        System.out.println(changeFontColor);
        textArea.setForeground(changeFontColor);
    }

    private void changeBackgroundColor() {
        Color color = JColorChooser.showDialog(this, "Color picker", Color.BLACK);
        textArea.setBackground(color);
    }

    private void backToLogin() {
        if (notSaved()) {
            if (answer("Are you sure you want to quit the program and return to login without saving?") == JOptionPane.OK_OPTION)
                exit("to login");
        } else exit("to login");
    }

    private void quitProgram() {
        if (notSaved()) {
            if (answer("Are you sure you want to quit the program without saving?") == JOptionPane.OK_OPTION)
                System.exit(0);
        } else System.exit(0);
    }


    // ** HELPER METHODS **

    private boolean notSaved() {
        if (this.getTitle().equals("page")) {
            return !textArea.getText().equals("");
        }
        File f = new File(this.getTitle());
        try {
            return !textArea.getText().equals(Files.readString(f.toPath()));
        } catch (IOException exception) {
            exception.printStackTrace();
            return true;
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

    // inspired by https://www.javatpoint.com/word-count-in-java and

    private void countWords() {
        String[] words = Page.textArea.getText().split("\\s+");
        wordCounter.setText(words.length + " words");
    }

    // ** DOCUMENT LISTENER METHODS **

    @Override
    public void insertUpdate(DocumentEvent e) {
        countWords();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        countWords();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        countWords();
    }
}