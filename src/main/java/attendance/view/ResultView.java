package attendance.view;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class ResultView {

    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("MM월 dd일 E요일 HH:mm", Locale.KOREA);
    private static final DateTimeFormatter DATE_FORMATTER_WITHOUT_TIME =
            DateTimeFormatter.ofPattern("MM월 dd일 E요일 --:--", Locale.KOREA);
    private static final DateTimeFormatter TIME_FORMATTER =
            DateTimeFormatter.ofPattern("HH:mm", Locale.KOREA);

    public void printErrorMessage(final String message) {
        System.out.println(String.join(" ", "[ERROR]", message));
    }

    public void printAttendanceConfirmResult(final LocalDateTime attendanceDateTime, final String attendanceStatus) {
        System.out.println();
        System.out.printf(String.join(" ", DATE_TIME_FORMATTER.format(attendanceDateTime), "(%s)"), attendanceStatus);
        System.out.println();
    }

    public void printOriginAttendanceRecord(
            final boolean hasAttendanceRecord, final LocalDateTime originAttendanceDateTime,
            final String originAttendanceStatus
    ) {
        System.out.println();
        if (hasAttendanceRecord) {
            System.out.printf(String.join(" ", DATE_TIME_FORMATTER.format(originAttendanceDateTime),
                    "(%s)"), originAttendanceStatus);
            return;
        }
        System.out.printf(String.join(" ", DATE_FORMATTER_WITHOUT_TIME.format(originAttendanceDateTime),
                "(%s)"), originAttendanceStatus);
    }

    public void printModificationAttendanceRecord(
            final LocalTime modificationAttendanceTime, final String modificationAttendanceStatus
    ) {
        System.out.printf(String.join(
                "", " -> ", TIME_FORMATTER.format(modificationAttendanceTime),
                " (%s)%n"), modificationAttendanceStatus);
        System.out.println();
    }

    public void printCrewAttendancesUntilYesterday(
            final String nickname, final List<LocalDateTime> attendanceDateTimes,
            final List<Boolean> attendanceExistences, final List<String> attendanceStatuses
    ) {
        System.out.println();
        System.out.printf("이번 달 %s의 출석 기록입니다.%n", nickname);
        System.out.println();
        for (int index = 0; index < attendanceDateTimes.size(); index++) {
            printAttendanceRecord(attendanceDateTimes, attendanceExistences, attendanceStatuses, index);
        }
    }

    private void printAttendanceRecord(
            final List<LocalDateTime> attendanceDateTimes, final List<Boolean> attendanceExistences,
            final List<String> attendanceStatuses, final int index
    ) {
        boolean hasAttendanceRecord = attendanceExistences.get(index);
        LocalDateTime attendanceDateTime = attendanceDateTimes.get(index);
        if (hasAttendanceRecord) {
            System.out.printf(String.join(" ",
                    DATE_TIME_FORMATTER.format(attendanceDateTime),
                    "(%s)%n"), attendanceStatuses.get(index));
            return;
        }
        System.out.printf(String.join(" ",
                DATE_FORMATTER_WITHOUT_TIME.format(attendanceDateTime),
                "(%s)%n"), attendanceStatuses.get(index));
    }

    public void printAttendanceStatusCount(final int absentCount, final int lateCount, final int attendanceCount) {
        System.out.println();
        System.out.printf("""
                출석 : %d
                지각 : %d
                결석 : %d
                """, attendanceCount, lateCount, absentCount);
        System.out.println();
    }

    public void printExpulsionStatus(final String expulsionStatus) {
        System.out.printf("%s 대상자입니다.%n", expulsionStatus);
    }

    public void printPenaltyCrews() {
        System.out.println();
        System.out.println("제적 위험자 조회 결과");
    }

    public void printPenaltyCrewInformation(final String nickname, final int absentCount, final int lateCount,
                                            final String expulsionStatus
    ) {
        System.out.printf("%s: 결석 %d회, 지각 %d회 (%s)%n", nickname, absentCount, lateCount, expulsionStatus);
    }

}
