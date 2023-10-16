package ua.com.usource.tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.testng.ScreenShooter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestResult;
import org.testng.annotations.*;
import ua.com.usource.actions.NavigationActions;
import ua.com.usource.common.consts.Constants;
import ua.com.usource.common.core.context.TestContext;
import ua.com.usource.common.listeners.TestListener;

import java.lang.reflect.Method;

/**
 * Class contains common methods and fields for all tests
 */
@Listeners({ScreenShooter.class, TestListener.class})
public abstract class BaseTest {

    protected static Logger logger = LogManager.getLogger(BaseTest.class);

    private NavigationActions navigation;

    public BaseTest() {
    }

    /**
     * Creates and returns NavigationActions instance
     */
    public NavigationActions navigation() {
        if (navigation == null) {
            navigation = new NavigationActions();
        }
        return navigation;
    }

    @BeforeSuite(alwaysRun = true)
    public void setUp() {
        logger.info("Starting tests execution..");
        Configuration.browser = TestContext.getBrowserName();
        Configuration.browserVersion = TestContext.getBrowserVersion();
        Configuration.timeout = (long) TestContext.getTimeoutSec() * Constants.Driver.MILLIS_IN_ONE_SECOND;
        Configuration.reportsFolder = "target" + TestContext.getSystemSeparator() + "screenshots";
        ScreenShooter.captureSuccessfulTests = true;
        Configuration.holdBrowserOpen = false;
    }

    @BeforeMethod(alwaysRun = true)
    protected void startTest(Method method) {
        logger.info("Executing test '" + method.getName() + "'..");
    }

    @AfterMethod(alwaysRun = true)
    public void finishTest(Method method, ITestResult result) {
        String resultName = switch (result.getStatus()) {
            case ITestResult.SUCCESS -> "PASSED";
            case ITestResult.FAILURE -> "FAILED";
            case ITestResult.SKIP -> "SKIPPED";
            default -> "UNKNOWN STATE";
        };
        logger.info("Test '" + method.getName() + "' execution has finished with result: " + resultName);
    }

    @AfterSuite(alwaysRun = true)
    public void tearDown() {
        WebDriverRunner.closeWebDriver();
        logger.info("Test execution has finished!");
    }
}
