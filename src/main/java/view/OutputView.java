package view;

import java.util.HashMap;
import java.util.List;
import model.AttendanceCalculator;
import model.AttendanceDateTime;
import model.AttendanceStatus;
import model.StudentPunishment;
import util.LocalDateTimePrintFormatter;

public class OutputView {
    private static final String STATE_FORMATTER = "%s: %d회\n";
    private static final String PRINT_PUNISHMENT_RESULT = "제적 위험자 조회 결과";
    private static final String INTERVIEW_LABEL_FORMATTER = "- %s: 결석 %d회, 지각 %d회 (면담)\n";
    private static final String WARNING_LABEL_FORMATTER = "- %s: 결석 %d회, 지각 %d회 (경고)\n";
    private static final String DISMISSAL_LABEL_FORMATTER = "- %s: 결석 %d회, 지각 %d회 (제적)\n";
    private static final String PARENTHESES_FORMATTER = "( %s )\n";
    private static final String DISMISSAL_SUBJECT = "제적 대상자입니다.";
    private static final String INTERVIEW_SUBJECT = "면담 대상자입니다.";
    private static final String WARNING_SUBJECT = "경고 대상자입니다.";

    public static void printTodayAttendanceResult(AttendanceDateTime attendanceDateTime, AttendanceStatus attendanceStatus) {
        String dateAndTime = LocalDateTimePrintFormatter.createAttendanceResultMessage(attendanceDateTime);
        System.out.printf(dateAndTime);
        System.out.printf(PARENTHESES_FORMATTER,attendanceStatus.getState());
    }

    public static void printSecondMenu(String recordBeforeModify, String localDateTimeFormat3) {
        System.out.println(recordBeforeModify + " -> " + localDateTimeFormat3);
    }

    public static void printAttendanceRecord(List<AttendanceDateTime> record) {
        for (AttendanceDateTime attendanceDateTime : record) {
            System.out.printf(LocalDateTimePrintFormatter.createAttendanceResultMessage(attendanceDateTime));
            System.out.printf(PARENTHESES_FORMATTER, AttendanceCalculator.calculateAttendance(attendanceDateTime,
                    attendanceDateTime.toLocalTime()).getState());
        }
    }

    public static void printResult(HashMap<AttendanceStatus, Integer> studentRecord) {
        int riskLevel = studentRecord.get(AttendanceStatus.ABSENT) + studentRecord.get(AttendanceStatus.LATE) / 3;
        for (AttendanceStatus attendanceStatus : studentRecord.keySet()) {
            System.out.printf(STATE_FORMATTER, attendanceStatus.getState(), studentRecord.get(attendanceStatus));
        }
        if (StudentPunishment.determineDisciplinaryAction(riskLevel) == null) {
            return;
        }
        if (StudentPunishment.determineDisciplinaryAction(riskLevel).equals(StudentPunishment.DISMISSAL)) {
            System.out.println(DISMISSAL_SUBJECT);
        }
        if (StudentPunishment.determineDisciplinaryAction(riskLevel).equals(StudentPunishment.WARNING)) {
            System.out.println(WARNING_SUBJECT);
        }
        if (StudentPunishment.determineDisciplinaryAction(riskLevel).equals(StudentPunishment.INTERVIEW)) {
            System.out.println(INTERVIEW_SUBJECT);
        }
    }

    public static void displayAtRiskStudent() {
        System.out.println(PRINT_PUNISHMENT_RESULT);
    }

    public static void printDismissalSubject(HashMap<AttendanceStatus, Integer> studentRecord, String name) {
        int riskLevel = studentRecord.get(AttendanceStatus.ABSENT) + studentRecord.get(AttendanceStatus.LATE) / 3;
        if (riskLevel >= StudentPunishment.DISMISSAL.getStandard()) {
            System.out.printf(DISMISSAL_LABEL_FORMATTER,name,studentRecord.get(AttendanceStatus.ABSENT),studentRecord.get(AttendanceStatus.LATE));
            return;
        }
        if (riskLevel >= StudentPunishment.INTERVIEW.getStandard()) {
            System.out.printf(INTERVIEW_LABEL_FORMATTER,name,studentRecord.get(AttendanceStatus.ABSENT),studentRecord.get(AttendanceStatus.LATE));
            return;
        }
        if (riskLevel >= StudentPunishment.WARNING.getStandard()) {
            System.out.printf(WARNING_LABEL_FORMATTER,name,studentRecord.get(AttendanceStatus.ABSENT),studentRecord.get(AttendanceStatus.LATE));
        }
    }

}
