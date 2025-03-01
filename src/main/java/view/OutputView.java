package view;

import domain.Crew;
import domain.attendance.Attendance;
import domain.attendance.AttendanceDate;
import domain.attendance.AttendanceStatus;
import domain.attendance.StudentStatus;

import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

import static util.DateTimeUtils.*;

public class OutputView {
    private static final String doubleNewLine = "\n\n";

    public static void printWelcomeMessage() {
        String welcomeMessage = "오늘은 " +
                convertToLocalDayFormat(TODAY_DATE_TIME_NOW) +
                convertToDayOfWeekKorean(TODAY_DATE_TIME_NOW) +
                "입니다. 기능을 선택해 주세요.";
        System.out.println(welcomeMessage);
    }

    public static void printAddAttendance(LocalDateTime attendanceTime, AttendanceStatus status){
        String attendResult = convertToDateTimeFormat(attendanceTime) +
                convertToDayOfWeekKorean(attendanceTime) +
                convertToDateTimeFormat(attendanceTime) +
                "(" + status.getStatus() + ")";
        System.out.println(attendResult);
    }

    public static void printCrewAttendance(String crewName ,Attendance crewAttendanceResult){
        StringBuilder attendResult = new StringBuilder();

        List<AttendanceDate> sortedRecord = crewAttendanceResult.getSortedAttendanceResult();
        attendResult.append("이번 달 ").append(crewName).append("의 출석 기록입니다.").append(doubleNewLine);
        sortedRecord.forEach(attendanceDate -> attendResult
                .append(convertToLocalDayFormat(attendanceDate.getAttendanceAt()))
                .append(convertToDateTimeFormat(attendanceDate.getAttendanceAt()))
                .append("\n"));
        attendResult.append(doubleNewLine);

        attendResult.append("출석: ").append(crewAttendanceResult.getAttendanceCount()).append("회\n")
                .append("지각: ").append(crewAttendanceResult.getTardyCount()).append("회\n")
                .append("결석: ").append(crewAttendanceResult.getAbsenceCount()).append("회\n\n");

        System.out.println(attendResult);
        printStudentStatus(crewAttendanceResult);
    }

    private static void printStudentStatus(Attendance crewAttendanceResult){
        if(crewAttendanceResult.getStudentStatus().equals(StudentStatus.NONE)) return;
        System.out.println(crewAttendanceResult.getStudentStatus().getDescription() + " 대상자 입니다.\n");
    }

    public static void printEditResult(AttendanceDate oldRecord, AttendanceDate newRecord){
        LocalDateTime oldDateRecord = oldRecord.getAttendanceAt();
        LocalDateTime newDateRecord = newRecord.getAttendanceAt();

        System.out.println(convertToLocalDayFormat(oldDateRecord) + " " + convertToDayOfWeekKorean(oldDateRecord)
        + convertToDateTimeFormat(oldDateRecord) + convertToAttendanceStatus(oldRecord) + " -> "
                + convertToDateTimeFormat(newDateRecord) + convertToAttendanceStatus(newRecord) + "수정 완료!\n");
    }

    public static void printWarningCrews(List<Crew> warningCrews) {
        System.out.println("재적 위험자 조회 결과");
        warningCrews.forEach(crew -> printWarningCrewDetails(
                crew.getName()
                ,crew.getAttendanceRecord())
        );
    }

    private static void printWarningCrewDetails(String crewName, Attendance recordsDetails){
        System.out.println("- " + crewName + ": " + "결석 " + recordsDetails.getAbsenceCount() + "회, " +
                "지각 " + recordsDetails.getTardyCount() + "회 " +
                convertToStudentStatus(recordsDetails));
    }

    private static String convertToStudentStatus(Attendance recordDetails){
        StudentStatus studentStatus = recordDetails.getStudentStatus();
        return "(" + studentStatus.getDescription() + ")";
    }

    private static String convertToLocalDayFormat(LocalDateTime attendanceAt){
        return attendanceAt.format(localDayFormatter);
    }

    private static String convertToDateTimeFormat(LocalDateTime attendanceAt){
        return attendanceAt.format(dateTimeFormatter);
    }

    private static String convertToDayOfWeekKorean(LocalDateTime attendanceAt){
        return attendanceAt.getDayOfWeek().getDisplayName(TextStyle.FULL,Locale.KOREAN);
    }

    private static String convertToAttendanceStatus(AttendanceDate attendanceRecord){
        return " ( " + attendanceRecord.getStatus() + " ) ";
    }
}
