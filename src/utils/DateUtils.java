package utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtils {
    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    public static long daysUntil30DayDates(LocalDate start, LocalDate end) {
        long startDays = start.getYear() * 365L + start.getMonthValue() * 30L + start.getDayOfMonth();
        long endDays = end.getYear() * 365L + end.getMonthValue() * 30L + end.getDayOfMonth();
        return endDays - startDays;
    }
}
