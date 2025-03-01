import java.time.LocalDate;
import java.time.LocalTime;

public enum AttendanceStatus {

    ATTENDANCE,
    LATE,
    ABSENT;

    public static AttendanceStatus attendanceStatusCalculate(LocalDate todayDate, LocalTime attendanceTime) {
        LocalTime attendanceStartTime = WeeklyAttendanceSchedule.findAttendanceScheduleByLocalDate(todayDate);
        if (attendanceTime.isAfter(attendanceStartTime.plusMinutes(30))) {
            return ABSENT;
        }
        if (attendanceTime.isAfter(attendanceStartTime.plusMinutes(5))) {
            return LATE;
        }
        return ATTENDANCE;
    }
}
