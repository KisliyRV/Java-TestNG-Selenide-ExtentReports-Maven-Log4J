package ua.com.usource.common.core.helpers;

import ua.com.usource.common.consts.Constants;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;
import java.util.TimeZone;

/**
 * Class contains methods that help to generate data or expected results
 */
public class TestDataHelper {

    private static final String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String nonZeroDigits = "123456789";
    private static final String withZeroDigits = "0" + nonZeroDigits;
    private static final String specials = "/*-+.,~!@#$%^&()_=";
    private static final String allCharacters = letters + letters.toLowerCase() + withZeroDigits;
    private static final String allCharactersIncludeSpecials = allCharacters + specials;

    /**
     * Returns date as formatted string with default format
     *
     * @param date calendar instance
     * @return formatted date as String
     */
    public static String getFormattedDate(Calendar date) {
        return getFormattedDate(date, Constants.DateFormat.MM_dd_yyyy);
    }

    /**
     * Returns date as formatted string
     *
     * @param calendar      calendar instance
     * @param formatPattern pattern string
     * @return formatted date as String
     */
    public static String getFormattedDate(Calendar calendar, String formatPattern) {
        return new SimpleDateFormat(formatPattern).format(calendar.getTime());
    }

    /**
     * Returns random generated string
     */
    public static String getRandomString() {
        return getRandomString(allCharacters, 16);
    }

    /**
     * Returns random number with defined length
     *
     * @param length number length
     */
    public static int getRandomNumber(int length) {
        StringBuilder sb = new StringBuilder();
        sb.append(nonZeroDigits.charAt(new Random().nextInt(nonZeroDigits.length())));
        for (int i = 1; i < length; i++) {
            sb.append(withZeroDigits.charAt(new Random().nextInt(withZeroDigits.length())));
        }
        return Integer.parseInt(sb.toString());
    }

    /**
     * Returns timestamp based on the current date and time
     */
    public static String getTimestamp() {
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DateFormat.YYYYMMDDHHMMSS);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        Calendar calendar = new GregorianCalendar();
        return sdf.format(calendar.getTime());
    }

    /**
     * Returns random generated string
     *
     * @param symbols Initial symbols set
     * @param length  Number of symbols in generated string
     */
    private static String getRandomString(String symbols, int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(symbols.charAt(new Random().nextInt(symbols.length())));
        }
        return sb.toString();
    }
}
