package attendance.domain.attendanceManager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import attendance.domain.attendance.Attendance;
import attendance.domain.attendance.AttendanceBook;
import attendance.domain.attendance.AttendanceList;

public class ModifyManager extends AttendanceManager {

    public ModifyManager(AttendanceBook attendanceBook) {
        super(attendanceBook);
    }

    @Override
    public void manage(String nickname, LocalDate date, LocalTime time) {
        var dateTime = LocalDateTime.of(date, time);
        var attendance = new Attendance(dateTime);

        AttendanceList attendanceList = attendanceBook.getAttendanceList(nickname);

        attendanceBook.findAttendance(nickname, date)
            .ifPresent(attendanceList::remove);

        attendanceList.add(attendance);
    }

    @Override
    public String getResult() {
        return builder.toString();
    }
}
