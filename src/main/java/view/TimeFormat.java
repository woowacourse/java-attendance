package view;

import java.time.format.DateTimeFormatter;

public class TimeFormat {
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    private TimeFormat() {
    }
}
