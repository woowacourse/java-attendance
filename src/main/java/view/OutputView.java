package view;

import domain.AttendanceStatus;
import domain.DailyRecord;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Map;

public class OutputView {

    private static final String ERROR_PREFIX = "[ERROR] ";
    private static final String DATE_TIME_RECORD_FORMAT = "%02d월 %02d일 %s %s (%s)%n";
    private static final String EDITED_RECORD_FORMAT = "%02d월 %02d일 %s %s (%s) -> %s (%s) 수정 완료!%n";
    private static final String CREW_RECORD_START_MESSAGE = "이번 달 %s의 출석 기록입니다.%n";
    private static final String STATUS_RECORD_FORMAT = "%s: %d회%n";

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

    public void printCrewRecords(String name, Map<LocalDate, DailyRecord> records) {
        System.out.println();
        System.out.printf(CREW_RECORD_START_MESSAGE, name);
        for (LocalDate date : records.keySet()) {
            DailyRecord record = records.get(date);
            System.out.printf(DATE_TIME_RECORD_FORMAT, date.getMonthValue(), date.getDayOfMonth(),
                date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN),
                record.getFormattedTime(), record.getStatus().getName());
        }
    }

    public void printStatistics(Map<AttendanceStatus, Integer> statisticsResult) {
        System.out.println();
        for (AttendanceStatus status : statisticsResult.keySet()) {
            System.out.printf(STATUS_RECORD_FORMAT, status.getName(), statisticsResult.get(status));
        }
    }
}