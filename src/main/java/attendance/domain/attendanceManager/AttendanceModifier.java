package attendance.domain.attendanceManager;

import java.time.LocalDate;
import java.time.LocalTime;

import attendance.domain.attendanceBook.AttendanceBook;

public class AttendanceModifier extends AttendanceManager {

    public AttendanceModifier(AttendanceBook attendanceBook) {
        super(attendanceBook);
    }

    @Override
    public void manage(String nickname, LocalDate date, LocalTime time) {

    }

}
