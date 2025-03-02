package view;

import domain.Attendance;
import domain.AttendanceStatus;
import domain.Penalty;
import dto.AttendanceCount;
import dto.AttendanceLog;
import dto.ModifyingResult;
import dto.PenaltyCrewsInformation;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

public class OutputView {
    public void showAttendanceResult(Attendance attendance) {
        System.out.println("\n" + formatAttendance(attendance) + "\n");
    }

    public void showModifyingResult(ModifyingResult modifyingResult) {
        System.out.println("\n" + formatAttendance(modifyingResult.originalAttendance()) + " -> " +
                formatTime(modifyingResult.modifiedAttendance()) + " " +
                formatStatus(modifyingResult.modifiedAttendance()) + " 수정 완료!\n"
        );
    }

    public void showAttendanceHistory(AttendanceLog attendanceLog, AttendanceCount attendanceCount) {
        System.out.println("\n이번 달 " + attendanceCount.crewName().value() + "의 출석 기록입니다.\n");
        for (Attendance attendance : attendanceLog.sortedAttendanceLog()) {
            System.out.println(formatAttendance(attendance));
        }
        showCount(attendanceCount);
        showPenalty(attendanceCount);
    }

    public void showCount(AttendanceCount attendanceCount) {
        System.out.println("\n" +
                "출석: " + attendanceCount.attendCount() + "회\n" +
                "지각: " + attendanceCount.lateCount() + "회\n" +
                "결석: " + attendanceCount.absentCount() + "회\n");
    }

    public void showPenalty(AttendanceCount attendanceCount) {
        Penalty penalty = Penalty.from(attendanceCount);
        if (penalty != Penalty.NONE) {
            System.out.println(penalty.getExpression() + " 대상자입니다.\n");
        }
    }

    public void showPenaltyCrews(PenaltyCrewsInformation penaltyCrewsInformation) {
        System.out.println("\n제적 위험자 조회 결과");
        for (AttendanceCount attendanceCount : penaltyCrewsInformation.sortedPenaltyCrewsInformation()) {
            showPenaltyCrew(attendanceCount);
        }
        System.out.println();
    }

    public void showErrorMessage(String message) {
        System.out.println(message);
    }

    private void showPenaltyCrew(AttendanceCount attendanceCount) {
        if (Penalty.from(attendanceCount) == Penalty.NONE) {
            return;
        }
        System.out.println("- " + attendanceCount.crewName().value() + ": " +
                "결석 " + attendanceCount.absentCount() + "회, " +
                "지각 " + attendanceCount.lateCount() + "회 " +
                "(" + Penalty.from(attendanceCount).getExpression() + ")"
        );
    }

    private String formatAttendance(Attendance attendance) {
        return formatDate(attendance.getDate()) + " "
                + formatDayOfWeek(attendance.getDate()) + " "
                + formatTime(attendance) + " "
                + formatStatus(attendance);
    }

    private String formatDate(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern("MM월 dd일", Locale.KOREAN));
    }

    private String formatDayOfWeek(LocalDate date) {
        return date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN);
    }

    private String formatTime(Attendance attendance) {
        if (AttendanceStatus.isAbsentStatus(AttendanceStatus.from(attendance))) {
            return "--:--";
        }
        return attendance.getTime().format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    private String formatStatus(Attendance attendance) {
        return "(" + AttendanceStatus.from(attendance).getExpression() + ")";
    }
}
