package view;

import domain.DailyRecord;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.Locale;

public class OutputView {

    private static final String ERROR_PREFIX = "[ERROR] ";
    private static final String DATE_TIME_RECORD_FORMAT = "%02d월 %02d일 %s %s (%s)%n";
    private static final String EDITED_RECORD_FORMAT = "%02d월 %02d일 %s %s (%s) -> %s (%s) 수정 완료!%n";

    public void printErrorMessage(IllegalArgumentException e) {
        System.out.println();
        System.out.println(ERROR_PREFIX + e.getMessage());
        System.out.println();
    }

    public void printDateTimeRecord(LocalDate date, DailyRecord record) {
        LocalTime time = record.getAttendedTime();
        String status = record.getStatus().getName();

        System.out.printf(DATE_TIME_RECORD_FORMAT, date.getMonthValue(), date.getDayOfMonth(),
            date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN), time, status);
    }

    public void printEditedResult(LocalDate date, DailyRecord oldRecord, DailyRecord newRecord) {
        System.out.printf(EDITED_RECORD_FORMAT, date.getMonthValue(), date.getDayOfMonth(),
            date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN),
            oldRecord.getAttendedTime(), oldRecord.getStatus().getName(),
            newRecord.getAttendedTime(), newRecord.getStatus().getName());
    }
}