package view;

import controller.Menu;
import domain.Attendance;
import domain.AttendanceCustomDate;
import domain.AttendanceStatus;
import domain.CrewStatus;
import service.dto.AttendanceHistoryResponse;
import service.dto.AttendanceModifyResponse;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class OutputView {

    public void printDateAndMenus() {
        String formattedDate = AttendanceCustomDate.now().format(
                DateTimeFormatter.ofPattern("MM월 dd일 E요일").withLocale(Locale.forLanguageTag("ko"))
        );
        System.out.printf("오늘은 %s입니다. 기능을 선택해 주세요.%n", formattedDate);

        Arrays.stream(Menu.values()).forEach(menu -> {
            System.out.printf("%s. %s%n", menu.getInputValue(), menu.getExpression());
        });
    }

    public void printAttendanceResult(Attendance attendance) {
        LocalDateTime time = attendance.getTime();
        String formattedDate = time.format(
                DateTimeFormatter.ofPattern("MM월 dd일 E요일 HH:mm").withLocale(Locale.forLanguageTag("ko"))
        );
        String formattedStatus = "(" + attendance.getStatus().getExpression() + ")";
        System.out.println(formattedDate + " " + formattedStatus);
    }

    public void printExceptionMessage(String message) {
        System.out.println(message);
    }

    public void recommendModifyFunction(String message) {
        System.out.println(message + " 수정 기능을 이용해주세요.");
    }

    public void printModifyResult(AttendanceModifyResponse response) {
        String formattedBeforeDate = response.beforeTime().format(
                DateTimeFormatter.ofPattern("MM월 dd일 E요일 HH:mm").withLocale(Locale.forLanguageTag("ko"))
        );
        String formattedAfterDate = response.afterTime().format(
                DateTimeFormatter.ofPattern("HH:mm").withLocale(Locale.forLanguageTag("ko"))
        );

        System.out.printf("%s (%s) -> %s (%s) 수정 완료!\n",
                formattedBeforeDate,
                response.beforeStatus().getExpression(),
                formattedAfterDate,
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
            String formattedDate = response.date().format(
                    DateTimeFormatter.ofPattern("MM월 dd일 E요일").withLocale(Locale.forLanguageTag("ko"))
            );
            String formattedTime = "--:--";
            if (response.time().isPresent()) {
                formattedTime = response.time().get().format(
                        DateTimeFormatter.ofPattern("HH:mm").withLocale(Locale.forLanguageTag("ko"))
                );
            }
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
}
