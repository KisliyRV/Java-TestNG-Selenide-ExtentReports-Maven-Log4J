package ua.com.usource.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ua.com.usource.common.enums.Pages;

/**
 * This test class contains tests that check Login
 */

public class LoginTest extends BaseTest {

    @Test(description = "Test verify Login page")
    public void testVerifyLoginPage() {
        Pages targetPage = Pages.SIGN_IN;

        navigation().openWebsite();
        navigation().navigateTo(targetPage);
        Assert.assertTrue(navigation().isOnPage(targetPage), "Verify that the user is taken to the correct page after clicking the appropriate navigation link");
    }

    @Test(description = "Test login for Test user")
    public void testLoginTestUser() {
        navigation().loginTestUser("loginer@usource.com.ua", "TY#pUEcGz7nN%b");
        Assert.assertTrue(navigation().isOnPage(Pages.ABOUT), "Test with incorrect logic to be failed");
    }

    @Test(description = "Test logout for Test user")
    public void testLogoutTestUser() {
        navigation().logoutTestUser();
        Assert.assertTrue(navigation().isOnPage(Pages.SIGN_IN), "Verify that the user is taken to the correct page after clicking the appropriate navigation link");
    }

    @Test(description = "Authorization with incorrect email")
    public void testFiledUsernameLoginTestUser() {
        Pages targetPage = Pages.SIGN_IN;

        navigation().openWebsite();
        navigation().navigateTo(targetPage);
        navigation().loginTestUser("11@usource.com.ua", "TY#pUEcGz7nN%b");
        Assert.assertTrue(navigation().isOnPage(Pages.ABOUT), "Test with incorrect logic to be failed");
    }

    @Test(description = "Authorization with incorrect password")
    public void testFiledPasswordTestUser() {
        Pages targetPage = Pages.SIGN_IN;

        navigation().openWebsite();
        navigation().navigateTo(targetPage);
        navigation().loginTestUser("loginer@usource.com.ua", "111");
        Assert.assertTrue(navigation().isOnPage(Pages.ABOUT), "Test with incorrect logic to be failed");
    }
}
