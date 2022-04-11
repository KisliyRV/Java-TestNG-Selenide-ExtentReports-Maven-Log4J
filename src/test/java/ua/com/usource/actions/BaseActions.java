package ua.com.usource.actions;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ua.com.usource.common.core.context.TestContext;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.open;

/**
 * Class contains common fields and methods for all actions
 */
public abstract class BaseActions {

    protected static Logger logger = LogManager.getLogger(BaseActions.class);

    /**
     * Open browser and navigates to the defined URL.
     * The main starting point for test.
     *
     * @param url the URL to navigate
     */
    protected void openUrl(String url) {
        if (WebDriverRunner.hasWebDriverStarted()) {
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
        WebDriverWait waitForSpinner = new WebDriverWait(WebDriverRunner.getWebDriver(), Duration.ofSeconds(maxTimeOutSec));
        waitForSpinner.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[contains(text(), 'Loading')]")));
    }
}
