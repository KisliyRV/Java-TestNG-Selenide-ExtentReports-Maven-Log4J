package ua.com.usource.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ua.com.usource.common.enums.Pages;

/**
 * The class contains test methods for the Web functionality
 */
public class FailedTest extends BaseTest {

    @Test(description = "Failed test example")
    public void failedTest() {
        navigation().openWebsite();
        navigation().navigateTo(Pages.SIGN_UP);
        Assert.assertTrue(navigation().isOnPage(Pages.ABOUT), "Test with incorrect logic to be failed");
    }
}
