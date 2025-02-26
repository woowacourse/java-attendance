package view;

import domain.AttendanceStatus;
import domain.ExpulsionStatus;
import dto.AttendanceResponse;
import dto.ExpulsionCrewResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public class OutputView {

    public void printToday(final LocalDate today) {
        System.out.printf(System.lineSeparator() + "오늘은 %d월 %d일 %s입니다. 기능을 선택해주세요." + System.lineSeparator(),
                today.getMonthValue(),
                today.getDayOfMonth(), today.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREA));
    }

    public void printIntroduceOperation() {
        System.out.println("1. 출석 확인");
        System.out.println("2. 출석 수정");
        System.out.println("3. 출석 크루별 출석 기록");
        System.out.println("4. 제적 위험자 확인");
        System.out.println("Q. 종료");
    }

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

    public void printUpdateAttendanceResult(final AttendanceResponse beforeAttendance, final LocalTime localTime, final AttendanceStatus attendanceStatus) {
        final LocalDateTime before = beforeAttendance.attendanceDate();
        System.out.printf(System.lineSeparator() + "%02d월 %02d일 %s %s (%s) -> %s (%s) 수정 완료!" + System.lineSeparator(),
                before.getMonthValue(), before.getDayOfMonth(),
                before.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREA),
                formatAttendanceTimeByEmpty(before, beforeAttendance.isEmpty()),
                beforeAttendance.attendanceStatus().getName(),
                localTime.toString(),
                attendanceStatus.getName()
        );
    }

    public void printAlreadyAttendance() {
        System.out.println("이미 출석을 하였습니다.");
    }

    public void printCrewAttendances(final String crewName, final List<AttendanceResponse> attendanceResponses) {
        System.out.printf("이번 달 %s의 출석 기록입니다.\n", crewName);
        printAttendances(attendanceResponses);
    }

    public void printAttendances(final List<AttendanceResponse> attendanceResponses) {
        final String crewAttendanceHistory = attendanceResponses.stream()
                .sorted((o1, o2) -> o1.attendanceDate().compareTo(o2.attendanceDate()))
                .map(this::formatAttendanceResponse)
                .collect(Collectors.joining("\n"));
        System.out.println(System.lineSeparator() + crewAttendanceHistory);

    }

    public void printExpulsionCrewResponses(final List<ExpulsionCrewResponse> expulsionCrewResponses) {
        System.out.println("제적 위험자 조회 결과");
        final String message = expulsionCrewResponses.stream()
                .map(expulsionCrewResponse -> String.format("- %s: 결석 %d회, 지각 %d회 (%s)", expulsionCrewResponse.name(),
                        expulsionCrewResponse.attendanceStatusCount().get(AttendanceStatus.ABSENCE),
                        expulsionCrewResponse.attendanceStatusCount().get(AttendanceStatus.LATE),
                        expulsionCrewResponse.expulsionStatus().getName()))
                .collect(Collectors.joining(System.lineSeparator()));
        System.out.println(message);
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

    public void printAttendancesStatistics(final Map<AttendanceStatus, Integer> attendanceStatuses, final ExpulsionStatus expulsionStatus) {
        final String statistics = attendanceStatuses.entrySet().stream()
                .map(entry -> String.format("%s: %d회", entry.getKey().getName(), entry.getValue()))
                .collect(Collectors.joining("\n"));
        System.out.println(System.lineSeparator() + statistics);
        printCrewExpulsionStatus(expulsionStatus);
    }

    private void printCrewExpulsionStatus(final ExpulsionStatus expulsionStatus) {
        if (expulsionStatus != ExpulsionStatus.NORMAL) {
            System.out.printf(System.lineSeparator() + "%s 대상자입니다.\n", expulsionStatus.getName());
        }
    }

    public void printExceptionMessage(final String message) {
        System.out.println(message);
    }

    public void printNotAttendanceDay() {
        System.out.println("오늘은 등교일이 아닙니다.");
    }
}
