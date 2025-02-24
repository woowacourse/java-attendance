package view;

import domain.Attendance;
import domain.AttendanceBook;
import domain.AttendanceHistory;
import domain.Status;
import domain.Penalty;
import dto.AttendanceData;
import dto.ModifyResult;
import dto.AttendanceCount;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class OutputView {
    private final LocalDate today;

    public OutputView(LocalDate today) {
        this.today = today;
    }

    public void printAttendanceResult(Attendance attendance) {
        System.out.println("\n" + formatDate(attendance.dateAndTime()) + "\n");
    }

    public void printModifiedAttendance(ModifyResult modifyResult) {
        LocalDateTime originalDateAndTime = modifyResult.getOriginalDateAndTime();
        LocalDateTime newDateAndTime = modifyResult.getNewDateAndTime();

        String originalOutput = formatDate(originalDateAndTime);
        String newOutput = formatTimeAndState(newDateAndTime);

        System.out.println("\n" + originalOutput
                + " -> "
                + newOutput
                + " 수정 완료!" + "\n");
    }

    public void printAttendanceHistory(AttendanceBook attendanceBook, String name) {
        System.out.println("\n이번 달 " + name + "의 출석 기록입니다.\n");

        AttendanceData attendanceData = attendanceBook.getAttendanceData(name, today);
        List<Attendance> sortedAttendanceData = getSortedAttendanceData(attendanceData);
        AttendanceCount attendanceCount = Status.getCount(attendanceData);

        System.out.println(formatHistory(sortedAttendanceData) + "\n" +
                formatCount(attendanceCount) + "\n" +
                formatPenalty(attendanceCount));
    }

    public void printPenaltyCrew(AttendanceBook attendanceBook) {
        System.out.println("\n제적 위험자 조회 결과");
        System.out.println(formatPenaltyInfo(attendanceBook));
    }

    private String formatHistory(List<Attendance> sortedAttendanceData) {
        return sortedAttendanceData.stream()
                .map(attendance -> formatDate(attendance.dateAndTime()))
                .collect(Collectors.joining("\n"));
    }

    private List<Attendance> getSortedAttendanceData(AttendanceData attendanceData) {
        return attendanceData.value()
                .stream()
                .sorted(Comparator.comparing(Attendance::getDayOfMonth))
                .toList();
    }

    private String formatStatus(Entry<String, AttendanceHistory> data) {
        AttendanceHistory attendanceHistory = data.getValue();
        String nameAndCount = data.getKey() + ": "
                + "결석 " + attendanceHistory.getOriginalAbsentCount() + "회, "
                + "지각 " + attendanceHistory.getLateCount() + "회 ";
        Penalty penalty = Penalty.from(attendanceHistory.getAbsentCount());
        if (penalty == Penalty.NONE) {
            return nameAndCount;
        }
        nameAndCount += "(" + penalty.getMessage() + ")";
        return nameAndCount;
    }

    private String formatCount(AttendanceCount attendanceCount) {
        return "\n출석: " + attendanceCount.attendanceCount() + "회\n"
                + "지각: " + attendanceCount.lateCount() + "회\n"
                + "결석: " + attendanceCount.absentCount() + "회\n";
    }

    private String formatPenalty(AttendanceCount attendanceCount) {
        Penalty penalty = Penalty.from(attendanceCount.absentCount());
        if (penalty == Penalty.NONE) {
            return "";
        }
        return penalty.getMessage() + " 대상자입니다.\n";
    }

    public void printExceptionMessage(String message) {
        System.out.println(message);
    }

    private String formatDate(LocalDateTime dateAndTime) {
        return dateAndTime.format(DateTimeFormatter.ofPattern("MM월 dd일 "))
                + dateAndTime.getDayOfWeek()
                .getDisplayName(TextStyle.FULL, Locale.KOREAN) + " "
                + formatTimeAndState(dateAndTime);
    }

    private String formatTimeAndState(LocalDateTime dateAndTime) {
        Status status = Status.of(dateAndTime);
        if (status == Status.ABSENCE) {
            return "--:-- " + "(" + status.getMessage() + ")";
        }
        return dateAndTime.format(DateTimeFormatter.ofPattern(
                "HH:mm ", Locale.KOREAN)) + "(" + status.getMessage() + ")";
    }

    private String formatPenaltyInfo(AttendanceBook attendanceBook) {
        StringBuilder result = new StringBuilder();
        for (Entry<String, AttendanceHistory> data : attendanceBook.getSorted()) {
            if (Penalty.from(data.getValue().getAbsentCount()) != Penalty.NONE) {
                result.append("- ").append(formatStatus(data)).append("\n");
            }
        }
        return result.toString();
    }
}
