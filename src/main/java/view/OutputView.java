package view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.AttendanceDate;
import model.AttendanceStatus;
import model.AttendanceStatusEvaluator;
import model.AttendanceTime;
import model.StudentPunishment;
import util.AttendanceDateAttendanceTimeFormatter;

public class OutputView {
    private static final String STATE_FORMATTER = "%s: %d회\n";
    private static final String PRINT_PUNISHMENT_RESULT = "제적 위험자 조회 결과";
    private static final String INTERVIEW_LABEL_FORMATTER = "- %s: 결석 %d회, 지각 %d회 (면담)\n";
    private static final String WARNING_LABEL_FORMATTER = "- %s: 결석 %d회, 지각 %d회 (경고)\n";
    private static final String DISMISSAL_LABEL_FORMATTER = "- %s: 결석 %d회, 지각 %d회 (제적)\n";
    private static final String PARENTHESES_FORMATTER = " (%s)\n";
    private static final String PARENTHESES_FORMATTER_NO_NEWLINE = " (%s) ";
    private static final String DISMISSAL_SUBJECT = "제적 대상자입니다.";
    private static final String INTERVIEW_SUBJECT = "면담 대상자입니다.";
    private static final String WARNING_SUBJECT = "경고 대상자입니다.";
    private static final String ATTENDANCE_RECORD_THIS_MONTH = "이번 달 %s의 출석 기록입니다.\n";
    private static final String RESULT_INDICATOR = " -> ";

    public static void printAttendanceResult(AttendanceDate attendanceDate, AttendanceTime attendanceTime, AttendanceStatus attendanceStatus) {
        String dateAndTime = AttendanceDateAttendanceTimeFormatter.createAttendanceResultMessage(attendanceDate, attendanceTime);
        System.out.printf(dateAndTime);
        System.out.printf(PARENTHESES_FORMATTER, attendanceStatus.getStatus());
    }

    public static void printModifyDone(String modifyResult) {
        System.out.println(RESULT_INDICATOR + modifyResult);
    }

    public static void printModifyComplete(AttendanceDate attendanceDate, AttendanceTime attendanceTime, AttendanceStatus attendanceStatus, String modifyResult) {
        printSpace();
        System.out.printf(AttendanceDateAttendanceTimeFormatter.createAttendanceResultMessage(attendanceDate, attendanceTime));
        System.out.printf(PARENTHESES_FORMATTER_NO_NEWLINE, attendanceStatus.getStatus());
        printModifyDone(modifyResult);
    }

    public static void printRecordCheck(Map<AttendanceDate, AttendanceTime> studentRecordHistory, String name) {
        printSpace();
        System.out.printf(ATTENDANCE_RECORD_THIS_MONTH, name);
        printSpace();
        List<AttendanceDate> attendanceTimeRecord = new ArrayList<>(studentRecordHistory.keySet());
        Collections.sort(attendanceTimeRecord);

        for (AttendanceDate attendanceDate : attendanceTimeRecord) {
            AttendanceStatus attendanceStatus = AttendanceStatusEvaluator.calculateAttendanceStatus(attendanceDate, studentRecordHistory.get(attendanceDate));
            printAttendanceResult(attendanceDate, studentRecordHistory.get(attendanceDate), attendanceStatus);
        }
        printSpace();
    }


    public static void printResult(Map<AttendanceStatus, Integer> studentRecord) {
        int riskLevel = studentRecord.get(AttendanceStatus.ABSENT) + studentRecord.get(AttendanceStatus.LATE) / 3;
        for (AttendanceStatus attendanceStatus : studentRecord.keySet()) {
            System.out.printf(STATE_FORMATTER, attendanceStatus.getStatus(), studentRecord.get(attendanceStatus));
        }
        if (StudentPunishment.calculatePunishment(riskLevel).equals(StudentPunishment.SAFE)) {
            return;
        }
        if (StudentPunishment.calculatePunishment(riskLevel).equals(StudentPunishment.DISMISSAl)) {
            System.out.println(DISMISSAL_SUBJECT);
        }
        if (StudentPunishment.calculatePunishment(riskLevel).equals(StudentPunishment.WARNING)) {
            System.out.println(WARNING_SUBJECT);
        }
        if (StudentPunishment.calculatePunishment(riskLevel).equals(StudentPunishment.INTERVIEW)) {
            System.out.println(INTERVIEW_SUBJECT);
        }
    }

    public static void displayAtRiskStudent() {
        System.out.println(PRINT_PUNISHMENT_RESULT);
    }

    public static void printDismissalSubject(Map<AttendanceStatus, Integer> studentRecord, String name) {
        int riskLevel = studentRecord.get(AttendanceStatus.ABSENT) + studentRecord.get(AttendanceStatus.LATE) / 3;
        if (riskLevel >= StudentPunishment.DISMISSAl.getAbsenceCount()) {
            System.out.printf(DISMISSAL_LABEL_FORMATTER, name, studentRecord.get(AttendanceStatus.ABSENT), studentRecord.get(AttendanceStatus.LATE));
            return;
        }
        if (riskLevel >= StudentPunishment.INTERVIEW.getAbsenceCount()) {
            System.out.printf(INTERVIEW_LABEL_FORMATTER, name ,studentRecord.get(AttendanceStatus.ABSENT), studentRecord.get(AttendanceStatus.LATE));
            return;
        }
        if (riskLevel >= StudentPunishment.WARNING.getAbsenceCount()) {
            System.out.printf(WARNING_LABEL_FORMATTER, name, studentRecord.get(AttendanceStatus.ABSENT), studentRecord.get(AttendanceStatus.LATE));
        }
    }

    private static void printSpace() {
        System.out.println();
    }
}
