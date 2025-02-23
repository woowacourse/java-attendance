package attendance.domain.attendanceManager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import attendance.common.exception.AttendanceArgumentException;
import attendance.domain.attendanceBook.Attendance;
import attendance.domain.attendanceBook.AttendanceBook;
import attendance.domain.attendanceBook.AttendanceList;

public class AttendanceRegister extends AttendanceManager {

    public AttendanceRegister(AttendanceBook attendanceBook) {
        super(attendanceBook);
    }

    @Override
    public void manage(String nickname, LocalDate date, LocalTime time) {
        try {
            var dateTime = LocalDateTime.of(date, time);
            var attendance = new Attendance(dateTime);
            var attendanceList = attendanceBook.attendances().get(nickname);
            isDuplicateAttendance(attendance, attendanceList);
            attendanceList.add(attendance);
        } catch (NullPointerException e) {
            throw new AttendanceArgumentException(Error.NOT_REGISTERED_NICKNAME.getMessage());
        }
    }

    private void isDuplicateAttendance(Attendance attendance, AttendanceList attendanceList) {
        if (attendanceList.contains(attendance)) {
            throw new AttendanceArgumentException(Error.DUPLICATE_DATE.getMessage());
        }
    }
}
