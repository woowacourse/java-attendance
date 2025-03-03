package view;

import domain.Penalty;
import domain.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

public class OutputView {

    public static String getFormattedDayInfo(LocalDate date) {
        return date.getMonthValue() + "월 "
                + date.getDayOfMonth() + "일 "
                + date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN);
    }

    public static void printMenu() {
        System.out.println("""
                1. 출석 확인
                2. 출석 수정
                3. 크루별 출석 기록 확인
                4. 제적 위험자 확인
                Q. 종료
                """);
    }

    public static void printCheckedAttendance(Attendance attendance) {
        System.out.println("\n" + getFormattedCheckedAttendance(attendance) + "\n");
    }

    public static String getFormattedCheckedAttendance(Attendance attendance) {
        String dayInfo = getFormattedDayInfo(attendance.getLocalDate());
        AttendanceStatus status = attendance.getAttendanceStatus();
        if (status == AttendanceStatus.ABSENT)
            return String.format("%s --:-- (%s)", dayInfo, status.getValue());
        String dateTime = attendance.getLocalTime()
                .format(DateTimeFormatter.ofPattern("HH:mm", Locale.KOREA));
        return String.format("%s %s (%s)", dayInfo,dateTime ,status.getValue());
    }

    public static void printModifyResult(ModifyResult modifyResult) {
        String dateTime = modifyResult.getNewAttendance()
                .getLocalTime()
                .format(DateTimeFormatter.ofPattern("HH:mm", Locale.KOREA));
        String status = modifyResult.getNewAttendance()
                .getAttendanceStatus()
                .getValue();
        System.out.println(String.format("%s -> %s (%s) 수정 완료!",getFormattedCheckedAttendance(modifyResult.getOldAttendance()), dateTime, status));
    }

    public static void printCrewAttendanceHistory(Crew crew) {
        System.out.println(getFormattedAttendanceHistory(crew.getAttendanceHistory()));
        System.out.println(getFormattedPenaltyStatusCount(crew));
    }

    private static String getFormattedAttendanceHistory(List<Attendance> attendanceHistory) {
        StringBuilder formattedAttendanceHistory = new StringBuilder();
        for (Attendance attendance : attendanceHistory) {
            formattedAttendanceHistory.append(getFormattedCheckedAttendance(attendance)).append("\n");
        }
        return formattedAttendanceHistory.toString();
    }

    private static String getFormattedPenaltyStatusCount(Crew crew) {
        String formattedPenaltyStatusCount = String.format("출석: %d회\n지각: %d회\n결석: %d회\n\n",crew.getAttendCount(),crew.getLateCount(),crew.getAbsentCount());
        if(crew.getPenalty()!=Penalty.NONE)
            formattedPenaltyStatusCount += String.format("%s 대상자입니다.", crew.getPenalty().getStatus());
        return formattedPenaltyStatusCount;
    }

    public static void printPenaltyReceivedCrew(List<Crew> penaltyReceivedCrew) {
        System.out.println("제적 위험자 조회 결과");
        StringBuilder formattedPenaltyReceivedCrew = new StringBuilder();
        for (Crew crew : penaltyReceivedCrew) {
            formattedPenaltyReceivedCrew.append(
                    String.format("- %s: 결석 %d회, 지각 %d회 (%s)\n",crew.getName(),crew.getAbsentCount(),crew.getLateCount(),crew.getPenalty().getStatus())
            );
        }
        System.out.println(formattedPenaltyReceivedCrew);
    }
}
