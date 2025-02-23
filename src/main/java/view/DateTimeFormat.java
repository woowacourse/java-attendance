package view;

import java.time.format.DateTimeFormatter;

public enum DateTimeFormat {
    DATE(DateTimeFormatter.ofPattern("MM월 dd일 E요일")),
    TIME(DateTimeFormatter.ofPattern("HH:mm"));

    private final DateTimeFormatter dateTimeFormatter;

    DateTimeFormat(DateTimeFormatter dateTimeFormatter) {
        this.dateTimeFormatter = dateTimeFormatter;
    }

    public DateTimeFormatter getDateTimeFormatter() {
        return dateTimeFormatter;
    }
}
