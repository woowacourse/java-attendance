package view;

import domain.Crew;
import domain.attendance.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.stream.IntStream;

import static domain.attendance.AttendanceStatus.*;
import static util.DateTimeUtils.*;

public class OutputView {
    private static final String newLine = "\n";

    public static void printErrorMessage(String errorMessage){
        System.out.println("[ERROR]" + errorMessage);
    }

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
        attendResult.append("이번 달 ").append(crewName).append("의 출석 기록입니다.").append(newLine);

        IntStream.range(1,NOW_DAY).forEach(day -> addCrewAttendanceResult(attendResult,day,crewAttendanceResult));

        attendResult.append("출석: ").append(crewAttendanceResult.getAttendanceCount()).append("회").append(newLine)
                .append("지각: ").append(crewAttendanceResult.getTardyCount()).append("회").append(newLine)
                .append("결석: ").append(crewAttendanceResult.getAbsenceCount()).append("회").append(newLine);

        System.out.println(attendResult);
        printStudentStatus(crewAttendanceResult);
    }

    private static void addCrewAttendanceResult(StringBuilder attendResult, int day, Attendance crewAttendanceResult) {
        LocalDate findDate = LocalDate.of(NOW_YEAR,NOW_MONTH,day);
        if (crewAttendanceResult.has(findDate)) {
            processAttendanceRecord(attendResult,crewAttendanceResult,day);
            return;
        }
        if(TimeTable.isAttendanceDay(findDate)){
            processAbsenceDay(attendResult,day);
        }
    }

    private static void processAttendanceRecord(StringBuilder attendResult, Attendance crewAttendanceResult, int day){
        AttendanceDate findAttendance = crewAttendanceResult.findByLocalDate(LocalDate.of(NOW_YEAR, NOW_MONTH, day));
        attendResult.append(convertToLocalDayFormat(findAttendance.getAttendanceAt()))
                .append(convertToDateTimeFormat(findAttendance.getAttendanceAt()))
                .append(convertToAttendanceStatus(findAttendance))
                .append(newLine);
    }

    private static void processAbsenceDay(StringBuilder attendResult, int day){
        attendResult.append(convertToLocalDayFormat(LocalDate.of(NOW_YEAR, NOW_MONTH, day)))
                .append("--:--").append(" (" + ABSENCE.getStatus() + ")").append(newLine);
    }

    private static void printStudentStatus(Attendance crewAttendanceResult){
        if(crewAttendanceResult.getStudentStatus().equals(StudentStatus.NONE)) return;
        System.out.println(crewAttendanceResult.getStudentStatus().getDescription() + " 대상자 입니다.");
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

    private static String convertToLocalDayFormat(LocalDate localDate){
        return localDate.format(localDayFormatter);
    }

    private static String convertToDateTimeFormat(LocalDateTime attendanceAt){
        return attendanceAt.format(dateTimeFormatter);
    }

    private static String convertToDayOfWeekKorean(LocalDateTime attendanceAt){
        return attendanceAt.getDayOfWeek().getDisplayName(TextStyle.FULL,Locale.KOREAN);
    }

    private static String convertToAttendanceStatus(AttendanceDate attendanceRecord){

        return " (" + attendanceRecord.getStatus().getStatus() + ") ";
    }
}
