package ua.com.usource.elements;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;

/**
 * Class contains element-level methods for the navigation functionality
 */
public class WebNavigationElements extends BaseElements {

    private final SelenideElement aboutPageLink = $x("//a[@href='/about']");
    private final SelenideElement signInPageLink = $x("//a[@href='/login']");
    private final SelenideElement signUpPageLink = $x("//a[@href='/register']");

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
}
