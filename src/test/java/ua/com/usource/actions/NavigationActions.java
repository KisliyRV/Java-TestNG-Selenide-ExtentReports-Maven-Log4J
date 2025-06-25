package ua.com.usource.actions;

import com.codeborne.selenide.WebDriverRunner;
import ua.com.usource.common.core.context.TestContext;
import ua.com.usource.common.enums.Pages;
import ua.com.usource.elements.WebNavigationElements;

/**
 * Class contains action methods for the navigation functionality
 */
public class NavigationActions extends BaseActions {

    private final WebNavigationElements elements = new WebNavigationElements();

    /**
     * Opens the target Website if it has not open yet
     */
    public void openWebsite() {
        logger.info("Initialising WebDriver and opening the website..");
        openUrl(TestContext.getTargetUrl());
    }

    /**
     * Navigates to the defined page
     *
     * @param page page name to navigate on
     */
    public void navigateTo(Pages page) {
        logger.info("Navigate to the page '" + page + "'..");
        switch (page) {
            case ABOUT -> elements.clickAboutLink();
            case SIGN_IN -> elements.clickSignInLink();
            case SIGN_UP -> elements.clickSignUpLink();
            default -> throw new IllegalArgumentException("Not recognized the page with name: '" + page + "'!");
        }
        waitForPageLoadComplete();
    }

    /**
     * Checks if on the defined page
     *
     * @param page expected page
     * @return True if the current URL contains the prefix of the target page, otherwise - false
     */
    public boolean isOnPage(Pages page) {
        boolean result;
        logger.info("Checking on the '" + page + "' page..");
        result = isUrlContainsPrefix(page.getPrefix());
        logger.info("Is on '" + page + "' page? - " + result);
        return result;
    }

    /**
     * Checks if current URL contains the defined prefix
     *
     * @param prefix expected part of URL
     */
    private boolean isUrlContainsPrefix(String prefix) {
        String url = WebDriverRunner.url();
        logger.info("Current URL: " + url);
        return url.contains(prefix);
    }

    /**
     * Authorization logic
     */
    public void loginTestUser(String user, String password) {
        elements.setUsername(user);
        elements.setPassword(password);
        elements.clickButtonLogin();
    }

    /**
     * Logic for exiting your personal account
     */
    public void logoutTestUser() {
        elements.clickButtonUserMenu();
        elements.clickButtonLogout();
    }
}
