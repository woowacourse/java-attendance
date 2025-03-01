package view;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public enum DateTimeFormat {
    DATE("MM월 dd일 E요일"),
    TIME("hh:mm");

    private final String format;
    private final DateTimeFormatter formatter;

    DateTimeFormat(final String format) {
        this.format = format;
        this.formatter = DateTimeFormatter.ofPattern(format);
    }

    public String formatDate(LocalDate date) {
        return DATE.formatter.format(date);
    }

    public String formatTime(LocalTime time) {
        return TIME.formatter.format(time);
    }
}
