package view;

import controller.facade.Menu;
import domain.attendance.Attendance;
import domain.date.CustomDate;
import domain.attendance.AttendanceStatus;
import domain.crew.CrewStatus;
import java.time.LocalTime;
import service.dto.AttendanceHistoryResponse;
import service.dto.AttendanceModifyResponse;
import service.dto.DisenrollmentCheckResponse;

import java.time.LocalDateTime;
import java.util.*;
import view.format.CustomDateTimeFormatter;

public class OutputView {

    public void printDateAndMenus() {
        String formattedDate = CustomDateTimeFormatter.formatDateAndDay(CustomDate.now());
        System.out.printf("오늘은 %s입니다. 기능을 선택해 주세요.%n", formattedDate);

        Arrays.stream(Menu.values()).forEach(menu -> {
            System.out.printf("%s. %s%n", menu.getInputValue(), menu.getExpression());
        });
    }

    public void printAttendanceResult(Attendance attendance) {
        LocalDateTime time = attendance.getTime();
        String formattedTime = CustomDateTimeFormatter.formatTime(time);
        String formattedDate = CustomDateTimeFormatter.formatDateAndDay(time);
        System.out.printf("%s %s (%s)\n", formattedDate, formattedTime, attendance.getStatus().getExpression());
    }

    public void printExceptionMessage(String message) {
        System.out.println(message);
    }

    public void recommendModifyFunction(String message) {
        System.out.println(message + " 수정 기능을 이용해주세요.");
    }

    public void printModifyResult(AttendanceModifyResponse response) {
        String formattedBeforeDate = CustomDateTimeFormatter.formatDateAndDay(response.beforeTime());
        String formattedBeforeTime = CustomDateTimeFormatter.formatTime(response.beforeTime());
        String formattedAfterTime = CustomDateTimeFormatter.formatTime(response.afterTime());
        System.out.printf("%s %s (%s) -> %s (%s) 수정 완료!\n",
                formattedBeforeDate,
                formattedBeforeTime,
                response.beforeStatus().getExpression(),
                formattedAfterTime,
                response.afterStatus().getExpression()
        );
    }

    public void printHistoryResult(
            String name,
            List<AttendanceHistoryResponse> histories,
            Map<AttendanceStatus, Integer> attendanceResult,
            CrewStatus crewStatus
    ) {
        System.out.printf("이번 달 %s의 출석 기록입니다.\n", name);
        printHistories(histories);
        printAttendanceCount(attendanceResult);
        printCrewStatus(crewStatus);
    }

    private void printHistories(List<AttendanceHistoryResponse> histories) {
        histories.forEach(response -> {
            String formattedDate = CustomDateTimeFormatter.formatDateAndDay(
                    LocalDateTime.of(response.date(), response.time().orElse(LocalTime.of(0, 0))));
            String formattedTime = getFormattedTime(response);
            String status = response.status().getExpression();
            System.out.printf("%s %s (%s)\n", formattedDate, formattedTime, status);
        });
    }

    private void printAttendanceCount(Map<AttendanceStatus, Integer> attendanceResult) {
        attendanceResult.keySet().forEach(attendanceStatus -> {
            System.out.printf("%s: %d회\n", attendanceStatus.getExpression(), attendanceResult.get(attendanceStatus));
        });
    }

    private void printCrewStatus(CrewStatus crewStatus) {
        System.out.printf("%s 대상자입니다.\n", crewStatus.getExpression());
    }

    public void printDisenrollmentCheckResult(List<DisenrollmentCheckResponse> responses) {
        System.out.println("제적 위험자 조회 결과");
        List<DisenrollmentCheckResponse> sortedResponse = getSortedResponse(responses);
        sortedResponse.forEach(response -> {
            System.out.printf("- %s: 결석 %d회, 지각 %d회 (%s)\n",
                    response.name(),
                    response.absenceCount(),
                    response.lateCount(),
                    response.crewStatus()
            );
        });
    }

    private static String getFormattedTime(AttendanceHistoryResponse response) {
        String formattedTime = "--:--";
        if (response.time().isPresent()) {
            formattedTime = CustomDateTimeFormatter.formatTime(
                    LocalDateTime.of(response.date(), response.time().get()));
        }
        return formattedTime;
    }

    private List<DisenrollmentCheckResponse> getSortedResponse(List<DisenrollmentCheckResponse> responses) {
        return responses.stream()
                .sorted(Comparator.comparing(DisenrollmentCheckResponse::convertedAbsenceCount)
                        .reversed()
                        .thenComparing(DisenrollmentCheckResponse::name))
                .toList();
    }
}
