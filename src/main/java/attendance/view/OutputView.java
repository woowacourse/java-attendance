package attendance.view;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

import attendance.domain.Attendance;
import attendance.domain.AttendanceStatus;
import attendance.domain.Crew;
import attendance.dto.AttendanceHistoryDto;

import static attendance.view.ViewConstants.*;

public class OutputView {

    public void printErrorMessage(final String message) {
        System.out.println(String.join(" ", "[ERROR]", message));
        System.out.println();
    }

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
        System.out.println();
    }

    public void printAttendance(final LocalDateTime attendanceDateTime, final String attendanceStatus) {
        LocalTime attendanceTime = attendanceDateTime.toLocalTime();
        if (attendanceTime.equals(Attendance.ABSENT_TIME)) {
            System.out.printf(DATE_FORMATTER.format(attendanceDateTime) + " --:-- (%s)%n", attendanceStatus);
            return;
        }
        System.out.printf(DATE_FORMATTER.format(attendanceDateTime) + " " + TIME_FORMATTER.format(attendanceDateTime) + " (%s)%n", attendanceStatus);
    }

    public void printModificationResult(LocalDateTime originDateTime, String originAttendanceStatus,
                                        LocalDateTime newDateTime, String newAttendanceStatus) {
        LocalTime originTime = originDateTime.toLocalTime();
        if (originTime.equals(Attendance.ABSENT_TIME)) {
            System.out.printf("%s --:-- (%s) -> %s (%s) 수정 완료!%n%n",
                    DATE_FORMATTER_WITHOUT_YEAR.format(originDateTime), originAttendanceStatus,
                    TIME_FORMATTER.format(newDateTime), newAttendanceStatus);
            return;
        }
        System.out.printf("%s %s (%s) -> %s (%s) 수정 완료!%n%n",
                DATE_FORMATTER_WITHOUT_YEAR.format(originDateTime), TIME_FORMATTER.format(originDateTime), originAttendanceStatus,
                TIME_FORMATTER.format(newDateTime), newAttendanceStatus);
    }

    public void printAttendances(final Crew crew, final List<LocalDateTime> attendanceTimes,
                                 final List<AttendanceStatus> attendanceStatus) {
        List<String> attendanceStatusTexts = attendanceStatus.stream()
                        .map(AttendanceStatus::getText)
                        .collect(Collectors.toList());
        System.out.printf("이번 달 %s의 출석 기록입니다.%n", crew.getNickname());
        for (int index = 0; index < attendanceTimes.size(); index++) {
            printAttendance(attendanceTimes.get(index), attendanceStatusTexts.get(index));
        }
        System.out.println();
    }

    public void printStatusCounts(final Map<String, Integer> statusCount) {
        Arrays.stream(AttendanceStatus.values())
                .forEach(status -> {
                    String statusText = status.getText();
                    System.out.printf("%s: %d회%n", statusText, statusCount.get(statusText));
                });
        System.out.println();
    }

    public void printExpulsionStatus(final String expulsionStatus) {
        System.out.printf("%s 대상자입니다.%n", expulsionStatus);
        System.out.println();
    }

    public void printExpulsionCrews(Map<String, AttendanceHistoryDto> attendanceHistories) {
        System.out.println("제적 위험자 조회 결과");
        ArrayList<String> keys = new ArrayList<>(attendanceHistories.keySet());
        keys.sort(comparateExpulsionCrews(attendanceHistories));
        for (String key : keys) {
            AttendanceHistoryDto attendanceHistoryDto = attendanceHistories.get(key);
            System.out.printf("- %s: 결석 %d회, 지각 %d회 (%s)%n", key, attendanceHistoryDto.absentCount(),
                    attendanceHistoryDto.lateCount(), attendanceHistoryDto.expulsionStatus());
        }
        System.out.println();
    }

    private static Comparator<String> comparateExpulsionCrews(Map<String, AttendanceHistoryDto> attendanceHistories) {
        return (o1, o2) -> {
            int firstAbsentCount = attendanceHistories.get(o1).absentCount()
                    + (attendanceHistories.get(o1).lateCount() / AttendanceStatus.ABSENT.getLateCount());
            int secondAbsentCount = attendanceHistories.get(o2).absentCount()
                    + (attendanceHistories.get(o2).lateCount() / AttendanceStatus.ABSENT.getLateCount());
            if (firstAbsentCount != secondAbsentCount) {
                return secondAbsentCount - firstAbsentCount;
            }
            return o1.compareTo(o2);
        };
    }

}
