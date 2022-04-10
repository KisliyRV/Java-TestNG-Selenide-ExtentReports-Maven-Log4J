package ua.com.usource.common.consts;

/**
 * Class contains global constants for tests
 */
public final class Constants {

    private Constants() {
    }

    public static class Driver {
        public static final int DEFAULT_TIMEOUT_SEC = 15;
        public static final int MILLIS_IN_ONE_SECOND = 1000;
    }

    public static class DateFormat {
        public static final String MM_dd_yyyy = "MM/dd/yyyy";
        public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    }
}
