import org.junit.Assert;
import org.junit.Test;

import java.io.File;

public class TextFileFilterTest {

    @Test
    public void getExtension() {
        Assert.assertEquals(".txt", TextFileFilter.getExtension(new File("example.txt")));
        Assert.assertEquals(".md", TextFileFilter.getExtension(new File("example.md")));
        Assert.assertNull(TextFileFilter.getExtension(new File("example")));
    }
}