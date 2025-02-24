package attendance.domain;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

public enum AttendanceStatus {

    OK("출석", 5, 0),
    LATE("지각", 30, 1),
    ABSENT("결석", Integer.MAX_VALUE, 3);

    private static final LocalTime MONDAY_START_TIME = LocalTime.of(13, 0);
    private static final LocalTime TUESDAY_TO_FRIDAY_START_TIME = LocalTime.of(10, 0);

    private final String text;
    private final int deadLineMinute;
    private final int lateCount;

    AttendanceStatus(final String text, final int deadLineMinute, final int lateCount) {
        this.text = text;
        this.deadLineMinute = deadLineMinute;
        this.lateCount = lateCount;
    }

    public static AttendanceStatus findByAttendanceDateAndTime(final AttendanceDate attendanceDate, final AttendanceTime attendanceTime) {
        if (attendanceDate.isMonday()) {
            return findStatusByStartTime(MONDAY_START_TIME, attendanceTime);
        }
        return findStatusByStartTime(TUESDAY_TO_FRIDAY_START_TIME, attendanceTime);
    }

    private static AttendanceStatus findStatusByStartTime(final LocalTime startTime, final AttendanceTime attendanceTime) {
        int minuteDifferences = attendanceTime.calculateMinuteDifferences(startTime);
        return Arrays.stream(values())
                .filter(status -> minuteDifferences <= status.deadLineMinute)
                .findAny()
                .orElse(ABSENT);
    }

    public static List<AttendanceStatus> findAllStatusesByLocalDateTimes(List<LocalDateTime> localDateTimes) {
        return localDateTimes.stream()
                .map(dateTime -> findByAttendanceDateAndTime(new AttendanceDate(dateTime.toLocalDate()),
                        new AttendanceTime(dateTime.toLocalTime())))
                .toList();
    }

    public static int calculateTotalAbsentCount(final List<AttendanceStatus> statuses) {
        int totalLateCount = statuses.stream()
                .mapToInt(status -> status.lateCount)
                .sum();
        return totalLateCount / ABSENT.lateCount;
    }

    public String getText() {
        return text;
    }

    public int getLateCount() {
        return lateCount;
    }
}
