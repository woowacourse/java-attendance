package view;

import domain.attendance.Attendance;
import domain.attendance.AttendanceStatus;
import domain.attendance.StudentStatus;
import util.DateTimeUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Locale;

import static util.DateTimeUtils.*;

public class OutputView {
    private static final String doubleNewLine = "\n\n";

    public static void printWelcomeMessage() {
        StringBuilder welcomeMessage = new StringBuilder();
        LocalDate today = LocalDate.now();
        welcomeMessage
                .append("오늘은 ")
                .append(today.format(localDayFormatter))
                .append(today.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN))
                .append("입니다. 기능을 선택해 주세요.");
        System.out.println(welcomeMessage);
    }

    public static void printAddAttendance(LocalDateTime attendanceTime, AttendanceStatus status){
        StringBuilder attendResult = new StringBuilder();
        attendResult
                .append(attendanceTime.format(dateTimeformatter))
                .append(attendanceTime.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN))
                .append(attendanceTime.format(dateTimeformatter))
                .append("(").append(status.getStatus()).append(")");
        System.out.println(attendResult);
    }

    public static void printCrewAttendance(String crewName ,Attendance crewAttendanceResult){
        StringBuilder attendResult = new StringBuilder();

        attendResult.append("이번 달 ").append(crewName).append("의 출석 기록입니다.").append(doubleNewLine);
        crewAttendanceResult.getSortedAttendanceResult()
                .forEach(attendanceDate -> attendResult
                        .append(attendanceDate.getAttendanceAt().format(localDayFormatter))
                        .append(attendanceDate.getAttendanceAt().format(dateTimeformatter)));
        attendResult.append(doubleNewLine);

        attendResult.append("출석: ").append(crewAttendanceResult.getAttendanceCount()).append("회\n")
                .append("지각: ").append(crewAttendanceResult.getTardyCount()).append("회\n")
                .append("결석: ").append(crewAttendanceResult.getAbsenceCount()).append("회\n\n");

        System.out.println(attendResult);
        printStatus(crewAttendanceResult);
    }

    private static void printStatus(Attendance crewAttendanceResult){
        if(crewAttendanceResult.getStudentStatus().equals(StudentStatus.NONE)) return;
        System.out.println(crewAttendanceResult.getStudentStatus().getDescription() + " 대상자 입니다.\n");
    }
}
