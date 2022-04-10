package ua.com.usource.actions;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.com.usource.common.core.context.TestContext;
import ua.com.usource.common.core.helpers.WaitHelper;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;

/**
 * Class contains common fields and methods for all actions
 */
public abstract class BaseActions {

    protected static Logger logger = LogManager.getLogger(BaseActions.class);
    protected static WaitHelper waiter = new WaitHelper();

    /**
     * Open browser and navigates to the defined URL.
     * The main starting point for test.
     *
     * @param url the URL to navigate
     */
    protected void openUrl(String url) {
        if(WebDriverRunner.hasWebDriverStarted()) {
            Selenide.clearBrowserCookies();
            Selenide.clearBrowserLocalStorage();
        }
        open(url);
    }

    /**
     * Waits while loading will be completed
     */
    protected static void waitForPageLoadComplete() {
        waitForPageLoadComplete(TestContext.getTimeoutSec());
    }

    /**
     * Waits while loading will be completed
     *
     * @param maxTimeOutSec max period for polling check
     */
    private static void waitForPageLoadComplete(int maxTimeOutSec) {
        logger.info("Waiting while loading..");
        waiter.waitFor(() -> !$x("//*[contains(text(), 'Loading')]").isDisplayed(), maxTimeOutSec);
    }
}
