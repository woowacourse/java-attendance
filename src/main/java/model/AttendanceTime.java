package model;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;

public enum AttendanceTime {

    MONDAY(DayOfWeek.MONDAY, LocalTime.of(8, 0), LocalTime.of(23, 0), LocalTime.of(13, 0)),
    TUESDAY(DayOfWeek.TUESDAY, LocalTime.of(8, 0), LocalTime.of(23, 0), LocalTime.of(10, 0)),
    WEDNESDAY(DayOfWeek.WEDNESDAY, LocalTime.of(8, 0), LocalTime.of(23, 0), LocalTime.of(10, 0)),
    THURSDAY(DayOfWeek.THURSDAY, LocalTime.of(8, 0), LocalTime.of(23, 0), LocalTime.of(10, 0)),
    FRIDAY(DayOfWeek.FRIDAY, LocalTime.of(8, 0), LocalTime.of(23, 0), LocalTime.of(10, 0)),
    ;

    private final DayOfWeek dayOfWeek;
    private final LocalTime operationStartTime;
    private final LocalTime operationEndTime;
    private final LocalTime educationStartTime;

    AttendanceTime(DayOfWeek dayOfWeek, LocalTime operationStartTime, LocalTime operationEndTime,
                   LocalTime educationStartTime) {
        this.dayOfWeek = dayOfWeek;
        this.operationStartTime = operationStartTime;
        this.operationEndTime = operationEndTime;
        this.educationStartTime = educationStartTime;
    }

    public static boolean isNotInOperation(LocalDateTime localDateTime) {
        AttendanceTime attendanceTime = findAttendanceTime(localDateTime);
        return localDateTime.toLocalTime().isBefore(attendanceTime.operationStartTime) || localDateTime.toLocalTime()
                .isAfter(attendanceTime.operationEndTime);
    }

    // 5 < 체크인 타임 <= 30
    public static long calculateMillisDifferenceFromStartTime(LocalDateTime dateTime) {
        AttendanceTime attendanceTime = findAttendanceTime(dateTime);
        LocalTime time = dateTime.toLocalTime();

        return Duration.between(attendanceTime.educationStartTime, time).toMillis();
    }

    private static AttendanceTime findAttendanceTime(LocalDateTime time) {
        return Arrays.stream(AttendanceTime.values())
                .filter(attendanceTime -> attendanceTime.dayOfWeek.equals(time.toLocalDate().getDayOfWeek()))
                .findAny().orElseThrow(() -> new IllegalArgumentException("주말 및 공휴일에는 출석할 수 없습니다."));
    }
}
