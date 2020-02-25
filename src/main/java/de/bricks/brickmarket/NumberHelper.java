package de.bricks.brickmarket;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class NumberHelper {

    /**
     * Gets a String n which is pares to an double.
     * If n is parsable return the double if not return infinity.
     * @param n
     * @return double value parsed from n
     */
    public static double unitStringToDouble(String n) {
        NumberFormat format = NumberFormat.getInstance(Locale.GERMAN);
        Number number = null;
        try {
            number = format.parse(n);
        } catch (ParseException e) {
            return Double.POSITIVE_INFINITY;
        }
        return number.doubleValue();
    }
}
