package ua.com.usource.common.core.helpers;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import ua.com.usource.common.consts.Constants;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.StaleElementReferenceException;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * Class contains methods for the wait functionality
 */
public class WaitHelper {

    private static final Logger logger = LogManager.getLogger(WaitHelper.class);

    public WaitHelper() {

    }

    /**
     * Waits for function to return true with default timeout.
     * Returns result of last function execution.
     *
     * @param func - wait function to execute
     * @return true if wait was successful
     */
    public boolean waitFor(Callable<Boolean> func) {
        return waitFor(func, Constants.Driver.DEFAULT_TIMEOUT_SEC);
    }

    /**
     * Waits for function to return true with timeout.
     * Returns result of last function execution.
     *
     * @param func       - wait function to execute
     * @param timeOutSec - execution timeout (in seconds)
     * @return true if wait was successful
     */
    public Boolean waitFor(Callable<Boolean> func, int timeOutSec) {
        logger.debug("Waiting for condition to be true (timeout " + timeOutSec + " seconds)..");
        long startTime = System.currentTimeMillis();
        long endTime = startTime + TimeUnit.SECONDS.toMillis(timeOutSec);

        while (System.currentTimeMillis() <= endTime) {
            try {
                Boolean res = func.call();
                if (res) {
                    logger.debug("Wait condition successfully finished!");
                    return res;
                }
            } catch (Throwable e) {
                logger.debug("Exception occurred during waitFor function execution", e);
            }
        }
        logger.debug("Wait condition still false after " + timeOutSec + " seconds!");
        return false;
    }

    /**
     * Waits for elements appear in collection. Returns true, if elements initialized.
     * This works, because selenide initializes elements, on usage.
     *
     * @param elements - elements collection to initialize
     * @return true if initialization successful
     */
    public boolean waitForElementsToBeInitialized(ElementsCollection elements) {
        logger.info("Wait for elements to be initialized..");
        Callable<Boolean> waitFunc = () -> elements != null && elements.size() > 0;
        boolean res = waitFor(waitFunc, Constants.Driver.DEFAULT_TIMEOUT_SEC);
        if (res) {
            logger.info("Elements successfully initialized!");
        } else {
            logger.info("Elements wasn't initialized in time!");
        }
        return res;
    }

    /**
     * Waits for element appear on the page. Returns true, if element is displayed.
     * This works, because selenide initializes elements, on usage.
     *
     * @param element - element to initialize
     * @return true if initialization successful
     */
    public boolean waitForElementToBeDisplayed(SelenideElement element) {
        logger.info("Wait for element to be displayed..");
        Callable<Boolean> waitFunc = () -> element != null && element.isDisplayed();
        boolean res = waitFor(waitFunc, Constants.Driver.DEFAULT_TIMEOUT_SEC);
        if (res) {
            logger.info("Element successfully displayed!");
        } else {
            logger.info("Element wasn't displayed in time!");
        }
        return res;
    }

    /**
     * Waits for element disappear on the page. Returns false, if element is displayed.
     *
     * @param element - element to wait
     * @return false if element disappear
     */
    public boolean waitForElementDisappear(SelenideElement element) {
        logger.info("Wait for element to be displayed..");
        Callable<Boolean> waitFunc = () -> {
            try {
                return element != null && !element.isDisplayed();
            } catch (StaleElementReferenceException ex) {
                return true;
            }
        };
        boolean res = waitFor(waitFunc, Constants.Driver.DEFAULT_TIMEOUT_SEC);
        if (res) {
            logger.info("Elements successfully displayed!");
        } else {
            logger.info("Elements wasn't displayed in time!");
        }
        return res;
    }

    /**
     * Waits for element text value appear on the page. Returns true, if value is displayed.
     *
     * @param element - element to initialize
     * @return true if initialization successful
     */
    public boolean waitForElementValuePresent(SelenideElement element) {
        logger.info("Wait for element to be displayed..");
        Callable<Boolean> waitFunc = () -> element != null && StringUtils.isNotBlank(element.getText());
        boolean res = waitFor(waitFunc, Constants.Driver.DEFAULT_TIMEOUT_SEC);
        if (res) {
            logger.info("Element text is displayed!");
        } else {
            logger.info("Element text wasn't displayed in time!");
        }
        return res;
    }
}
