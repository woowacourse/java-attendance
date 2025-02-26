import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Arrays;

public class AttendanceStatus {
    enum ClassSchedule {
        MONDAY(DayOfWeek.MONDAY, LocalTime.of(13, 0)),
        TUESDAY(DayOfWeek.TUESDAY, LocalTime.of(10, 0)),
        WEDNESDAY(DayOfWeek.WEDNESDAY, LocalTime.of(10, 0)),
        THURSDAY(DayOfWeek.THURSDAY, LocalTime.of(10, 0)),
        FRIDAY(DayOfWeek.FRIDAY, LocalTime.of(10, 0));

        private final DayOfWeek dayOfWeek;
        private final LocalTime startTime;

        ClassSchedule(DayOfWeek dayOfWeek, LocalTime startTime) {
            this.dayOfWeek = dayOfWeek;
            this.startTime = startTime;
        }

        public static LocalTime getStartTimeOf(DayOfWeek day) {
            return Arrays.stream(ClassSchedule.values())
                    .filter(schedule -> schedule.dayOfWeek == day)
                    .findFirst()
                    .orElseThrow(IllegalStateException::new)
                    .getStartTime();
        }

        public LocalTime getStartTime() {
            return startTime;
        }
    }

    public String getStatus(DayOfWeek dayOfWeek, LocalTime time) {
        LocalTime classStartTime = ClassSchedule.getStartTimeOf(dayOfWeek);

        if (time.isBefore(classStartTime.plusMinutes(6))) {
            return "출석";
        }
        if (time.isBefore(classStartTime.plusMinutes(31))) {
            return "지각";
        }
        return "결석";
    }
}
