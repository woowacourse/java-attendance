package view;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import model.AttendanceCalculator;
import model.AttendanceStatus;
import model.StudentPunishment;
import util.LocalDateTimePrintFormatter;

public class OutputView {
    private static final String STATE_FORMATTER = "%s: %d회\n";
    private static final String DISMISSAL_SUBJECT = "제적 대상자입니다.";
    private static final String WARNING_SUBJECT = "경고 대상자입니다.";
    private static final String INTERVIEW_SUBJECT = "면담 대상자입니다.";
    private static final String PRINT_PUNISHMENT_RESULT = "제적 위험자 조회 결과";
    private static final String INTERVIEW_LABEL_FORMATTER = "- %s: 결석 %d회, 지각 %d회 (면담)\n";
    private static final String WARNING_LABEL_FORMATTER = "- %s: 결석 %d회, 지각 %d회 (경고)\n";
    private static final String DISMISSAL_LABEL_FORMATTER = "- %s: 결석 %d회, 지각 %d회 (제적)\n";
    private static final String PARENTHESES_FORMATTER = "( %s )\n";

    public static void printTodayAttendanceResult(LocalDateTime localDateTime, AttendanceStatus attendanceStatus) {
        String dateAndTime = LocalDateTimePrintFormatter.LocalDateTimeToLocalTime(localDateTime);
        System.out.printf(dateAndTime);
        System.out.printf(String.format(PARENTHESES_FORMATTER,attendanceStatus.getState()));
    }

    public static void printSecondMenu(String recordBeforeModify, String localDateTimeFormat3) {
        System.out.println(recordBeforeModify + " -> " + localDateTimeFormat3);
    }

    public static void printAttendanceRecord(ArrayList<LocalDateTime> record) {
        for (LocalDateTime localDateTime : record) {
            System.out.printf(LocalDateTimePrintFormatter.LocalDateTimeToLocalTime(localDateTime));
            int day = localDateTime.getDayOfWeek().getValue();
            System.out.printf(String.format(PARENTHESES_FORMATTER, AttendanceCalculator.calculateAttendance(day,
                    LocalTime.from(localDateTime)).getState()));
        }
    }

    public static void printResult(HashMap<String, Integer> studentRecord) {
        int riskLevel = studentRecord.get(AttendanceStatus.ATTENDANCE.getState()) + studentRecord.get(AttendanceStatus.LATE.getState()) / 3;
        for (String state : studentRecord.keySet()) {
            System.out.printf(String.format(STATE_FORMATTER,state,studentRecord.get(state)));
        }
        if (riskLevel >= StudentPunishment.DISMISSAL.getStandard()) {
            System.out.println(DISMISSAL_SUBJECT);
            return;
        }
        if (riskLevel >= StudentPunishment.INTERVIEW.getStandard()) {
            System.out.println(INTERVIEW_SUBJECT);
            return;
        }
        if (riskLevel >= StudentPunishment.WARNING.getStandard()) {
            System.out.println(WARNING_SUBJECT);
        }
    }

    public static void displayAtRiskStudent() {
        System.out.println(PRINT_PUNISHMENT_RESULT);
    }

    public static void printDismissalSubject(HashMap<String, Integer> studentRecord, String name) {
        int riskLevel = studentRecord.get(AttendanceStatus.ABSENT.getState()) + studentRecord.get(AttendanceStatus.LATE.getState()) / 3;
        if (riskLevel >= StudentPunishment.DISMISSAL.getStandard()) {
            System.out.printf(String.format(DISMISSAL_LABEL_FORMATTER,name,studentRecord.get(AttendanceStatus.ABSENT.getState()),studentRecord.get(AttendanceStatus.LATE.getState())));
            return;
        }
        if (riskLevel >= StudentPunishment.INTERVIEW.getStandard()) {
            System.out.printf(String.format(INTERVIEW_LABEL_FORMATTER,name,studentRecord.get(AttendanceStatus.ABSENT.getState()),studentRecord.get(AttendanceStatus.LATE.getState())));
            return;
        }
        if (riskLevel >= StudentPunishment.WARNING.getStandard()) {
            System.out.printf(String.format(WARNING_LABEL_FORMATTER,name,studentRecord.get(AttendanceStatus.ABSENT.getState()),studentRecord.get(AttendanceStatus.LATE.getState())));
        }
    }

}
