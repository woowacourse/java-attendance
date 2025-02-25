import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AttendanceSystem {
    public LocalDate TODAY = LocalDate.of(2024, 12, 17);
    private final LocalDate CHRISTMAS = LocalDate.of(2024, 12, 25);
    private final Map<String, AttendanceBook> attendanceBooks;

    public AttendanceSystem() {
        attendanceBooks = new HashMap<>();
    }

    public void attendance(String name, LocalTime time) {
        if (!attendanceBooks.containsKey(name)) {
            attendanceBooks.put(name, new AttendanceBook());
        }
        if(attendanceBooks.get(name).hasAttendanceRecord(TODAY)) {
            throw new IllegalArgumentException();
        }
        attendanceBooks.get(name).attendance(TODAY, time);
    }


    public LocalDateTime getAttendanceRecord(String name, LocalDate date) {
        return attendanceBooks.get(name).getAttendanceDateTimeByDate(date);
    }

    public void editAttendance(String name, LocalDate date, LocalTime time) {
        if(isHoliday(date)) {
            throw new IllegalArgumentException();
        }
        attendanceBooks.get(name).attendance(date, time);
    }

    private boolean isHoliday(LocalDate date) {
        DayOfWeek day = date.getDayOfWeek();
        return isWeekend(day) || isChristmas(date);
    }

    private boolean isChristmas(LocalDate date) {
        return Objects.equals(date, CHRISTMAS);
    }

    private static boolean isWeekend(DayOfWeek day) {
        return day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY;
    }
}
