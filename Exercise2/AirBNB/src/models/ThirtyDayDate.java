package models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ThirtyDayDate {
    private int year;
    private int month;
    private int day;

    public ThirtyDayDate(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public int getYear() {
        return this.year;
    }

    public int getMonthValue() {
        return this.month;
    }

    public int getDayOfMonth() {
        return this.day;
    }

    public static ThirtyDayDate parse(String date, DateTimeFormatter formatter) {
        Pattern pattern = Pattern.compile("(\\d{4})/(\\d{2})/(\\d{2})");
        Matcher matcher = pattern.matcher(date);

        matcher.matches();
        int year = Integer.parseInt(matcher.group(1));
        int month = Integer.parseInt(matcher.group(2));
        int day = Integer.parseInt(matcher.group(3));

        if (month > 12 || month < 1) return null;
        if (day > 30 || day < 1) return null;

        return new ThirtyDayDate(year, month, day);
    }

    public String format(DateTimeFormatter formatter) {
        int validDay = Math.min(this.day, 28);
        LocalDate tempDate = LocalDate.of(this.year, this.month, validDay);

        String formatted = tempDate.format(formatter);
        String dayStr = String.format("%02d", validDay);
        String actualDayStr = String.format("%02d", this.day);

        return formatted.replace(dayStr, actualDayStr);
    }

    public boolean isBefore(ThirtyDayDate other) {
        if (this.year != other.year) {
            return this.year < other.year;
        }
        if (this.month != other.month) {
            return this.month < other.month;
        }
        return this.day < other.day;
    }

    public boolean isAfter(ThirtyDayDate other) {
        if (this.year != other.year) {
            return this.year > other.year;
        }
        if (this.month != other.month) {
            return this.month > other.month;
        }
        return this.day > other.day;
    }

}
