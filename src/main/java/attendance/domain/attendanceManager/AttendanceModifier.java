package attendance.domain.attendanceManager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import attendance.domain.attendanceBook.Attendance;
import attendance.domain.attendanceBook.AttendanceBook;
import attendance.domain.attendanceBook.AttendanceList;

public class AttendanceModifier extends AttendanceManager {

    public AttendanceModifier(AttendanceBook attendanceBook) {
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
}
