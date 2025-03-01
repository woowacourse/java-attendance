package domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public enum OperationSchedule {

    MONDAY(DayOfWeek.MONDAY, LocalTime.of(8, 0), LocalTime.of(23, 0), LocalTime.of(13, 0)),
    TUESDAY(DayOfWeek.TUESDAY, LocalTime.of(8, 0), LocalTime.of(23, 0), LocalTime.of(10, 0)),
    WEDNESDAY(DayOfWeek.WEDNESDAY, LocalTime.of(8, 0), LocalTime.of(23, 0), LocalTime.of(10, 0)),
    THURSDAY(DayOfWeek.THURSDAY, LocalTime.of(8, 0), LocalTime.of(23, 0), LocalTime.of(10, 0)),
    FRIDAY(DayOfWeek.FRIDAY, LocalTime.of(8, 0), LocalTime.of(23, 0), LocalTime.of(10, 0)),
    SATURDAY(DayOfWeek.SATURDAY, LocalTime.of(8, 0), LocalTime.of(23, 0), LocalTime.of(10, 0)),
    SUNDAY(DayOfWeek.SUNDAY, LocalTime.of(8, 0), LocalTime.of(23, 0), LocalTime.of(10, 0)),
    NONE(null, LocalTime.of(0, 0), LocalTime.of(0, 0), LocalTime.of(10, 0));

    private final DayOfWeek dayOfWeek;
    private final LocalTime startTime;
    private final LocalTime endTime;
    private final LocalTime attendanceStandard;

    OperationSchedule(DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime, LocalTime attendanceStandard) {
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
        this.attendanceStandard = attendanceStandard;
    }

    public static boolean isInOperationTime(LocalDateTime datetime) {
        OperationSchedule schedule = findSchedule(datetime.toLocalDate());
        LocalTime time = datetime.toLocalTime();
        return !(time.isBefore(schedule.startTime) || time.isAfter(schedule.endTime));
    }

    private static OperationSchedule findSchedule(LocalDate time) {
        for (OperationSchedule schedule : OperationSchedule.values()) {
            if (schedule.dayOfWeek.equals(time.getDayOfWeek())) {
                return schedule;
            }
        }
        return NONE;
    }

    public static long calculateDifferenceFromAttendanceStandard(LocalDateTime time) {
        OperationSchedule schedule = findSchedule(time.toLocalDate());
        return time.toLocalTime().toNanoOfDay() - schedule.attendanceStandard.toNanoOfDay();
    }

    public static LocalTime addMinutesToStartTime(LocalDate day, int standardTime) {
        return findSchedule(day).attendanceStandard.plusMinutes(standardTime);
    }
}
