package domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public enum OperationSchedule {

    MONDAY(DayOfWeek.MONDAY, Constant.EIGHT, Constant.TWENTY_THREE, LocalTime.of(13, 0)),
    TUESDAY(DayOfWeek.TUESDAY, Constant.EIGHT, Constant.TWENTY_THREE, Constant.TEN),
    WEDNESDAY(DayOfWeek.WEDNESDAY, Constant.EIGHT, Constant.TWENTY_THREE, Constant.TEN),
    THURSDAY(DayOfWeek.THURSDAY, Constant.EIGHT, Constant.TWENTY_THREE, Constant.TEN),
    FRIDAY(DayOfWeek.FRIDAY, Constant.EIGHT, Constant.TWENTY_THREE, Constant.TEN),
    SATURDAY(DayOfWeek.SATURDAY, Constant.EIGHT, Constant.TWENTY_THREE, Constant.TEN),
    SUNDAY(DayOfWeek.SUNDAY, Constant.EIGHT, Constant.TWENTY_THREE, Constant.TEN),
    NONE(null, LocalTime.of(0, 0), LocalTime.of(0, 0), LocalTime.of(10, 0));

    private static class Constant {
        private static final LocalTime EIGHT = LocalTime.of(8, 0);
        private static final LocalTime TWENTY_THREE = LocalTime.of(23, 0);
        private static final LocalTime TEN = LocalTime.of(10, 0);
    }

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
