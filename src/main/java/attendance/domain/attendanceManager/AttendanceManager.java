package attendance.domain.attendanceManager;

import java.time.LocalDate;
import java.time.LocalTime;

import attendance.domain.attendanceBook.AttendanceBook;

public abstract class AttendanceManager {
    protected final AttendanceBook attendanceBook;
    protected final StringBuilder builder = new StringBuilder();

    public AttendanceManager(AttendanceBook attendanceBook) {
        this.attendanceBook = attendanceBook;
    }

    public abstract void manage(String nickname, LocalDate date, LocalTime time);

    public abstract String getResult();
}
