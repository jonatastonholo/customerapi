package br.com.customerapi.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * A util class with Date and Time manipulations
 * @author Jônatas Ribeiro Tonholo
 */
public abstract class ClockUtils {

    /***
     * Return the SQL Timestamp with the current time
     * @return the timestamp of current time
     */
    public static Timestamp getTimestampNow() {
        return new Timestamp(new Date().getTime());
    }

    /***
     * Convert a string date in format yyyy-MM-dd in a SQL Timestamp
     * @param date a string with date in format yyyy-MM-dd
     * @return the converted date to timestamp
     */
    public static Timestamp getTimestampFromStringDate(String date) {
        Timestamp timestamp;
        try {
            timestamp = new Timestamp((new SimpleDateFormat("yyyy-MM-dd").parse(date)).getTime());
        }
        catch ( ParseException pe) {
            timestamp = getTimestampNow();
        }
        return timestamp;
    }

    public static int getAge(Timestamp bornDate) {
        Timestamp now = getTimestampNow();
        return getTimestampNow().getYear() - bornDate.getYear();
    }
}
