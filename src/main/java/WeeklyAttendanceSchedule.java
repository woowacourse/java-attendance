import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;

public enum WeeklyAttendanceSchedule {
    MONDAY(LocalTime.of(13,0), DayOfWeek.MONDAY),
    TUESDAY(LocalTime.of(10,0), DayOfWeek.TUESDAY),
    WEDNESDAY(LocalTime.of(10,0), DayOfWeek.WEDNESDAY),
    THURSDAY(LocalTime.of(10,0), DayOfWeek.THURSDAY),
    FRIDAY(LocalTime.of(10,0), DayOfWeek.FRIDAY);

    private final LocalTime attendanceStartTime;
    private final DayOfWeek dayOfWeek;

    WeeklyAttendanceSchedule(LocalTime attendanceStartTime, DayOfWeek dayOfWeek) {
        this.attendanceStartTime = attendanceStartTime;
        this.dayOfWeek = dayOfWeek;
    }

    public static LocalTime findAttendanceScheduleByLocalDate(LocalDate localDate){
        return Arrays.stream(WeeklyAttendanceSchedule.values())
                .filter(weeklyAttendanceSchedule -> weeklyAttendanceSchedule.dayOfWeek.equals(localDate.getDayOfWeek()))
                .map(WeeklyAttendanceSchedule::getAttendanceStartTime)
                .findFirst()
                .orElseThrow();
    }
    public LocalTime getAttendanceStartTime(){
        return attendanceStartTime;
    }
}
