package view;

import domain.AttendanceStatus;
import domain.ExpulsionStatus;
import dto.AttendanceResponse;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public class OutputView {

    public void printAttendanceHistoryTitle(final String crewName) {
        System.out.printf("이번 달 %s의 출석 기록입니다.\n", crewName);
    }

    public void printCrewAttendances(final List<AttendanceResponse> attendanceResponses) {
        final String crewAttendanceHistory = attendanceResponses.stream()
                .sorted((o1, o2) -> o1.attendanceDate().compareTo(o2.attendanceDate()))
                .map(this::formatAttendanceResponse)
                .collect(Collectors.joining("\n"));
        System.out.println(System.lineSeparator() + crewAttendanceHistory);
    }

    private String formatAttendanceResponse(final AttendanceResponse attendanceResponse) {
        final LocalDateTime attendanceDate = attendanceResponse.attendanceDate();
        final String status = attendanceResponse.attendanceStatus().getName();
        return String.format("%d월 %02d일 %s %s (%s)", attendanceDate.getMonthValue(),
                attendanceDate.getDayOfMonth(),
                attendanceDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN),
                formatAttendanceTimeByEmpty(attendanceDate, attendanceResponse.isEmpty()), status);
    }

    private String formatAttendanceTimeByEmpty(final LocalDateTime attendanceDate, final boolean isAttendanceEmpty) {
        if (isAttendanceEmpty) {
            return "--:--";
        }
        return String.format("%02d:%02d", attendanceDate.getHour(), attendanceDate.getMinute());
    }

    public void printAttendancesStatistics(final Map<AttendanceStatus, Integer> attendanceStatuses) {
        final String statistics = attendanceStatuses.entrySet().stream()
                .map(entry -> String.format("%s: %d회", entry.getKey().getName(), entry.getValue()))
                .collect(Collectors.joining("\n"));
        System.out.println(System.lineSeparator() + statistics);
    }

    public void printCrewExpulsionStatus(final ExpulsionStatus expulsionStatus) {
        if (expulsionStatus != ExpulsionStatus.NORMAL) {
            System.out.printf(System.lineSeparator() + "%s 대상자입니다.\n", expulsionStatus.getName());
        }
    }
}
