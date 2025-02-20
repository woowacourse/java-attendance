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

    public void printAddAttendanceCrewName() {
        System.out.println("닉네임을 입력해주세요.");
    }

    public void printAddAttendanceDate() {
        System.out.println("등교 시간을 입력해 주세요.");
    }

    public void printUpdateAttendanceCrewName() {
        System.out.println("출석을 수정하려는 크루의 닉네임을 입력해 주세요.");
    }

    public void printUpdateAttendanceDayOfMonth() {
        System.out.println("수정하려는 날짜(일)을 입력해 주세요.");
    }

    public void printUpdateAttendanceDate() {
        System.out.println("언제로 변경하겠습니까?");
    }

    public void printUpdateAttendanceResult(final AttendanceResponse beforeAttendance,
                                            final AttendanceResponse afterAttendance) {
        final LocalDateTime before = beforeAttendance.attendanceDate();
        final LocalDateTime after = afterAttendance.attendanceDate();
        System.out.printf(System.lineSeparator() + "%02d월 %02d일 %s %s (%s) -> %s (%s) 수정 완료!" + System.lineSeparator(),
                before.getMonthValue(), before.getDayOfMonth(),
                before.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREA),
                formatAttendanceTimeByEmpty(before, beforeAttendance.isEmpty()), beforeAttendance.attendanceStatus().getName(),
                formatAttendanceTimeByEmpty(after, afterAttendance.isEmpty()), afterAttendance.attendanceStatus().getName()
        );
    }

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
        return String.format("%02d월 %02d일 %s %s (%s)", attendanceDate.getMonthValue(),
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
