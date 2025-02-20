package view;

import domain.Penalty;
import domain.Records;
import domain.StatisticsResult;
import domain.TimeAndStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Locale;

public class OutputView {

    private static final int COUNT_START_YEAR = 2024;
    private static final int COUNT_START_MONTH = 12;
    private static final int COUNT_START_DAY = 1;

    private final String RECORD_FORMAT = "%s %s%n";
    private final String EDIT_FORMAT = "%s %s -> %s 수정 완료!%n";
    private final String DATE_FORMAT = "%d월 %02d일 %s";
    private final String TIME_FORMAT = "%02d:%02d (%s)";
    private final String ABSENCE_FORMAT = "--:-- (결석)";
    private final String RECORD_CHECK_MESSAGE = "이번 달 %s의 출석 기록입니다.%n";
    private final String ATTEND_CHECK_FORMAT = "출석: %s회%n";
    private final String LATENESS_CHECK_FORMAT = "지각: %s회%n";
    private final String ABSENCE_CHECK_FORMAT = "결석: %s회%n";
    private final String PENALTY_FORMAT = "%s 대상자입니다.%n";


    public void printAttendanceRecord(LocalDate localDate, TimeAndStatus timeAndStatus) {
        String date = dateFormatting(localDate);
        String time = timeFormatting(timeAndStatus);

        System.out.printf(RECORD_FORMAT, date, time);
    }

    public void printEditResult(LocalDate localDate, TimeAndStatus before, TimeAndStatus after) {
        String date = dateFormatting(localDate);
        String beforeInfo = timeFormatting(before);
        String afterInfo = timeFormatting(after);

        System.out.printf(EDIT_FORMAT, date, beforeInfo, afterInfo);
    }

    public void printRecords(String name, LocalDate nowDate, Records records) {
        System.out.printf(RECORD_CHECK_MESSAGE, name);

        LocalDate startDate = LocalDate.of(COUNT_START_YEAR, COUNT_START_MONTH, COUNT_START_DAY);
        while (startDate.isBefore(nowDate)) {
            TimeAndStatus status = records.findByDate(startDate);
            if (status == null || status.getStatus() == null) {
                String date = dateFormatting(startDate);
                System.out.printf(RECORD_FORMAT, date, ABSENCE_FORMAT);
                startDate = startDate.plusDays(1);
                continue;
            }
            printAttendanceRecord(startDate, status);
            startDate = startDate.plusDays(1);
        }
    }

    public void printStatistics(int attendanceCount, int latenessCount, int absenceCount) {
        System.out.println();
        System.out.printf(ATTEND_CHECK_FORMAT, attendanceCount);
        System.out.printf(LATENESS_CHECK_FORMAT, latenessCount);
        System.out.printf(ABSENCE_CHECK_FORMAT, absenceCount);
        System.out.println();
        printPenalty(absenceCount, latenessCount);
    }

    public void printPenalty(int absenceCount, int latenessCount) {
        Penalty penaltyResult = Penalty.check(absenceCount, latenessCount);
        if(penaltyResult != Penalty.NONE){
            System.out.printf(PENALTY_FORMAT, penaltyResult.penalty);
        }
    }

    private String timeFormatting(TimeAndStatus timeAndStatus) {
        return String.format(TIME_FORMAT, timeAndStatus.getTime().getHour(),
            timeAndStatus.getTime().getMinute(), timeAndStatus.getStatus());
    }

    private String dateFormatting(LocalDate localDate) {
        return String.format(DATE_FORMAT, localDate.getMonthValue(),
            localDate.getDayOfMonth(),
            localDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN));
    }
}
