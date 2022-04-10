package ua.com.usource.common.core.context;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.com.usource.common.consts.Constants;

/**
 * Class contains test context methods
 */
public class TestContext {

    protected static Logger logger = LogManager.getLogger(TestContext.class);

    /**
     * Returns Browser name from context
     */
    public static String getBrowserName() {
        return getProperty("browser", "Browser name");
    }

    /**
     * Returns browser version from context
     */
    public static String getBrowserVersion() {
        return getProperty("version", "Browser version");
    }

    /**
     * Returns MAX timeout value (in seconds) from context
     */
    public static int getTimeoutSec() {
        String result = System.getProperty("timeout"); //use 'System.getProperty' directly to prevent huge logs list
        return Integer.parseInt(result == null || result.equals("") ? setProperty("timeout", String.valueOf(Constants.Driver.DEFAULT_TIMEOUT_SEC), "MAX timeout (in seconds)") : result);
    }

    /**
     * Returns Current test scope from context
     */
    public static String getTestScope() {
        return getProperty("test", "Current test scope");
    }

    /**
     * Returns target URL from context
     */
    public static String getTargetUrl() {
        return getProperty("url", "Target URL");
    }

    /**
     * Returns File system separator
     */
    public static String getSystemSeparator() {
        return getProperty("file.separator", "File system separator");
    }

    /**
     * Returns the system property
     *
     * @param property property key for getting from system
     * @param comment  comment for logging
     */
    private static String getProperty(String property, String comment) {
        String result;
        logger.info("Get " + comment + " from context..");
        String value = System.getProperty(property);
        result = (value == null || value.equals("") ? null : value);
        logger.info(comment + ": '" + result + "'");
        return result;
    }

    /**
     * Sets and Returns the system property
     *
     * @param key     property key for setting to system
     * @param value   property value for setting to system
     * @param comment comment for logging
     */
    private static String setProperty(String key, String value, String comment) {
        logger.info("Set " + comment + " to context..");
        System.setProperty(key, value);
        logger.info(comment + ": '" + value + "'");
        return value;
    }
}
