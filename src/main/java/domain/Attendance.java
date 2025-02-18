package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class Attendance {
    public static Map<LocalDate, LocalTime> attendanceBook = new HashMap<>();
    public static Object attend(String name, LocalDateTime target) {
        int dayOfWeek = target.getDayOfWeek().getValue();

        LocalTime attendanceTime = LocalTime.of(10, 0);
        LocalTime targetTime = target.toLocalTime();

        if (dayOfWeek == 6 || dayOfWeek == 7) {
            throw new IllegalArgumentException();
        }

        if (dayOfWeek == 1) {
            attendanceTime = LocalTime.of(13, 0);
        }


        if (targetTime.isAfter(attendanceTime)) {
            if (attendanceTime.plusMinutes(30).isBefore(targetTime)) {
                return 2;
            }
            if (attendanceTime.plusMinutes(5).isBefore(targetTime)) {
                return 1;
            }
            return 0;
        }
        return 0;
    }

    public static void addAttendStatus(String name, LocalDateTime target) {
        LocalDate date = target.toLocalDate();
        LocalTime time = target.toLocalTime();
        attendanceBook.put(date, time);
    }

    public static LocalTime getAttendanceTime(String name, LocalDate date) {
        return attendanceBook.getOrDefault(date, LocalTime.of(0, 0));
    }

    public static void editAttendStatus(String name, LocalDateTime target) {
        LocalDate date = target.toLocalDate();
        if (!attendanceBook.containsKey(date)) {
            throw new IllegalArgumentException();
        }
        attendanceBook.put(date, target.toLocalTime());
    }
}
