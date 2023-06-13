package utils;

import com.mindsmiths.calendarAdapter.api.Timeslot;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

public class TimeFormatUtils {

    public static String formatTimeslot(Timeslot timeslot, String timeZone) {
        return toZonedDateTime(timeslot.getStart(), timeZone).format(DateTimeFormatter.ofPattern("h:mm")) + " - "
                + toZonedDateTime(timeslot.getEnd(), timeZone).format(DateTimeFormatter.ofPattern("h:mm a"));
    }

    public static String formatDate(LocalDateTime dateTime, String timeZone) {
        ZonedDateTime zonedDateTime = toZonedDateTime(dateTime, timeZone);
        return DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).withLocale(Locale.US).format(zonedDateTime.toLocalDate());
    }

    public static String formatDate(LocalDate date) {
        return DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).withLocale(Locale.US).format(date);
    }

    public static String formatDatetime(LocalDateTime dateTime, String timeZone) {
        return formatDate(dateTime, timeZone) + ", " + formatTime(dateTime, timeZone);
    }

    public static String formatTime(LocalDateTime dateTime, String timeZone) {
        ZonedDateTime zonedDateTime = toZonedDateTime(dateTime, timeZone);
        return DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)
                .withLocale(Locale.US).format(zonedDateTime.toLocalTime());
    }

    public static ZonedDateTime toZonedDateTime(LocalDateTime localDateTime, String timeZone) {
        return localDateTime.atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.of(timeZone));
    }
}
