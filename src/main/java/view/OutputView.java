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
        return date.getMonthValue() + "월 " + date.getDayOfMonth() + "일 " + date.getDayOfWeek().getDisplayName(
                TextStyle.FULL, Locale.KOREAN);
    }

    public static void printMenu() {
        System.out.println("1. 출석 확인\n"
                + "2. 출석 수정\n"
                + "3. 크루별 출석 기록 확인\n"
                + "4. 제적 위험자 확인\n"
                + "Q. 종료");
    }

    public static void printCheckedAttendance(Attendance attendance) {
        System.out.println("\n" + getFormattedCheckedAttendance(attendance) + "\n");
    }

    public static String getFormattedCheckedAttendance(Attendance attendance) {
        String formattedCheckedAttendance = "";
        formattedCheckedAttendance += getFormattedDayInfo(attendance.getLocalDate()) + " ";
        if (attendance.getAttendanceStatus() == AttendanceStatus.ABSENT) {
            formattedCheckedAttendance += "--:--" + " (" + attendance.getAttendanceStatus().getValue() + ")";
            return formattedCheckedAttendance;
        }
        formattedCheckedAttendance += attendance.getLocalTime()
                .format(DateTimeFormatter.ofPattern("HH:mm", Locale.KOREA))
                + " (" + attendance.getAttendanceStatus().getValue() + ")";
        return formattedCheckedAttendance;
    }

    public static void printModifyResult(ModifyResult modifyResult) {
        System.out.println(
                getFormattedCheckedAttendance(modifyResult.getOldAttendance())
                        + " -> "
                        + modifyResult.getNewAttendance().getLocalTime()
                        .format(DateTimeFormatter.ofPattern("HH:mm", Locale.KOREA))
                        + " (" + modifyResult.getNewAttendance().getAttendanceStatus().getValue() + ") 수정 완료!"
        );
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
        String formattedPenaltyStatusCount = "";
        formattedPenaltyStatusCount += "출석: " + crew.getAttendCount() + "회\n"
                + "지각: " + crew.getLateCount() + "회\n"
                + "결석: " + crew.getAbsentCount() + "회\n\n";
        if (crew.getPenalty() != Penalty.NONE)
            formattedPenaltyStatusCount += crew.getPenalty().getStatus() + " 대상자입니다.";
        return formattedPenaltyStatusCount;
    }

    public static void printPenaltyReceivedCrew(List<Crew> penaltyReceivedCrew) {
        System.out.println("제적 위험자 조회 결과");
        StringBuilder formattedPenaltyReceivedCrew = new StringBuilder();
        for (Crew crew : penaltyReceivedCrew) {
            formattedPenaltyReceivedCrew.append("- ")
                    .append(crew.getName())
                    .append(": 결석 ")
                    .append(crew.getAbsentCount()).append("회, 지각 ")
                    .append(crew.getLateCount()).append("회 (")
                    .append(crew.getPenalty().getStatus()).append(")\n");
        }
        System.out.println(formattedPenaltyReceivedCrew);
    }
}
