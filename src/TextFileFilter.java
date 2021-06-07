import javax.swing.filechooser.FileFilter;
import java.io.File;

// adapted code from https://docs.oracle.com/javase/tutorial/uiswing/examples/components/FileChooserDemo2Project/src/components/ImageFilter.java

public class TextFileFilter extends FileFilter {
    @Override
    public boolean accept(File f) {
        if (f.isDirectory()) return true;
        return validExtension(getExtension(f));
    }

    public static boolean validExtension(String ex) {
        if (ex != null) {
            return ex.equals(".txt") || ex.equals(".java")  || ex.equals(".md");
        }
        return false;
    }

    public static String getExtension(File f) {
        String filename = f.getName();
        String ex = null;
        for (int i = 0; i < filename.length(); i++) {
            if (filename.charAt(i) == '.') {
                ex = filename.substring(i);
                break;
            }
        }
        return ex;
    }

    @Override
    public String getDescription() {
        return "Selected text files";
    }
}
