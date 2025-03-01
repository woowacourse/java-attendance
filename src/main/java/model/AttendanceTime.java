package model;

import static constant.ErrorMessage.CANNOT_CHECK_IN_ON_WEEKEND;
import static constant.ErrorMessage.OUT_OF_OPERATION_HOURS;

import java.time.DayOfWeek;
import java.time.LocalDate;
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

    AttendanceTime(DayOfWeek dayOfWeek, LocalTime operationStartTime,
                   LocalTime operationEndTime, LocalTime educationStartTime) {
        this.dayOfWeek = dayOfWeek;
        this.operationStartTime = operationStartTime;
        this.operationEndTime = operationEndTime;
        this.educationStartTime = educationStartTime;
    }

    public static void validateInOperationTime(LocalDate localDate, LocalTime localTime) {
        AttendanceTime attendanceTime = find(localDate);

        if (localTime.isBefore(attendanceTime.operationStartTime) ||
                localTime.isAfter(attendanceTime.operationEndTime)) {
            throw new IllegalArgumentException(OUT_OF_OPERATION_HOURS.getMessage());
        }
    }

    public static boolean isLate(LocalDate localDate, LocalTime localTime, int beLateTime, int absenceTime) {
        AttendanceTime attendanceTime = find(localDate);

        return localTime.isAfter(attendanceTime.educationStartTime.plusMinutes(beLateTime)) &&
                localTime.isBefore(attendanceTime.educationStartTime.plusMinutes(absenceTime + 1));
    }

    public static boolean isAbsence(LocalDate localDate, LocalTime localTime, int absenceTime) {
        AttendanceTime attendanceTime = find(localDate);

        return localTime.isAfter(attendanceTime.educationStartTime.plusMinutes(absenceTime));
    }

    private static AttendanceTime find(LocalDate localDate) {
        return Arrays.stream(AttendanceTime.values())
                .filter(attendanceTime -> attendanceTime.dayOfWeek.equals(localDate.getDayOfWeek()))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(CANNOT_CHECK_IN_ON_WEEKEND.getMessage()));
    }
}
