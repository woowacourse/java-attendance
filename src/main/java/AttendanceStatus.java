import java.time.DayOfWeek;
import java.time.LocalTime;

public class AttendanceStatus {
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
