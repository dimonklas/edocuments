package utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    public String getCurrentDateTime (String pattern) {
        ZoneId zoneId = ZoneId.of("Europe/Kiev");
        return LocalDateTime.now(zoneId).format(DateTimeFormatter.ofPattern(pattern));
    }

    public String getDateWithShift(String pattern, int days) {
        ZoneId zoneId = ZoneId.of("Europe/Kiev");
        return LocalDateTime.now(zoneId).plusDays(days).format(DateTimeFormatter.ofPattern(pattern));
    }
}
