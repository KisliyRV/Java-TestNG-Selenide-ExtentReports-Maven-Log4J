package ua.com.usource.elements;

import com.codeborne.selenide.SelenideElement;
import org.testng.Assert;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

/**
 * Class contains element-level methods for the navigation functionality
 */
public class WebNavigationElements extends BaseElements {

    private final SelenideElement aboutPageLink = $x("//a[@href='/about']");
    private final SelenideElement signInPageLink = $x("//a[@href='/login']");
    private final SelenideElement signUpPageLink = $x("//a[@href='/register']");
    private final SelenideElement inputUsername = $("input[name='username']");
    private final SelenideElement inputPassword = $("input[name='password']");
    private final SelenideElement buttonLogin = $("button[class$='btn-block']");
    public SelenideElement buttonUserMenu = $("#userMenu");
    public SelenideElement linkLogout = $("a[class='dropdown-item dropdown-item']");
    

    public void clickAboutLink() {
        logger.info("Clicking the About link");
        aboutPageLink.click();
    }

    public void clickSignInLink() {
        logger.info("Clicking the Sign In link");
        signInPageLink.click();
    }

    public void clickSignUpLink() {
        logger.info("Clicking the Sign Up link");
        signUpPageLink.click();
    }

    public void setUsername(String user) {
        logger.info("Clicking the Email");
        inputUsername.click();
        inputUsername.clear();
        logger.info("Set User");
        inputUsername.sendKeys(user);
    }

    public void setPassword(String password) {
        logger.info("Clicking the Password");
        inputPassword.click();
        inputPassword.clear();
        logger.info("Set Password");
        inputPassword.sendKeys(password);
    }

    public void clickButtonLogin() {
        logger.info("Clicking the Login Button");
        buttonLogin.click();
    }

    public void clickButtonUserMenu() {
        logger.info("Clicking the User Menu");
        buttonUserMenu.click();
    }

    public void clickButtonLogout() {
        logger.info("Clicking the button Logout");
        linkLogout.click();
    }
}
