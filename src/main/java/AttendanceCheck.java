import java.time.LocalTime;

public class AttendanceCheck {

    public AttendanceStatus judge(LocalTime attendanceTime,
                                  LocalTime lateStandardTime,
                                  LocalTime absentStandardTime) {

        if (attendanceTime.isAfter(lateStandardTime) && attendanceTime.isBefore(absentStandardTime)
                || attendanceTime.equals(absentStandardTime)) {
            return AttendanceStatus.LATENESS;
        }

        if (attendanceTime.isAfter(absentStandardTime)) {
            return AttendanceStatus.ABSENCE;
        }

        return AttendanceStatus.ATTENDANCE;
    }
}
