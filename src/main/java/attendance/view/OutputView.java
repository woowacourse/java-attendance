package attendance.view;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class OutputView {

    public void printOperations(LocalDate today) {
        System.out.printf("오늘은 %d월 %d일 %s입니다. 기능을 선택해 주세요.%n", today.getMonthValue(), today.getDayOfMonth(),
                today.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREA));
        System.out.println("1. 출석 확인");
        System.out.println("2. 출석 수정");
        System.out.println("3. 크루별 출석 기록 확인");
        System.out.println("4. 제적 위험자 확인");
        System.out.println("Q. 종료");
    }

    public void printUsingAttendanceModification() {
        System.out.println("오늘 출석 기록이 존재합니다.");
        System.out.println("출석 수정(2) 기능을 이용해주세요.");
    }

    public void printAttendance(final LocalDateTime attendanceDateTime, final String attendanceStatus) {
        LocalTime attendanceTime = attendanceDateTime.toLocalTime();
        System.out.printf("%d월 %d일 %s %02d:%02d (%s)%n", attendanceDateTime.getMonthValue(),
                attendanceDateTime.getDayOfMonth(),
                attendanceDateTime.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREA),
                attendanceTime.getHour(), attendanceTime.getMinute(), attendanceStatus);
    }

    public void printErrorMessage(final String message) {
        System.out.println(String.join(" ", "[ERROR]", message));
    }

    public void printModificationResult(LocalDateTime originDateTime, String originAttendanceStatus,
                                        LocalDateTime newDateTime, String newAttendanceStatus) {
        System.out.printf("%02d월 %02d일 %s %02d:%02d (%s) -> %02d:%02d (%s) 수정 완료!",
                originDateTime.getMonthValue(), originDateTime.getDayOfMonth(),
                originDateTime.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREA)
                , originDateTime.toLocalTime().getHour(), originDateTime.toLocalTime().getMinute(),
                originAttendanceStatus
                , newDateTime.toLocalTime().getHour(), newDateTime.toLocalTime().getMinute(), newAttendanceStatus);
    }

    public void printAttendances(final String crewNickname, final List<LocalDateTime> attendanceTimes,
                                 final List<String> attendanceStatus) {
        System.out.printf("이번 달 %s의 출석 기록입니다.%n", crewNickname);
        for (int index = 0; index < attendanceTimes.size(); index++) {
            printAttendance(attendanceTimes.get(index), attendanceStatus.get(index));
        }
    }

    public void printStatusCounts(final Map<String, Integer> statusCount) {
        System.out.println();
        System.out.printf("출석: %d회%n", statusCount.get("출석"));
        System.out.printf("지각: %d회%n", statusCount.get("지각"));
        System.out.printf("결석: %d회%n", statusCount.get("결석"));
    }

    public void printExpulsionStatus(final String expulsionStatus) {
        System.out.println();
        System.out.printf("%s 대상자입니다.%n", expulsionStatus);
    }

}
