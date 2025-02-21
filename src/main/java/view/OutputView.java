package view;

import domain.Attendance;
import domain.AttendanceBook;
import domain.AttendanceStatus;
import domain.WarningStatus;
import dto.AttendanceData;
import dto.ModifyResult;
import dto.AttendanceCount;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

public class OutputView {
    private final LocalDate today;

    public OutputView(LocalDate today) {
        this.today = today;
    }

    public void printPenaltyCrew(AttendanceBook attendanceBook) {
        System.out.println("\n제적 위험자 조회 결과");
        System.out.println(attendanceBook.printAllCrewWarningInfo(today));
    }

    public void printAttendanceHistory(AttendanceBook attendanceBook, String name) {
        System.out.println("\n이번 달 " + name + "의 출석 기록입니다.\n");

        AttendanceData attendanceData = attendanceBook.getAttendanceData(name, today);
        StringBuilder history = new StringBuilder();
        for (Attendance attendance : attendanceData.getValue()) {
            history.append(getFormatted(attendance.dateAndTime())).append("\n");
        }
        AttendanceCount attendanceCount = AttendanceStatus.getCount(attendanceData);
        history.append("\n")
                .append(getFormattedCount(attendanceCount)).append("\n")
                .append(getFormattedWarningStatus(attendanceCount)).append("\n");
        System.out.println(history);
    }

    public String getFormattedCount(AttendanceCount attendanceCount) {
        return "출석: " + attendanceCount.getAttendanceCount() + "회\n"
                + "지각: " + attendanceCount.getLateCount() + "회\n"
                + "결석: " + attendanceCount.getAbsentCount() + "회\n";
    }

    public String getFormattedWarningStatus(AttendanceCount attendanceCount) {
        WarningStatus warningStatus = WarningStatus.from(attendanceCount.getAbsentCount());
        if (warningStatus == WarningStatus.NONE) {
            return "";
        }
        return warningStatus + " 대상자입니다.";
    }

    public void printAttendanceResult(Attendance attendance) {
        System.out.println("\n" + getFormatted(attendance.dateAndTime()) + "\n");
    }

    public void printModifiedAttendance(ModifyResult modifyResult) {
        LocalDateTime originalDateAndTime = modifyResult.getOriginalDateAndTime();
        LocalDateTime newDateAndTime = modifyResult.getNewTime();

        String originalOutput = getFormatted(originalDateAndTime);
        String newOutput = getFormattedTimeAndState(newDateAndTime);

        System.out.println("\n" + originalOutput
                + " -> "
                + newOutput
                + " 수정 완료!" + "\n");
    }

    public void printExceptionMessage(String message) {
        System.out.println(message);
    }

    private String getFormatted(LocalDateTime dateAndTime) {
        return dateAndTime.format(DateTimeFormatter.ofPattern("MM월 dd일 "))
                + dateAndTime.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN) + " "
                + getFormattedTimeAndState(dateAndTime);
    }

    private String getFormattedTimeAndState(LocalDateTime dateAndTime) {
        AttendanceStatus attendanceStatus = AttendanceStatus.of(dateAndTime);
        if (attendanceStatus == AttendanceStatus.ABSENCE) {
            return "--:-- " + "(" + attendanceStatus.getResult() + ")";
        }
        return dateAndTime.format(DateTimeFormatter.ofPattern("HH:mm ", Locale.KOREAN)) + "("
                + attendanceStatus.getResult() + ")";
    }
}
