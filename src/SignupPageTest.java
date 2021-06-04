import org.junit.Assert;
import org.junit.Test;

public class SignupPageTest {

    @Test
    public void validPassword() {
        Assert.assertTrue(SignupPage.validPassword("PassWord-34"));
    }

    @Test
    public void invalidPassword() {
        Assert.assertFalse(SignupPage.validPassword("password"));
        Assert.assertFalse(SignupPage.validPassword("Password3"));
        Assert.assertFalse(SignupPage.validPassword("password3"));
        Assert.assertFalse(SignupPage.validPassword(":H:::::g::"));
    }
}