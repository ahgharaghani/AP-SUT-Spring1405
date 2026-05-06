package utils;

import models.ThirtyDayDate;

import java.time.format.DateTimeFormatter;

public class BookingIDGenerator {
    private static int counter = 0;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    public static String generateId(ThirtyDayDate currentDate) {
        counter++;
        String dateStr = currentDate.format(DATE_FORMATTER);
        return String.format("#b-%s-%04d", dateStr, counter);
    }
}