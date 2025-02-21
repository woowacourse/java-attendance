package view;

import static util.constant.OutputMessage.ABSENCE_RECORD_FORMAT;
import static util.constant.OutputMessage.ATTENDANCE_EDIT_FORMAT;
import static util.constant.OutputMessage.ATTENDANCE_RECORD_FORMAT;
import static util.constant.OutputMessage.CREW_ATTENDANCE_LIST_MESSAGE;
import static util.constant.OutputMessage.DATE_PRINT_FORMAT;
import static util.constant.OutputMessage.PENALTY_FORMAT;
import static util.constant.OutputMessage.TIME_PRINT_FORMAT;
import static util.constant.OutputMessage.TOTAL_ABSENCE_FORMAT;
import static util.constant.OutputMessage.TOTAL_ATTEND_FORMAT;
import static util.constant.OutputMessage.TOTAL_LATENESS_FORMAT;
import static util.constant.OutputMessage.WARNING_CREW_FORMAT;
import static util.constant.OutputMessage.WARNING_CREW_LIST_MESSAGE;
import static util.constant.Value.START_DAY;
import static util.constant.Value.START_MONTH;
import static util.constant.Value.START_YEAR;

import domain.Holiday;
import domain.Penalty;
import domain.Records;
import domain.StatisticsResult;
import domain.TimeAndStatus;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Map;

public class OutputView {

    public void printErrorMessage(IllegalArgumentException e) {
        System.out.println(e.getMessage());
    }

    public void printAttendanceRecord(LocalDate localDate, TimeAndStatus timeAndStatus) {
        String date = dateFormatting(localDate);
        String time = timeFormatting(timeAndStatus);

        System.out.printf(ATTENDANCE_RECORD_FORMAT, date, time);
    }

    public void printEditResult(LocalDate localDate, TimeAndStatus before, TimeAndStatus after) {
        String date = dateFormatting(localDate);
        String beforeInfo = timeFormatting(before);
        String afterInfo = timeFormatting(after);

        System.out.printf(ATTENDANCE_EDIT_FORMAT, date, beforeInfo, afterInfo);
    }

    public void printRecords(String name, LocalDate nowDate, Records records) {
        System.out.printf(CREW_ATTENDANCE_LIST_MESSAGE, name);

        LocalDate startDate = LocalDate.of(START_YEAR, START_MONTH, START_DAY);
        while (startDate.isBefore(nowDate)) {
            TimeAndStatus status = records.findByDate(startDate);
            if (!Holiday.isHoliday(startDate)) {
                if (status == null || status.getStatus() == null) {
                    String date = dateFormatting(startDate);
                    System.out.printf(ATTENDANCE_RECORD_FORMAT, date, ABSENCE_RECORD_FORMAT);
                    startDate = startDate.plusDays(1);
                    continue;
                }
                printAttendanceRecord(startDate, status);
            }
            startDate = startDate.plusDays(1);
        }
    }

    public void printStatistics(int attendanceCount, int latenessCount, int absenceCount,
        Penalty penaltyResult) {
        System.out.println();
        System.out.printf(TOTAL_ATTEND_FORMAT, attendanceCount);
        System.out.printf(TOTAL_LATENESS_FORMAT, latenessCount);
        System.out.printf(TOTAL_ABSENCE_FORMAT, absenceCount);
        System.out.println();
        printPenalty(penaltyResult);
    }

    public void printPenalty(Penalty penaltyResult) {
        if (penaltyResult != Penalty.NONE) {
            System.out.printf(PENALTY_FORMAT, penaltyResult.penalty);
        }
    }

    public void printExpelledWarningResult(Map<String, StatisticsResult> sortedResult) {
        System.out.println(WARNING_CREW_LIST_MESSAGE);
        for (String name : sortedResult.keySet()) {
            StatisticsResult statisticsResult = sortedResult.get(name);
            System.out.printf(WARNING_CREW_FORMAT, name, statisticsResult.getAbsenceCount()
                , statisticsResult.getLatenessCount()
                , statisticsResult.getPenalty().penalty
            );
        }
    }

    private String timeFormatting(TimeAndStatus timeAndStatus) {
        return String.format(TIME_PRINT_FORMAT, timeAndStatus.getTime().getHour(),
            timeAndStatus.getTime().getMinute(), timeAndStatus.getStatus());
    }

    private String dateFormatting(LocalDate localDate) {
        return String.format(DATE_PRINT_FORMAT, localDate.getMonthValue(),
            localDate.getDayOfMonth(),
            localDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN));
    }
}
