package view;

import java.time.format.DateTimeFormatter;

public enum DateTimeFormat {
    DATE("MM월 dd일 E요일"),
    TIME("HH:mm");

    private final String dateTimePattern;
    private final DateTimeFormatter dateTimeFormatter;

    DateTimeFormat(String dateTimePattern) {
        this.dateTimePattern = dateTimePattern;
        this.dateTimeFormatter = createDateTimeFormatter();
    }

    private DateTimeFormatter createDateTimeFormatter() {
        return DateTimeFormatter.ofPattern(dateTimePattern);
    }

    public DateTimeFormatter getDateTimeFormatter() {
        return dateTimeFormatter;
    }
}
