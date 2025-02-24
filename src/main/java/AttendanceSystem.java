import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class AttendanceSystem {
    public final LocalDate TODAY = LocalDate.of(2024, 12, 17);
    private final Map<String, AttendanceBook> attendanceBooks;

    public AttendanceSystem() {
        attendanceBooks = new HashMap<>();
    }

    public void attendance(String name, LocalTime time) {
        if(isNotOperatingHours(time)) {
            throw new IllegalArgumentException();
        }
        if(!attendanceBooks.containsKey(name)) {
            attendanceBooks.put(name, new AttendanceBook());
        }
        attendanceBooks.get(name).attendance(TODAY, time);
    }

    private boolean isNotOperatingHours(LocalTime time) {
        return time.isBefore(LocalTime.of(8, 0)) || time.isAfter(LocalTime.of(23, 0));
    }

    public LocalDateTime getAttendanceRecord(String name, LocalDate date) {
        return  attendanceBooks.get(name).getAttendanceDateTimeByDate(date);
    }
}
