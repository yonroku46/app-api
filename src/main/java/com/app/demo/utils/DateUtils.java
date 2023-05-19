package com.app.demo.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;

@Component
public class DateUtils {

    @Value("${app.datetime.format}")
    private String systemDateFormat;

    private static final String DATE_FORMAT_YMDHMS_HYPHEN = "yyyy-MM-dd HH:mm:ss";
    private static final String DATE_FORMAT_YMDHM_HYPHEN = "yyyy-MM-dd HH:mm";
    private static final String DATE_FORMAT_YMDHM = "yyyy/MM/dd HH:mm";
    private static final String DATE_FORMAT_YMDHMS = "yyyy/MM/dd HH:mm:ss";
    private static final String DATE_FORMAT_YMDHMS_S = "yyyy/MM/dd HH:mm:ss.SSS";
    private static final String DATE_FORMAT_YMDHMS_NOSLASH = "yyyyMMddHHmmss";
    private static final String DATE_FORMAT_YMD_HMS_NOSLASH = "yyyyMMdd_HHmmss";

    public int compare(String datetimeA, String datetimeB, int diff, int unit) {
        return compare(datetimeA, datetimeB, diff, unit, systemDateFormat);
    }

    public int compare(String datetimeA, String datetimeB, int diff, int unit, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        LocalDateTime a = LocalDateTime.parse(datetimeA, formatter);
        LocalDateTime b = LocalDateTime.parse(datetimeB, formatter);

        return compare(a, b, diff, unit);
    }

    public int compare(LocalDateTime datetimeA, LocalDateTime datetimeB, int diff, int unit) {
        LocalDateTime calA = datetimeA;
        LocalDateTime calB = datetimeB.plus(diff, getChronoUnit(unit));
        return calA.compareTo(calB);
    }

    public boolean inRange(LocalDateTime target, LocalDateTime rangeFrom, LocalDateTime rangeTo) {
        return target.compareTo(rangeFrom) >= 0 && target.compareTo(rangeTo) <= 0;
    }

    public static LocalDateTime getFormatDateYmdHm(String dateStr) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(DATE_FORMAT_YMDHM);
        try {
            return LocalDateTime.parse(dateStr, dateFormat);
        } catch (DateTimeParseException ex) {
            return null;
        }
    }

    public LocalDateTime convertSystemDateFormat(String dateStr) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(systemDateFormat);
            return LocalDateTime.parse(dateStr, formatter);
        } catch (DateTimeParseException ex) {
            return null;
        }
    }

    public String getFormatDateYmdHm(LocalDateTime dateTime) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(DATE_FORMAT_YMDHM);
        return dateTime.format(dateFormat);
    }

    public static int getDateDuration(LocalDateTime fromDate, LocalDateTime toDate) {
        Duration duration = Duration.between(fromDate, toDate);
        long minutes = duration.toMinutes();

        BigDecimal dbMinutes = new BigDecimal(minutes);
        BigDecimal ret = dbMinutes.divide(new BigDecimal("60"), 6, BigDecimal.ROUND_HALF_UP)
                .divide(new BigDecimal("24"), 6, BigDecimal.ROUND_HALF_UP);
        return ret.intValue();
    }

    public static LocalDateTime getUTCdatetimeAsDate() {
        String utcTime = getUTCdatetimeAsString();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT_YMDHMS_HYPHEN);
        formatter = formatter.withZone(ZoneId.of("UTC"));
        try {
            return LocalDateTime.parse(utcTime, formatter);
        } catch (DateTimeParseException ex) {
            return null;
        }
    }

    public static String getUTCdatetimeAsString() {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT_YMDHMS_HYPHEN);
        formatter.withZone(ZoneId.of("UTC"));
        final String utcTime = formatter.format(LocalDateTime.now());

        return utcTime;
    }

    private ChronoUnit getChronoUnit(int unit) {
        switch (unit) {
            case Calendar.DATE:
                return java.time.temporal.ChronoUnit.DAYS;
            case Calendar.MONTH:
                return java.time.temporal.ChronoUnit.MONTHS;
            case Calendar.YEAR:
                return java.time.temporal.ChronoUnit.YEARS;
            default:
                throw new IllegalArgumentException("Invalid unit value: " + unit);
        }
    }
}