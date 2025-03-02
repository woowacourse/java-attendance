//package view;
//
//import controller.Menu;
//import domain.domain.Attendance;
//import domain.AttendanceCustomDate;
//import domain.domain.AttendanceStatus;
//import domain.CrewStatus;
//import view.dto.DisenrollmentCheckResponse;
//
//import java.time.LocalDate;
//import java.time.LocalTime;
//import java.time.format.DateTimeFormatter;
//import java.util.*;
//
//public class OutputView {
//
//    public void printDateAndMenus() {
//        String formattedDate = AttendanceCustomDate.now().format(
//                DateTimeFormatter.ofPattern("MM월 dd일 E요일").withLocale(Locale.forLanguageTag("ko"))
//        );
//        System.out.printf("오늘은 %s입니다. 기능을 선택해 주세요.%n", formattedDate);
//
//        Arrays.stream(Menu.values()).forEach(menu -> {
//            System.out.printf("%s. %s%n", menu.getInputValue(), menu.getExpression());
//        });
//    }
//
//    public void printAttendanceResult(LocalDate date, LocalTime time, String status) {
//        String formattedDate = date.format(
//                DateTimeFormatter.ofPattern("MM월 dd일 E요일").withLocale(Locale.forLanguageTag("ko"))
//        );
//        String formattedTime = time.format(
//                DateTimeFormatter.ofPattern("HH:mm").withLocale(Locale.forLanguageTag("ko"))
//        );
//
//        String formattedStatus = "(" + status + ")";
//        System.out.println(formattedDate + " " + formattedTime + " " + formattedStatus);
//    }
//
//    public void printExceptionMessage(String message) {
//        System.out.println(message);
//    }
//
//    public void recommendModifyFunction(String message) {
//        System.out.println(message + " 수정 기능을 이용해주세요.");
//    }
//
//    public void printModifyResult(
//            LocalDate date,
//            LocalTime beforeTime,
//            String beforeStatus,
//            LocalTime afterTime,
//            String afterStatus
//    ) {
//        String formattedDate = getFormattedDate(date);
//
//        String formattedBeforeTime = "--:--";
//        if (beforeTime != null) {
//            formattedBeforeTime = getFormattedTime(beforeTime);
//        }
//
//        String formattedAfterTime = "--:--";
//        if (afterTime != null) {
//            formattedAfterTime = getFormattedTime(afterTime);
//        }
//
//        System.out.printf("%s %s (%s) -> %s %s (%s) 수정 완료!\n",
//                formattedDate,
//                formattedBeforeTime,
//                beforeStatus,
//                formattedDate,
//                formattedAfterTime,
//                afterStatus
//        );
//    }
//
//    public void printHistoryResult(
//            String name,
//            Map<LocalDate, domain.Attendance> histories,
//            Map<domain.AttendanceStatus, Integer> attendanceResult,
//            CrewStatus crewStatus
//    ) {
//        System.out.printf("이번 달 %s의 출석 기록입니다.\n", name);
//        printHistories(histories);
//        printAttendanceCount(attendanceResult);
//        // TODO: 도메인 객체로 검사하는게 맞나..?
//        if (crewStatus != CrewStatus.NORMAL) {
//            printCrewStatus(crewStatus);
//        }
//    }
//
//    private String getFormattedDate(LocalDate date) {
//        return date.format(
//                DateTimeFormatter.ofPattern("MM월 dd일 E요일").withLocale(Locale.forLanguageTag("ko"))
//        );
//    }
//
//    private String getFormattedTime(LocalTime time) {
//        return time.format(
//                DateTimeFormatter.ofPattern("HH:mm").withLocale(Locale.forLanguageTag("ko"))
//        );
//    }
//
//    private void printHistories(Map<LocalDate, domain.Attendance> histories) {
//        for (Map.Entry<LocalDate, domain.Attendance> entry : histories.entrySet()) {
//            LocalDate date = entry.getKey();
//            domain.Attendance attendance = entry.getValue();
//            String formattedDate = date.format(
//                    DateTimeFormatter.ofPattern("MM월 dd일 E요일").withLocale(Locale.forLanguageTag("ko"))
//            );
//            String formattedTime = "--:--";
//            if (!attendance.isAbsence()) {
//                formattedTime = attendance.getTime()
//                        .get()
//                        .format(DateTimeFormatter.ofPattern("HH:mm").withLocale(Locale.forLanguageTag("ko")));
//            }
//            String status = attendance.getStatus().getExpression();
//            System.out.printf("%s %s (%s)\n", formattedDate, formattedTime, status);
//        }
//    }
//
//    private void printAttendanceCount(Map<domain.AttendanceStatus, Integer> attendanceResult) {
//        attendanceResult.keySet().forEach(attendanceStatus -> {
//            System.out.printf("%s: %d회\n", attendanceStatus.getExpression(), attendanceResult.get(attendanceStatus));
//        });
//    }
//
//    private void printCrewStatus(CrewStatus crewStatus) {
//        System.out.printf("%s 대상자입니다.\n", getCrewStatusText(crewStatus));
//    }
//
//    public void printDisenrollmentCheckResult(List<DisenrollmentCheckResponse> responses) {
//        System.out.println("제적 위험자 조회 결과");
//        List<DisenrollmentCheckResponse> sortedResponse = getSortedResponse(responses);
//        for (DisenrollmentCheckResponse response : sortedResponse) {
//            System.out.printf("- %s: 결석 %d회, 지각 %d회 (%s)\n",
//                    response.name(),
//                    response.absenceCount(),
//                    response.lateCount(),
//                    getCrewStatusText(response.crewStatus())
//            );
//        }
//    }
//
//    private String getCrewStatusText(CrewStatus crewStatus) {
//        switch (crewStatus) {
//            case WARNING:
//                return "경고";
//            case CONSULTANT:
//                return "면담";
//            case DISENROLLMENT:
//                return "제적";
//        }
//        return "";
//    }
//
//    private List<DisenrollmentCheckResponse> getSortedResponse(List<DisenrollmentCheckResponse> responses) {
//        return responses.stream()
//                .sorted(Comparator.comparing(DisenrollmentCheckResponse::crewStatus)
//                        .thenComparing(DisenrollmentCheckResponse::totalAbsenceCount).reversed()
//                        .thenComparing(DisenrollmentCheckResponse::name))
//                .toList();
//    }
//}
