package view;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.Locale;

public class OutputView {

    private static final String ERROR_PREFIX = "[ERROR] ";
    private static final String DATE_TIME_RECORD_FORMAT = "%d월 %d일 %s %s (%s)%n";

    public void printErrorMessage(IllegalArgumentException e) {
        System.out.println(ERROR_PREFIX + e.getMessage());
    }

    public void printDateTimeRecord(LocalDate date, LocalTime time, String status) {
        System.out.printf(DATE_TIME_RECORD_FORMAT, date.getMonthValue(), date.getDayOfMonth()
            , date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN), time, status);
    }
}
