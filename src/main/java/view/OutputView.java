package view;

import static controller.AttendanceController.NOW_MONTH;
import static controller.AttendanceController.NOW_YEAR;

import domain.AttendanceStatus;
import domain.Holiday;
import domain.Penalty;
import domain.Crew;
import domain.StatisticsResult;
import domain.DailyRecord;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

public class OutputView {

    private static final String ERROR_PREFIX = "[ERROR] ";
    private static final String ATTENDANCE_RECORD_FORMAT = "%s %s%n";
    private static final String ABSENCE_RECORD_FORMAT = "--:-- (결석)";
    private static final String ATTENDANCE_EDIT_FORMAT = "%s %s -> %s 수정 완료!%n";
    private static final String DATE_PRINT_FORMAT = "%02d월 %02d일 %s";
    private static final String TIME_PRINT_FORMAT = "%02d:%02d (%s)";
    private static final String CREW_ATTENDANCE_LIST_MESSAGE = "이번 달 %s의 출석 기록입니다.%n";
    private static final String TOTAL_ATTEND_FORMAT = "출석: %s회%n";
    private static final String TOTAL_LATENESS_FORMAT = "지각: %s회%n";
    private static final String TOTAL_ABSENCE_FORMAT = "결석: %s회%n";
    private static final String PENALTY_FORMAT = "%s 대상자입니다.%n";
    private static final String WARNING_CREW_LIST_MESSAGE = "제적 위험자 조회 결과";
    private static final String WARNING_CREW_FORMAT = "- %s: 결석 %d회, 지각 %d회 (%s)%n";

    public void printErrorMessage(IllegalArgumentException e) {
        System.out.println(ERROR_PREFIX + e.getMessage());
    }

    public void printAttendanceRecord(LocalDate localDate, DailyRecord dailyRecord) {
        String date = dateFormatting(localDate);
        String time = timeFormatting(dailyRecord);

        System.out.printf(ATTENDANCE_RECORD_FORMAT, date, time);
    }

    public void printEditResult(LocalDate localDate, DailyRecord before, DailyRecord after) {
        String date = dateFormatting(localDate);
        String beforeInfo = timeFormatting(before);
        String afterInfo = timeFormatting(after);

        System.out.printf(ATTENDANCE_EDIT_FORMAT, date, beforeInfo, afterInfo);
    }

    public void printRecords(String name, LocalDate nowDate, Crew crew) {
        System.out.printf(CREW_ATTENDANCE_LIST_MESSAGE, name);

        LocalDate startDate = LocalDate.of(NOW_YEAR, NOW_MONTH, 1);
        startDate.datesUntil(nowDate)
            .filter(date -> Holiday.isWeekDay(date))
            .forEach(date -> printCrewAttendance(date, crew));
    }

    private void printCrewAttendance(LocalDate date, Crew crew) {
        Optional<DailyRecord> record = crew.findRecordByDate(date);
        if (record.isEmpty()) {
            System.out.printf(ATTENDANCE_RECORD_FORMAT
                , dateFormatting(date)
                , ABSENCE_RECORD_FORMAT);
            return;
        }
        printAttendanceRecord(date, record.orElse(null));
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
            System.out.printf(WARNING_CREW_FORMAT
                , name
                , statisticsResult.getCount(AttendanceStatus.ABSENCE)
                , statisticsResult.getCount(AttendanceStatus.LATENESS)
                , statisticsResult.getPenalty().penalty
            );
        }
    }

    private String timeFormatting(DailyRecord dailyRecord) {
        return String.format(TIME_PRINT_FORMAT
            , dailyRecord.getTime().getHour()
            , dailyRecord.getTime().getMinute()
            , dailyRecord.getStatus());
    }

    private String dateFormatting(LocalDate localDate) {
        return String.format(DATE_PRINT_FORMAT
            , localDate.getMonthValue(),
            localDate.getDayOfMonth(),
            localDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN));
    }
}
