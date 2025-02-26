import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

public class AttendanceCheck {

    public AttendanceStatus judge(LocalTime attendanceTime, LocalDate date) {

        DayOfWeek dayOfWeek = date.getDayOfWeek();
        StandardTime standardTime = StandardTime.findByDayOfWeek(dayOfWeek);

        if (attendanceTime.isAfter(standardTime.getLateTime()) && attendanceTime.isBefore(standardTime.getAbsentTime())
                || attendanceTime.equals(standardTime.getAbsentTime())) {
            return AttendanceStatus.LATENESS;
        }

        if (attendanceTime.isAfter(standardTime.getAbsentTime())) {
            return AttendanceStatus.ABSENCE;
        }

        return AttendanceStatus.ATTENDANCE;
    }
}
