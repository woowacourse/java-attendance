import domain.Attendance;
import domain.AttendanceBook;
import domain.Attendances;
import domain.Day;
import java.time.LocalTime;

public class AttendanceUpdate {

    public void updateAttendanceTime(AttendanceBook attendanceBook, String nickname, Day day, LocalTime modifiedTime) {
        Attendances attendances = attendanceBook.getAttendances(nickname);
        Attendance attendance = attendances.findByDay(day);
        attendance.modifyTimeTo(modifiedTime);
    }
}
