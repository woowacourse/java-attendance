import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class AttendanceBook {
    private final Map<LocalDate, LocalTime> attendanceBook;

    public AttendanceBook() {
        attendanceBook = new HashMap<>();
    }

    public LocalDateTime getAttendanceDateTimeByDate(LocalDate date) {
        return LocalDateTime.of(date, attendanceBook.get(date));
    }

    public void attendance(LocalDate today, LocalTime time) {
        if(isNotOperatingHours(time)) {
            throw new IllegalArgumentException();
        }
        attendanceBook.put(today, time);
    }

    public boolean hasAttendanceRecord(LocalDate date) {
        return attendanceBook.containsKey(date);
    }

    private boolean isNotOperatingHours(LocalTime time) {
        return time.isBefore(LocalTime.of(8, 0)) || time.isAfter(LocalTime.of(23, 0));
    }
}
