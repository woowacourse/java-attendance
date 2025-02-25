package attendance.domain.attendanceManager;

import java.time.LocalDateTime;

import attendance.domain.attendance.Attendance;
import attendance.domain.attendance.AttendanceBook;
import attendance.domain.attendance.AttendanceList;

public class ModifyManager {
    private final AttendanceBook attendanceBook;
    private final StringBuilder builder = new StringBuilder();

    public ModifyManager(AttendanceBook attendanceBook) {
        this.attendanceBook = attendanceBook;
    }

    public void manage(String nickname, LocalDateTime dateTime) {
        var attendance = new Attendance(dateTime);

        AttendanceList attendanceList = attendanceBook.getAttendanceList(nickname);

        attendanceBook.findAttendance(nickname, dateTime.toLocalDate())
            .ifPresent(attendanceList::remove);

        attendanceList.add(attendance);
    }

    public String getResult() {
        return builder.toString();
    }
}
