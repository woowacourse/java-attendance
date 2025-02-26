package attendance.model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Comparator;

public enum AttendanceStatus {
    ATTEND(0),
    LATE(5),
    ABSENCE(30),
    ;

    private final int deadline;

    AttendanceStatus(int deadline) {
        this.deadline = deadline;
    }

    public static AttendanceStatus from(LocalDate localDate, LocalTime time) {
        if (localDate.getDayOfWeek().equals(DayOfWeek.MONDAY)) {
            return getAttendanceStatus(time, LocalTime.of(13, 0));
        } else {
            return getAttendanceStatus(time, LocalTime.of(10, 0));
        }
    }

    private static AttendanceStatus getAttendanceStatus(LocalTime currentTime, LocalTime startTime) {
        return Arrays.stream(AttendanceStatus.values())
                .sorted(Comparator.reverseOrder())
                .filter(attendanceState -> currentTime.isAfter(startTime.plusMinutes(attendanceState.deadline)))
                .findFirst()
                .orElse(AttendanceStatus.ATTEND);
    }
}
