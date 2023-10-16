package ua.com.usource.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ua.com.usource.common.enums.Pages;

/**
 * The class contains test methods for the Web functionality
 */
public class ExampleTwoTest extends BaseTest {

    @Test(description = "Test example 2")
    public void test2Example() {
        Pages targetPage = Pages.SIGN_IN;

        navigation().openWebsite();
        navigation().navigateTo(targetPage);
        Assert.assertTrue(navigation().isOnPage(targetPage), "Verify that the user is taken to the correct page after clicking the appropriate navigation link");
    }
}
