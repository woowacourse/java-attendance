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

    public static void printTodayAttendanceResult(Student student, LocalDateTime localDateTime) {
        for (LocalDateTime localDateTimeIn : student.getRecord().keySet()) {
            makeLocalDateTimeFormatAndPrint(student, localDateTime, localDateTimeIn);
        }
    }

    private static void makeLocalDateTimeFormatAndPrint(Student student, LocalDateTime localDateTime, LocalDateTime localDateTimeIn) {
        if (localDateTimeIn.isEqual(localDateTime)) {
            String dateAndTime = LocalDateTimePrintFormatter.LocalDateTimeToLocalTime(localDateTimeIn);
            String state = student.getRecord().get(localDateTimeIn).getState();
            System.out.println(dateAndTime + "(" + state +")");
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
            System.out.println(LocalDateTimePrintFormatter.LocalDateTimeToLocalTime(entry.getKey()) + "(" +entry.getValue().getState() + ")");
        }
    }

    public static void printStudentState(Student student) {
        System.out.println("출석: " + student.attendance + "회");
        System.out.println("지각" + student.late + "회");
        System.out.println("결석" + student.absent + "회");
    }

    public static void printStudentPunishmentLabel(Student student) {
        if (student.calculateAbsent() > StudentPunishment.DISMISSAL.getStandard()) {
            System.out.println("제적 대상자입니다.");
            return;
        }
        if (student.calculateAbsent() >= StudentPunishment.INTERVIEW.getStandard()) {
            System.out.println("면담 대상자입니다.");
            return;
        }
        if (student.calculateAbsent() >= StudentPunishment.WARNING.getStandard()) {
            System.out.println("경고 대상자입니다.");
        }
    }

    public static void printEveryStudentPunishmentLabel(StudentRepository studentRepository) {
        for (Student student : studentRepository.getStudents()) {
            printStudentPunishmentLabelAndPrint(student);
        }
    }

    private static void printStudentPunishmentLabelAndPrint(Student student) {
        if (student.calculateAbsent() > StudentPunishment.DISMISSAL.getStandard()) {
            System.out.println("- " + student.getName() + ": 결석" + student.getAbsent() + "회, 지각 " + student.getLate() + "회 (" + StudentPunishment.DISMISSAL.getPunishmentLabel() + ")" );
            return;
        }
        if (student.calculateAbsent() >= StudentPunishment.INTERVIEW.getStandard()) {
            System.out.println("- " + student.getName() + ": 결석" + student.getAbsent() + "회, 지각 " + student.getLate() + "회 (" + StudentPunishment.INTERVIEW.getPunishmentLabel() + ")" );
            return;
        }
        if (student.calculateAbsent() >= StudentPunishment.WARNING.getStandard()) {
            System.out.println("- " + student.getName() + ": 결석" + student.getAbsent() + "회, 지각 " + student.getLate() + "회 (" + StudentPunishment.WARNING.getPunishmentLabel() + ")" );
        }
    }

}
