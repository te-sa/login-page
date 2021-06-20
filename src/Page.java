import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.Files;

public class Page extends JFrame implements ActionListener, DocumentListener {
    private final JMenuItem openFile;
    private final JMenuItem saveFile;
    private final JMenuItem exitFile;
    private final JMenuItem undoChange;
    private final JMenuItem redoChange;
    private final JMenuItem findInFile;
    private final JMenuItem findAndReplace;
    private final JMenuItem changeFontStyle;
    private final JMenuItem changeFontColor;
    private final JMenuItem changeBackgroundColor;
    private final JMenuItem changePassword;
    private final JMenuItem backToLogin;
    private final JMenuItem quitProgram;

    // ** COPIED CODE **

    protected UndoAction undoAction;
    protected RedoAction redoAction;
    protected UndoManager undo = new UndoManager();

    private final JLabel wordCounter = new JLabel(0 + " words");
    private final JLabel characterCounter = new JLabel(0 + " characters");

    static JTextArea textArea;

    // TODO: figure out layout
    // TODO: add warning when quitting using Quit Main (Command Q)
    // TODO: add undo and redoChange buttons under Edit menu (https://docs.oracle.com/javase/tutorial/uiswing/components/generaltext.html#filter)
    // TODO: give users the option to change background colors for certain panels // bottomSection.setBackground(new Color(48, 213, 200));

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

        // TODO: add keyboard shortcuts (mnemonics) for JMenuItems
        // code for accelerators: https://stackoverflow.com/questions/17808745/java-swing-inputevent-modifiers-for-various-hardware

        openFile = new JMenuItem("Open");
        openFile.addActionListener(this);
        openFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        saveFile = new JMenuItem("Save");
        saveFile.addActionListener(this);
        saveFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        exitFile = new JMenuItem("Exit");
        exitFile.addActionListener(this);
        exitFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));

        // ** PARTIALLY COPIED CODE **

        undoAction = new UndoAction();
        undoChange = new JMenuItem(undoAction);
        undoChange.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        redoAction = new RedoAction();
        redoChange = new JMenuItem(redoAction);
        redoChange.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));

        findInFile = new JMenuItem("Find...");
        findInFile.addActionListener(this);
        findInFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        findAndReplace = new JMenuItem("Find and replace");
        findAndReplace.addActionListener(this);
        findAndReplace.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
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
        editMenu.add(undoChange);
        editMenu.add(redoChange);
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

        // ** PARTIALLY COPIED CODE **

        textArea.getDocument().addUndoableEditListener(e -> {
            undo.addEdit(e.getEdit());
            undoAction.updateUndoState();
            redoAction.updateRedoState();
        });

        JScrollPane scrollPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setMaximumSize(a4);

        JPanel panel = new JPanel();
        panel.setPreferredSize(a4);
        panel.setMaximumSize(a4);
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
        bottomSection.add(characterCounter);
        bottomSection.add(new JLabel("\t\t\t"));
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
        else if (undoChange.equals(source)) System.out.println("Undoing last change...");
        else if (redoChange.equals(source)) System.out.println("Redoing...");
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

    private boolean textAreaEmpty() {
        return textArea.getText().equals("");
    }

    private boolean notSaved() {
        if (this.getTitle().equals("page")) {
            return !textAreaEmpty();
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

    // inspired by https://www.javatpoint.com/word-count-in-java and https://stackoverflow.com/questions/26720785/using-string-methods-to-count-words

    private void countWordsAndCharacters() {
        String[] words = Page.textArea.getText().split("\\s+");
        // inspired by https://introcs.cs.princeton.edu/java/15inout/GUI.java.html
        if (textAreaEmpty())
            wordCounter.setText(0 + " words"); // need to set this so it doesn't say there is 1 word after deleting text
        else wordCounter.setText(words.length + " words");
        // just resetting text of existing label is much easier than trying to create a new one for every change
        characterCounter.setText(Page.textArea.getText().length() + " characters");
    }

    // ** DOCUMENT LISTENER METHODS **

    @Override
    public void insertUpdate(DocumentEvent e) {
        countWordsAndCharacters();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        countWordsAndCharacters();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        countWordsAndCharacters();
    }

    // ** COPIED THE SECTION BELOW AND ALL CODE ASSOCIATED WITH UNDO/REDO FUNCTIONALITY FROM https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/uiswing/examples/components/TextComponentDemoProject/src/components/TextComponentDemo.java **

    /*
     * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
     *
     * Redistribution and use in source and binary forms, with or without
     * modification, are permitted provided that the following conditions
     * are met:
     *
     *   - Redistributions of source code must retain the above copyright
     *     notice, this list of conditions and the following disclaimer.
     *
     *   - Redistributions in binary form must reproduce the above copyright
     *     notice, this list of conditions and the following disclaimer in the
     *     documentation and/or other materials provided with the distribution.
     *
     *   - Neither the name of Oracle or the names of its
     *     contributors may be used to endorse or promote products derived
     *     from this software without specific prior written permission.
     *
     * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
     * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
     * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
     * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
     * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
     * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
     * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
     * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
     * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
     * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
     * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
     */

    class UndoAction extends AbstractAction {
        public UndoAction() {
            super("Undo");
            setEnabled(false);
        }

        public void actionPerformed(ActionEvent e) {
            try {
                undo.undo();
            } catch (CannotUndoException ex) {
                System.out.println("Unable to undo: " + ex);
                ex.printStackTrace();
            }
            updateUndoState();
            redoAction.updateRedoState();
        }

        protected void updateUndoState() {
            if (undo.canUndo()) {
                setEnabled(true);
                putValue(Action.NAME, undo.getUndoPresentationName());
            } else {
                setEnabled(false);
                putValue(Action.NAME, "Undo");
            }
        }
    }

    class RedoAction extends AbstractAction {
        public RedoAction() {
            super("Redo");
            setEnabled(false);
        }

        public void actionPerformed(ActionEvent e) {
            try {
                undo.redo();
            } catch (CannotRedoException ex) {
                System.out.println("Unable to redo: " + ex);
                ex.printStackTrace();
            }
            updateRedoState();
            undoAction.updateUndoState();
        }

        protected void updateRedoState() {
            if (undo.canRedo()) {
                setEnabled(true);
                putValue(Action.NAME, undo.getRedoPresentationName());
            } else {
                setEnabled(false);
                putValue(Action.NAME, "Redo");
            }
        }
    }
}
