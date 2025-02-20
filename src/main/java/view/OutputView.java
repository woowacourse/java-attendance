package view;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.AttendanceStatus;
import model.Student;
import model.StudentPunishment;
import model.StudentRepository;
import util.LocalDateTimePrintFormatter;

public class OutputView {
    private static final String COUNT = "회";
    private static final String ATTENDANCE = "출석: ";
    private static final String LATE = "지각: ";
    private static final String ABSENT = "결석: ";
    private static final String DISMISSAL_SUBJECT = "제적 대상자입니다.";
    private static final String WARNING_SUBJECT = "제적 대상자입니다.";
    private static final String INTERVIEW_SUBJECT = "제적 대상자입니다.";
    private static final String INTERVIEW_LABEL_FORMATTER = "- %s: 결석 %d회, 지각 %d회 (면담)\n";
    private static final String WARNING_LABEL_FORMATTER = "- %s: 결석 %d회, 지각 %d회 (경고)\n";
    private static final String DISMISSAL_LABEL_FORMATTER = "- %s: 결석 %d회, 지각 %d회 (제적)\n";
    private static final String PARENTHESES_FORMATTER = "( %s )\n";




    public static void printTodayAttendanceResult(Student student, LocalDateTime localDateTime) {
        for (LocalDateTime localDateTimeIn : student.getRecord().keySet()) {
            makeLocalDateTimeFormatAndPrint(student, localDateTime, localDateTimeIn);
        }
    }

    private static void makeLocalDateTimeFormatAndPrint(Student student, LocalDateTime localDateTime, LocalDateTime localDateTimeIn) {
        if (localDateTimeIn.isEqual(localDateTime)) {
            String dateAndTime = LocalDateTimePrintFormatter.LocalDateTimeToLocalTime(localDateTimeIn);
            String state = student.getRecord().get(localDateTimeIn).getState();
            System.out.printf(dateAndTime);
            System.out.printf(String.format(PARENTHESES_FORMATTER,state));
        }
    }

    public static void printSecondMenu(String recordBeforeModify, String localDateTimeFormat3) {
        System.out.println(recordBeforeModify + " -> " + localDateTimeFormat3);
    }

    public static void printAttendanceRecord(HashMap<LocalDateTime, AttendanceStatus> record) {
        List<Map.Entry<LocalDateTime, AttendanceStatus>> entries =
                record.entrySet().stream()
                        .sorted(Map.Entry.comparingByKey())
                        .toList();
        for (Map.Entry<LocalDateTime,AttendanceStatus> entry : entries) {
            System.out.printf(LocalDateTimePrintFormatter.LocalDateTimeToLocalTime(entry.getKey()));
            System.out.printf(String.format(PARENTHESES_FORMATTER, entry.getValue().getState()));
        }
    }

    public static void printStudentState(Student student) {
        System.out.println(ATTENDANCE + student.getAttendance() + COUNT);
        System.out.println(LATE + student.getLate() + COUNT);
        System.out.println(ABSENT + student.getAbsent() + COUNT);
    }

    public static void printStudentPunishmentLabel(Student student) {
        if (student.calculateAbsent() > StudentPunishment.DISMISSAL.getStandard()) {
            System.out.println(DISMISSAL_SUBJECT);
            return;
        }
        if (student.calculateAbsent() >= StudentPunishment.INTERVIEW.getStandard()) {
            System.out.println(INTERVIEW_SUBJECT);
            return;
        }
        if (student.calculateAbsent() >= StudentPunishment.WARNING.getStandard()) {
            System.out.println(WARNING_SUBJECT);
        }
    }

    public static void printEveryStudentPunishmentLabel(StudentRepository studentRepository) {
        for (Student student : studentRepository.getStudents()) {
            printStudentPunishmentLabelAndPrint(student);
        }
    }

    private static void printStudentPunishmentLabelAndPrint(Student student) {
        if (student.calculateAbsent() > StudentPunishment.DISMISSAL.getStandard()) {
            System.out.printf(String.format(DISMISSAL_LABEL_FORMATTER,student.getName(),student.getAbsent(),student.getLate()));
            return;
        }
        if (student.calculateAbsent() >= StudentPunishment.INTERVIEW.getStandard()) {
            System.out.printf(String.format(INTERVIEW_LABEL_FORMATTER,student.getName(),student.getAbsent(),student.getLate()));
            return;
        }
        if (student.calculateAbsent() >= StudentPunishment.WARNING.getStandard()) {
            System.out.printf(String.format(WARNING_LABEL_FORMATTER,student.getName(),student.getAbsent(),student.getLate()));
        }
    }

}
