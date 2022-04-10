package ua.com.usource.elements;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.com.usource.common.core.helpers.WaitHelper;

/**
 * Class contains common methods and fields for all element-level classes
 */
public abstract class BaseElements {

    protected static Logger logger = LogManager.getLogger(BaseElements.class);

    protected WaitHelper waiter;

    public BaseElements() {
        waiter = new WaitHelper();
    }

    public abstract void waitForDisplayed();
}
