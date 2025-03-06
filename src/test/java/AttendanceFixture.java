import domain.Attendance;
import domain.AttendanceStatus;
import domain.AttendanceTime;
import java.time.LocalDate;

public class AttendanceFixture {

    public static Attendance createAttendance(LocalDate localDate, int hour, int minute) {
        AttendanceTime attendanceTime = new AttendanceTime(hour, minute);
        AttendanceStatus attendanceStatus = AttendanceStatus.findByStartHourAndAttendanceTime(10, attendanceTime);
        return Attendance.create(localDate, attendanceTime, attendanceStatus);
    }

}
