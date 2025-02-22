package attendance.view;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import attendance.domain.AttendanceHistory;

import static attendance.view.ViewConstants.*;

public class OutputView {

    public void printOperations() {
        System.out.printf("오늘은 %s입니다. 기능을 선택해 주세요.%n", DATE_FORMATTER.format(LocalDate.now()));
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
        if (attendanceTime.equals(LocalTime.of(23, 0))) {
            System.out.printf(DATE_FORMATTER.format(attendanceDateTime) + " --:-- (%s)%n", attendanceStatus);
            return;
        }
        System.out.printf(DATE_FORMATTER.format(attendanceDateTime) + " " + TIME_FORMATTER.format(attendanceDateTime) + " (%s)%n", attendanceStatus);
    }

    public void printErrorMessage(final String message) {
        System.out.println(String.join(" ", "[ERROR]", message));
    }

    public void printModificationResult(LocalDateTime originDateTime, String originAttendanceStatus,
                                        LocalDateTime newDateTime, String newAttendanceStatus) {
        LocalTime originTime = originDateTime.toLocalTime();
        if (originTime.equals(LocalTime.of(23, 0))) {
            System.out.printf("%02d월 %02d일 %s %s (%s) -> %s (%s) 수정 완료!%n",
                    originDateTime.getMonthValue(), originDateTime.getDayOfMonth(),
                    originDateTime.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREA)
                    , "--:--",
                    originAttendanceStatus
                    , TIME_FORMATTER.format(newDateTime), newAttendanceStatus);
            return;
        }
        System.out.printf("%02d월 %02d일 %s %s (%s) -> %s (%s) 수정 완료!%n",
                originDateTime.getMonthValue(), originDateTime.getDayOfMonth(),
                originDateTime.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREA)
                , TIME_FORMATTER.format(originDateTime),
                originAttendanceStatus
                , TIME_FORMATTER.format(newDateTime), newAttendanceStatus);
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

    public void printExpulsionCrews(Map<String, AttendanceHistory> attendanceHistories) {
        System.out.println("제적 위험자 조회 결과");
        ArrayList<String> keys = new ArrayList<>(attendanceHistories.keySet());
        keys.sort((o1, o2) -> {
            int firstAbsentCount = attendanceHistories.get(o1).getAbsentCount();
            int secondAbsentCount = attendanceHistories.get(o2).getAbsentCount();
            if (firstAbsentCount != secondAbsentCount) {
                return secondAbsentCount - firstAbsentCount;
            }
            return o1.compareTo(o2);
        });
        for (String key : keys) {
            AttendanceHistory attendanceHistory = attendanceHistories.get(key);
            System.out.printf("- %s: 결석 %d회, 지각 %d회 (%s)%n", key, attendanceHistory.getAbsentCount(),
                    attendanceHistory.getLateCount(), attendanceHistory.getExpulsionStatus());
        }
    }

}
